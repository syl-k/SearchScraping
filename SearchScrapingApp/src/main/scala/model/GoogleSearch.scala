package model

import dispatch._
import Defaults._

import scala.collection.mutable.HashMap
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.util.{Failure, Success}
import scala.collection.immutable.$colon$colon
import scala.collection.mutable

/**
  * Created by T_Kikuyama on 2017/08/21.
  */
class GoogleSearch {
    /**
      * Google Developer ConsoleのCustom Search API
      * ここで承認鍵(key)を作成
      * https://developers.google.com/custom-search/json-api/v1/overview?hl=ja
      *
      * Custom Search Engine
      * ここでCustom Search Engine(cx)を作成
      * https://www.google.com/cse/
      *
      * 検索ワード(q)を付与する
      *
      * 参考URL: http://ryutamaki.hatenablog.com/entry/2014/01/18/171640
      */
    val URL = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCSmDmp47joIH9nUnNBR755g5BbbSowQFQ&cx=015039519934210843393:z99odeen7to&q="

    def search(searchWord: String): mutable.HashMap[String, String] = {
        val u = URL + searchWord
        val svc = url(u)
        val future = Http(svc OK dispatch.as.json4s.Json)

        val f = future() // 同期
        val s = f.asInstanceOf[JObject].values.get("items")
        val x = s.getOrElse(0).asInstanceOf[List[Map[String, String]]]

        var resultList: mutable.HashMap[String, String] = mutable.HashMap.empty
        x foreach {
            i => {
                resultList.+=(i.getOrElse("title", "") -> i.getOrElse("link", ""))
            }
        }
        resultList
    }
}
