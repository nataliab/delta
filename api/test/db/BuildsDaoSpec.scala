package db

import io.flow.delta.v0.models.Status
import io.flow.postgresql.Authorization
import io.flow.test.utils.FlowPlaySpec

class BuildsDaoSpec extends FlowPlaySpec with Helpers {

  "create" in {
    val project = createProject()
    val dockerfile = "./Dockerfile"
    val config = createBuildConfig(project).copy(name = "root", dockerfile = dockerfile)
    val build = buildsWriteDao.upsert(systemUser, project.id, Status.Enabled, config)
    build.project.id must be(project.id)
    build.name must be("root")
  }

  "delete" in {
    val build = upsertBuild()
    buildsWriteDao.delete(systemUser, build)
    buildsDao.findById(Authorization.All, build.id) must be(None)
  }

  "findById" in {
    val build = upsertBuild()
    buildsDao.findById(Authorization.All, build.id).map(_.id) must be(
      Some(build.id)
    )

    buildsDao.findById(Authorization.All, createTestKey) must be(None)
  }

  "findByProjectIdAndName" in {
    val project = createProject()

    val apiConfig = createBuildConfig(project).copy(name = "api")
    val api = buildsWriteDao.upsert(systemUser, project.id, Status.Enabled, apiConfig)

    val wwwConfig = createBuildConfig(project).copy(name = "www")
    val www = buildsWriteDao.upsert(systemUser, project.id, Status.Enabled, wwwConfig)

    buildsDao.findByProjectIdAndName(Authorization.All, project.id, "api").map(_.id) must be(Some(api.id))
    buildsDao.findByProjectIdAndName(Authorization.All, project.id, "www").map(_.id) must be(Some(www.id))
    buildsDao.findByProjectIdAndName(Authorization.All, project.id, "other") must be(None)
  }

  "findAll by ids" in {
    val build1 = upsertBuild()
    val build2 = upsertBuild()

    buildsDao.findAll(Authorization.All, ids = Some(Seq(build1.id, build2.id))).map(_.id).sorted must be(
      Seq(build1.id, build2.id).sorted
    )

    buildsDao.findAll(Authorization.All, ids = Some(Nil)) must be(Nil)
    buildsDao.findAll(Authorization.All, ids = Some(Seq(createTestKey))) must be(Nil)
    buildsDao.findAll(Authorization.All, ids = Some(Seq(build1.id, createTestKey))).map(_.id) must be(Seq(build1.id))
  }

  "findAll by projectId" in {
    val project1 = createProject()
    val project2 = createProject()

    val build1 = upsertBuild(project1)
    val build2 = upsertBuild(project2)

    buildsDao.findAll(Authorization.All, projectId = Some(project1.id)).map(_.project.id).distinct must be(
      Seq(project1.id)
    )

    buildsDao.findAll(Authorization.All, projectId = Some(project2.id)).map(_.project.id).distinct must be(
      Seq(project2.id)
    )

    buildsDao.findAll(Authorization.All, projectId = Some(createTestKey())) must be(Nil)
  }

  "findAllByProjectId" in {
    val project = createProject()
    val build1 = upsertBuild(project)
    val build2 = upsertBuild(project)

    buildsDao.findAllByProjectId(Authorization.All, project.id).toSeq.map(_.project.id).distinct must be(
      Seq(project.id)
    )
  }

  "authorization for builds" in {
    val org = createOrganization()
    val project = createProject(org)
    val user = createUserReference()
    createMembership(createMembershipForm(org = org, user = user))

    val build = upsertBuild(project)(createBuildConfig(project), user = user)

    buildsDao.findAll(Authorization.PublicOnly, ids = Some(Seq(build.id))) must be(Nil)
    buildsDao.findAll(Authorization.All, ids = Some(Seq(build.id))).map(_.id) must be(Seq(build.id))
    buildsDao.findAll(Authorization.Organization(org.id), ids = Some(Seq(build.id))).map(_.id) must be(Seq(build.id))
    buildsDao.findAll(Authorization.Organization(createOrganization().id), ids = Some(Seq(build.id))) must be(Nil)
    buildsDao.findAll(Authorization.User(user.id), ids = Some(Seq(build.id))).map(_.id) must be(Seq(build.id))
    buildsDao.findAll(Authorization.User(createUser().id), ids = Some(Seq(build.id))) must be(Nil)
  }

}
