package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def product(prodType: String, prodNum: Int) = Action {
    Ok(s"Product type is: $prodType, product number is: $prodNum")
  }

}
