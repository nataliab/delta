package db

import java.util.UUID

import io.flow.delta.v0.models.EventType
import io.flow.play.util.Constants
import io.flow.test.utils.FlowPlaySpec

class EventsDaoSpec extends FlowPlaySpec with Helpers {

  "create" in {
    val project = createProject()
    val id = eventsDao.create(Constants.SystemUser, project.id, EventType.Info, "test", ex = None)
    val event = eventsDao.findById(id).getOrElse {
      sys.error("Failed to create event")
    }
    event.`type` must be(EventType.Info)
    event.summary must be("test")
    event.error must be(None)
  }

  "findById" in {
    val event = createEvent()
    eventsDao.findById(event.id).map(_.id) must be(
      Some(event.id)
    )

    eventsDao.findById(UUID.randomUUID.toString) must be(None)
  }

  "findAll by ids" in {
    val event1 = createEvent()
    val event2 = createEvent()

    eventsDao.findAll(ids = Some(Seq(event1.id, event2.id))).map(_.id).sorted must be(
      Seq(event1.id, event2.id).sorted
    )

    eventsDao.findAll(ids = Some(Nil)) must be(Nil)
    eventsDao.findAll(ids = Some(Seq(UUID.randomUUID.toString))) must be(Nil)
    eventsDao.findAll(ids = Some(Seq(event1.id, UUID.randomUUID.toString))).map(_.id) must be(Seq(event1.id))
  }

  "findAll by projectId" in {
    val project1 = createProject()
    val project2 = createProject()

    val event1 = createEvent(project1)
    val event2 = createEvent(project2)
    val ids = Seq(event1.id, event2.id)

    eventsDao.findAll(ids = Some(ids), projectId = Some(project1.id)).map(_.id).sorted must be(
      Seq(event1.id)
    )

    eventsDao.findAll(ids = Some(ids), projectId = Some(project2.id)).map(_.id).sorted must be(
      Seq(event2.id)
    )

    eventsDao.findAll(projectId = Some(createTestKey())) must be(Nil)
  }

  "findAll by numberMinutesSinceCreation" in {
    val event = createEvent()

    eventsDao.findAll(ids = Some(Seq(event.id)), numberMinutesSinceCreation = Some(10)).map(_.id) must be(Seq(event.id))

    DirectDbAccess.setCreatedAt("events", event.id, -15)
    eventsDao.findAll(ids = Some(Seq(event.id)), numberMinutesSinceCreation = Some(10)) must be(Nil)
  }

}
