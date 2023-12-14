package models

import collection.mutable

object MessageListInMemoryModel {
    private val users = mutable.Map[String, String]("mlewis" -> "prof", "web" -> "apps")
    private var generalMessages: List[String] = List("Make videos", "eat", "sleep", "code", "Do the project", "sleep")
    private var personalMessages = mutable.Map[String, List[String]]("mlewis" -> List("Hi Mark"), "web" -> List("Hi Web"))

    def validateUser(username: String, password: String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }

    def createUser(username: String, password: String): Boolean = {
        if(users.contains(username)) false else {
            users += (username -> password)
            true
        }
    }

    def getGeneralMessages(username: String): Seq[String] = {
        generalMessages
    }

    def getPersonalMessages(username: String): Seq[String] = {
        personalMessages.get(username).getOrElse(Nil)
    }

    def addGeneralMessage(message: String): Unit = {
        generalMessages = message :: generalMessages
    }

    def addPersonalMessage(message: String, sender: String, receiver: String): Unit = {
        val signedMessage = message + ". Message sent from " + sender
        personalMessages(receiver) = signedMessage :: personalMessages.get(receiver).getOrElse(Nil) 
    }

    def clearGeneralMessages(): Unit = {
        generalMessages = List()
    }

    def clearPersonalMessages(): Unit = {
        personalMessages = mutable.Map[String, List[String]]()
    }
}
