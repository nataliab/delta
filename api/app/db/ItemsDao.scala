package db

import io.flow.delta.v0.models.{Item, ItemSummary, ItemSummaryUndefinedType}
import io.flow.delta.v0.models.{OrganizationSummary, Project, ProjectSummary, Visibility}
import io.flow.delta.v0.models.json._
import io.flow.common.v0.models.User
import io.flow.postgresql.{Authorization, Query, OrderBy}
import anorm._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import scala.util.{Failure, Success, Try}

case class ItemForm(
  summary: ItemSummary,
  label: String,
  description: Option[String],
  contents: String
)

object ItemsDao {

  private[this] val BaseQuery = Query(s"""
    select items.id,
           items.organization_id,
           items.visibility,
           items.object_id,
           items.label,
           items.description,
           items.contents,
           items.summary::text,
           items.created_at,
           organizations.id as organization_id,
           organizations.key as organization_key
      from items
      join organizations on organizations.id = items.organization_id
  """)

  private[this] val InsertQuery = """
    insert into items
    (id, organization_id, visibility, object_id, label, description, contents, summary, updated_by_user_id)
    values
    ({id}, {organization_id}, {visibility}, {object_id}, {label}, {description}, {contents}, {summary}::json, {updated_by_user_id})
  """

  private[this] def objectId(summary: ItemSummary): String = {
    summary match {
      case ProjectSummary(id, org, name) => id
      case ItemSummaryUndefinedType(name) => sys.error(s"Cannot get a id from ItemSummaryUndefinedType($name)")
    }
  }

  private[this] def organization(summary: ItemSummary): OrganizationSummary = {
    summary match {
      case ProjectSummary(id, org, name) => org
      case ItemSummaryUndefinedType(name) => sys.error(s"Cannot get a id from ItemSummaryUndefinedType($name)")
    }
  }

  private[this] def visibility(summary: ItemSummary): Visibility = {
    summary match {
      case ProjectSummary(id, org, name) => {
        ProjectsDao.findById(Authorization.All, id).map(_.visibility).getOrElse(Visibility.Private)
      }
      case ItemSummaryUndefinedType(name) => {
        Visibility.Private
      }
    }
  }

  def replaceProject(user: User, project: Project): Item = {
    val label = project.name
    val description = project.uri

    replace(
      user,
      ItemForm(
        summary = ProjectSummary(
          id = project.id,
          organization = project.organization,
          name = project.name
        ),
        label = label,
        description = Some(description),
        contents = Seq(project.id.toString, label, description).mkString(" ")
      )
    )
  }

  private[db] def replace(user: User, form: ItemForm): Item = {
    DB.withConnection { implicit c =>
      findByObjectId(Authorization.All, objectId(form.summary)).map { item =>
        deleteWithConnection(user, item)(c)
      }

      Try(create(user, form)(c)) match {
        case Success(item) => item
        case Failure(ex) => {
          findByObjectId(Authorization.All, objectId(form.summary)).getOrElse {
            sys.error(s"Failed to replace item: $ex")
          }
        }
      }
    }
  }

  private[this] def create(createdBy: User, form: ItemForm)(implicit c: java.sql.Connection): Item = {
    val id = io.flow.play.util.IdGenerator("itm").randomId()

    SQL(InsertQuery).on(
      'id -> id,
      'organization_id -> organization(form.summary).id,
      'visibility -> visibility(form.summary).toString,
      'object_id -> objectId(form.summary),
      'label -> form.label,
      'description -> form.description,
      'contents -> form.contents.trim.toLowerCase,
      'summary -> Json.stringify(Json.toJson(form.summary)),
      'updated_by_user_id -> createdBy.id
    ).execute()

    findById(Authorization.All, id).getOrElse {
      sys.error("Failed to create item")
    }
  }

  def delete(deletedBy: User, item: Item) {
    DB.withConnection { implicit c =>
      deleteWithConnection(deletedBy, item)(c)
    }
  }

  private[this] def deleteWithConnection(deletedBy: User, item: Item)(
    implicit c: java.sql.Connection
  ) {
    Delete.delete("items", deletedBy.id, item.id)
  }

  def deleteByObjectId(auth: Authorization, deletedBy: User, objectId: String) {
    findByObjectId(auth, objectId).map { item =>
      delete(deletedBy, item)
    }
  }

  def findById(auth: Authorization, id: String): Option[Item] = {
    findAll(auth, id = Some(id), limit = 1).headOption
  }

  def findByObjectId(auth: Authorization, objectId: String): Option[Item] = {
    findAll(auth, objectId = Some(objectId), limit = 1).headOption
  }

  def findAll(
    auth: Authorization,
    id: Option[String] = None,
    ids: Option[Seq[String]] = None,
    q: Option[String] = None,
    objectId: Option[String] = None,
    orderBy: OrderBy = OrderBy("-lower(items.label), items.created_at"),
    limit: Long = 25,
    offset: Long = 0
  ): Seq[Item] = {
    DB.withConnection { implicit c =>
      BaseQuery.
        and(Filters(auth).organizations("items.organization_id", Some("items.visibility")).sql).
        equals("items.id", id).
        optionalIn("items.id", ids).
        and(q.map { v => "items.contents like '%' || lower(trim({q})) || '%' " }).bind("q", q).
        equals("items.object_id", objectId).
        orderBy(orderBy.sql).
        limit(limit).
        offset(offset).
        as(
          parser().*
        )
    }
  }

  private[this] def parser(): RowParser[io.flow.delta.v0.models.Item] = {
    SqlParser.str("id") ~
    io.flow.delta.v0.anorm.parsers.OrganizationSummary.parserWithPrefix("organization") ~
    io.flow.delta.v0.anorm.parsers.Visibility.parser("visibility") ~
    SqlParser.str("summary") ~
    SqlParser.str("label") ~
    SqlParser.str("description").? map {
      case id ~ organization ~ visibility ~ summary ~ label ~ description => {
        io.flow.delta.v0.models.Item(
          id = id,
          organization = organization,
          visibility = visibility,
          summary = Json.parse(summary).as[ItemSummary],
          label = label,
          description = description
        )
      }
    }
  }

}
