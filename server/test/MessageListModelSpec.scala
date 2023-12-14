import org.scalatestplus.play._
import models._

class MessageListModelSpec extends PlaySpec {
    "MessageListInMemoryModel" must {
        "do valid login for default user" in {
            MessageListInMemoryModel.validateUser("mlewis", "prof") mustBe (true)
        }

        "reject login with wrong password" in {
            MessageListInMemoryModel.validateUser("mlewis", "professor") mustBe (false)
        }

        "reject login with wrong username" in {
            MessageListInMemoryModel.validateUser("Mark", "prof") mustBe (false)
        }

        "reject login with wrong username and password" in {
            MessageListInMemoryModel.validateUser("Mark", "professor") mustBe (false)
        }

        "get correct default general tasks" in {
            MessageListInMemoryModel.getGeneralMessages("mlewis") mustBe (List("Make videos", "eat", "sleep", "code", "Do the project", "sleep"))
        }

        "get correct default personal tasks for mlewis" in {
            MessageListInMemoryModel.getPersonalMessages("mlewis") mustBe (List("Hi Mark"))
        }

        "create new user with no personal messages and the same general messages as everyone else" in {
            MessageListInMemoryModel.createUser("Mike", "password") mustBe (true)
            MessageListInMemoryModel.getPersonalMessages("Mike") mustBe (Nil)
            MessageListInMemoryModel.getGeneralMessages("Mike") mustBe (List("Make videos", "eat", "sleep", "code", "Do the project", "sleep"))
        }

        "create new user with an existing username" in {
            MessageListInMemoryModel.createUser("mlewis", "something") mustBe (false)
        }

        "add new general message for default user" in {
            MessageListInMemoryModel.addGeneralMessage("testing")
            MessageListInMemoryModel.getGeneralMessages("mlewis") must contain ("testing")
        }

        "add new general message for new user" in {
            MessageListInMemoryModel.addGeneralMessage("something new")
            MessageListInMemoryModel.getGeneralMessages("Mike") must contain ("something new")
        }

        "add new personal message for default user" in {
            MessageListInMemoryModel.addPersonalMessage("oooo spooky", "web", "mlewis")
            MessageListInMemoryModel.getPersonalMessages("mlewis") must contain ("oooo spooky. Message sent from web")
        }

        "add new personal message for new user" in {
            MessageListInMemoryModel.addPersonalMessage("Hi Mike", "web", "Mike")
            MessageListInMemoryModel.getPersonalMessages("Mike") must contain ("Hi Mike. Message sent from web")
        }

    }
}