package controller

import java.net.URLEncoder

import model.{CSVFile, Scraping}

import scala.collection.mutable
import scalafx.Includes._
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml

/**
  * Created by T_Kikuyama on 2017/08/21.
  */
@sfxml
class SearchScrapingController(private val startScrapingButton: Button, private val searchText: TextField, private val logArea: TextArea) {

    startScrapingButton.onMouseClicked = (e: MouseEvent) => {
        logArea.text = "開始。\n"
        startScraping()
    }

    def startScraping(): Unit = {
        if (searchText.text().isEmpty) {
            logArea.text = "検索ワードを入力してください。\n"
        } else {
            val keyword: String = URLEncoder.encode(searchText.text(), "UTF-8")

            val s = new Scraping()
            val searchResult: mutable.HashMap[String, String] = s.search(keyword)
            val scrapingResult: mutable.HashMap[String, List[String]] = s.start(searchResult)

            val c = new CSVFile()
            c.outputScrapingData(searchText.text(), searchResult, scrapingResult)
            logArea.text = "完了しました。\n"
        }
    }
}
