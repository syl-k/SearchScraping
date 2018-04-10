import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}


/**
  * Created by T_Kikuyama on 2017/08/21.
  */
object SearchScraping extends JFXApp {
    val resource = getClass.getResource("view/SearchScraping.fxml")
    val root = FXMLView(resource, NoDependencyResolver)

    stage = new JFXApp.PrimaryStage() {
        title = "Google検索結果スクレイピング"
        scene = new Scene(root)
    }
}