package model

import java.net.URLDecoder
import javax.net.ssl.SSLHandshakeException

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

import scala.collection.mutable

/**
  * Created by T_Kikuyama on 2017/08/21.
  */
class Scraping {

    def search(searchWord: String): mutable.HashMap[String, String] = {
        val g = new GoogleSearch()
        g.search(searchWord)
    }

    def start(searchResult: mutable.HashMap[String, String]): mutable.HashMap[String, List[String]] = {
        var m: mutable.HashMap[String, List[String]] = mutable.HashMap.empty
        searchResult foreach {
            case (key, value) =>
                m.+=(key -> siteScraping(value))
        }
        m
    }

    def siteScraping(url: String): List[String] = {
        val du: String = URLDecoder.decode(url, "UTF-8")
        println("検索URL:" + du)

        var rList: List[String] = List.empty

        try {
            val browser = JsoupBrowser()
            val doc = browser.get(du)

            var urlList: Iterable[String] = Iterable.empty
            for {
                l <- doc.body >?> attrs("href")("a")
            } {
                urlList = l
            }

            urlList foreach {
                l => {
                    if (l.indexOf("http") == 0 ) {
                        rList = rList :+ l
                    }
                }
            }
        } catch {
            case e: SSLHandshakeException => println(e)
        }
        rList
    }
}