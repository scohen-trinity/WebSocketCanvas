package models

import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext
import models.Tables._
import scala.concurrent.Future
import org.mindrot.jbcrypt.BCrypt

class MessageListDatabaseModel(db: Database)(implicit ec:  ExecutionContext) {
    def validateUser(username: String, password: String): Future[Option[Int]] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.map(userRows => userRows.headOption.flatMap{
            userRow => if (BCrypt.checkpw(password, userRow.password)) Some(userRow.id) else None
        })
    }

    def getUserId(username: String): Future[Option[Int]] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.map(userRows => userRows.headOption.flatMap{
            userRow => Some(userRow.id)
        })
    }

    def createUser(username: String, password: String): Future[Boolean] = {
        val matches = db.run(Users.filter(userRow => userRow.username === username).result)
        matches.flatMap { userRows =>
            if(!userRows.nonEmpty) {
                db.run(Users += UsersRow(-1, username, BCrypt.hashpw(password, BCrypt.gensalt())))
                    .map(addCount => addCount > 0)
            } else Future.successful(false)
        }
    }

    def getGeneralMessages(username: String): Future[Seq[String]] = {
        db.run(
            (for {
                message <- Generals
            } yield {
                message.text
            }).result
        )
    }

    def getPersonalMessages(username: String): Future[Seq[String]] = {
        db.run(
            (for {
                user <- Users if user.username === username
                message <- Personals if message.userId === user.id
            } yield {
                message.text
            }).result
        )
    }

    def addGeneralMessage(message: String): Future[Int] = {
        db.run(Generals += GeneralsRow(-1, message))
    }

    def addPersonalMessage(userId: Int, message: String): Future[Int] = {
        val insertAction = Personals += PersonalsRow(-1, userId, message)
        db.run(insertAction).map(_ => 1)
    }

    def clearGeneralMessages(): Future[Int] = {
        ???
    }

    def clearPersonalMessages(): Future[Int] = {
        ???
    }
}