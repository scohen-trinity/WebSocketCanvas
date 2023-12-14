package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends Tables {
  val profile = slick.jdbc.PostgresProfile
}

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Generals.schema ++ Personals.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Generals
   *  @param itemId Database column item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param text Database column text SqlType(varchar), Length(2000,true) */
  case class GeneralsRow(itemId: Int, text: String)
  /** GetResult implicit for fetching GeneralsRow objects using plain SQL queries */
  implicit def GetResultGeneralsRow(implicit e0: GR[Int], e1: GR[String]): GR[GeneralsRow] = GR{
    prs => import prs._
    GeneralsRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table generals. Objects of this class serve as prototypes for rows in queries. */
  class Generals(_tableTag: Tag) extends profile.api.Table[GeneralsRow](_tableTag, "generals") {
    def * = (itemId, text).<>(GeneralsRow.tupled, GeneralsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(itemId), Rep.Some(text))).shaped.<>({r=>import r._; _1.map(_=> GeneralsRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column item_id SqlType(serial), AutoInc, PrimaryKey */
    val itemId: Rep[Int] = column[Int]("item_id", O.AutoInc, O.PrimaryKey)
    /** Database column text SqlType(varchar), Length(2000,true) */
    val text: Rep[String] = column[String]("text", O.Length(2000,varying=true))
  }
  /** Collection-like TableQuery object for table Generals */
  lazy val Generals = new TableQuery(tag => new Generals(tag))

  /** Entity class storing rows of table Personals
   *  @param itemId Database column item_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int4)
   *  @param text Database column text SqlType(varchar), Length(2000,true) */
  case class PersonalsRow(itemId: Int, userId: Int, text: String)
  /** GetResult implicit for fetching PersonalsRow objects using plain SQL queries */
  implicit def GetResultPersonalsRow(implicit e0: GR[Int], e1: GR[String]): GR[PersonalsRow] = GR{
    prs => import prs._
    PersonalsRow.tupled((<<[Int], <<[Int], <<[String]))
  }
  /** Table description of table personals. Objects of this class serve as prototypes for rows in queries. */
  class Personals(_tableTag: Tag) extends profile.api.Table[PersonalsRow](_tableTag, "personals") {
    def * = (itemId, userId, text).<>(PersonalsRow.tupled, PersonalsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(itemId), Rep.Some(userId), Rep.Some(text))).shaped.<>({r=>import r._; _1.map(_=> PersonalsRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column item_id SqlType(serial), AutoInc, PrimaryKey */
    val itemId: Rep[Int] = column[Int]("item_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column text SqlType(varchar), Length(2000,true) */
    val text: Rep[String] = column[String]("text", O.Length(2000,varying=true))

    /** Foreign key referencing Users (database name personals_user_id_fkey) */
    lazy val usersFk = foreignKey("personals_user_id_fkey", userId, Users)(r => r.id, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Personals */
  lazy val Personals = new TableQuery(tag => new Personals(tag))

  /** Entity class storing rows of table Users
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param username Database column username SqlType(varchar), Length(20,true)
   *  @param password Database column password SqlType(varchar), Length(200,true) */
  case class UsersRow(id: Int, username: String, password: String)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (id, username, password).<>(UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(username), Rep.Some(password))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(varchar), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20,varying=true))
    /** Database column password SqlType(varchar), Length(200,true) */
    val password: Rep[String] = column[String]("password", O.Length(200,varying=true))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
