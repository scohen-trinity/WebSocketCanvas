import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory

class MessageList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
    "Message List 1" must {
        "login and access functions" in {
            go to s"http://localhost:$port/login1"
            pageTitle mustBe "Login Page for Task 5"
            find(cssSelector("h2")).isEmpty mustBe false
            find(cssSelector("h2")).foreach(e => e.text mustBe "Login Page for Sam Cohen Task 5")
            click on "username-login"
            textField("username-login").value = "mlewis"
            click on "password-login"
            pwdField(id("password-login")).value = "prof"
            submit()
            eventually {
                pageTitle mustBe "Message Lists"
                find(cssSelector("h2")).isEmpty mustBe false
                find(cssSelector("h2")).foreach(e => e.text mustBe "General Messages")
                findAll(cssSelector("li")).toList.map(_.text) mustBe List("something new", "testing", "Make videos", "eat", "sleep", "code", "Do the project", "sleep", "oooo spooky. Message sent from web", "Hi Mark")
            }
        }
    }
}