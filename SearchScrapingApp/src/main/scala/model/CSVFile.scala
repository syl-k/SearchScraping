package model

import java.io.{PrintWriter, Serializable}
import java.text.SimpleDateFormat
import java.util.Date

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.util.control.Breaks._

/**
  * Created by T_Kikuyama on 2017/08/22.
  */
class CSVFile {

    def csvHeader: List[String] = List("順位,タイトル,URL,外部リンク一覧,a8,at,afb,ja,xmax,vc,felmat,adebis,webanntena,adplan")

    /**
      * ファイル読み込み.
      */
    def outputScrapingData(keyword: String, searchResult: mutable.HashMap[String, String],
                           scrapingResult: mutable.HashMap[String, List[String]]): Unit = {
        val outputFileName = getOutputFileName(keyword)
        outputDataToCSV(csvHeader, searchResult, scrapingResult, outputFileName)
    }

    /**
      * 出力ファイル名取得.
      * @param keyword 検索キーワード
      * @return        出力ファイル名
      */
    def getOutputFileName(keyword: String): String = {
        val outputDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date)
        "検索結果_" + keyword + "_" + outputDate + ".csv"
    }

    /**
      * ファイル出力.
      *
      * @param outputFileName 出力ファイル名
      */
    def outputDataToCSV(header: List[String], searchResult: mutable.HashMap[String, String],
                        scrapingResult: mutable.HashMap[String, List[String]], outputFileName: String): Unit = {
        val outFile = new PrintWriter(outputFileName, "Shift_JIS")
        outFile.write("%s\n" format header.mkString(","))

        var dataList: Array[String] = Array[String]()
        var linkCheckList: Array[String] = Array("", "", "", "", "", "", "", "", "", "")
        var i: Int = 1

        searchResult foreach {
            case (title, url) =>
                dataList :+= i + "位"
                dataList :+= title
                dataList :+= url
                dataList :+= ""

                val linkList: List[String] = scrapingResult.getOrElse(title, List.empty)
                linkList foreach {
                    link => {
                        val point = checkSpecificLink(link)
                        if (point > -1) {
                            linkCheckList.update(point, "○")
                        }
                    }
                }
                linkCheckList foreach {
                    v => dataList :+= v
                }
                println(dataList)
                outFile.write("%s\n" format dataList.mkString(","))

                i = i + 1
                dataList = Array[String]()
                linkCheckList = Array("", "", "", "", "", "", "", "", "", "")
        }
        outFile.close()
    }

    def checkSpecificLink(link: String): Int = {
        var r: Int = -1
        if (link.indexOf("http://px.a8.net") == 0) {
            r = 0
        } else if (link.indexOf("http://h.accesstrade.net") == 0) {
            r = 1
        } else if (link.indexOf("https://track.affiliate-b.com") == 0) {
            r = 2
        } else if (link.indexOf("http://click.j-a-net.jp") == 0) {
            r = 3
        } else if (link.indexOf("http://track.xmax.jp") == 0) {
            r = 4
        } else if (link.indexOf("http://ck.jp.ap.valuecommerce.com") == 0) {
            r = 5
        } else if (link.indexOf("https://t.felmat.net") == 0) {
            r = 6
        } else if (link.indexOf("http://ac.ebis.ne.jp") == 0) {
            r = 7
        } else if (link.indexOf("webanntena") == 0) {
            r = 8
        } else if (link.indexOf("http://r.advg.jp") == 0) {
            r = 9
        }
        r
    }
}
