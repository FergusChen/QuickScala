package org.ferguschen.collection

import java.util

import scala.collection.{immutable, mutable}

/**
 * Created by yudong on 17/3/15.
 */
object MapDemo {
  def main(args: Array[String]) {

    basicMap()
  }

  def basicMap() = {
    val scores = mutable.Map("Alice" -> 50, "Bob" -> 70, "Cindy" -> 98) //可变Map[String,Int]
    val bobsScore = if (scores.contains("Bob")) scores("Bob") else 0 //若键不存在,则抛出异常
    val cindysScore = scores.getOrElse("Cindy", 0) //更常用。而get方法返回的是Option对象
    scores("Fred") = 75 //键存在是修改,键不存在则是添加。 不能修改或添加不可变的Map
    scores += ("Ketty"->89, "Bob"->90) //添加和修改
    scores -= "Alice"  //删除
    val newScores = scores + ("Mate" -> 78, "Janice"->100)
    println(scores)

    //Map遍历, 可以直接用模式匹配(k,v), 也可以获取keySet, values
    for((k,v) <- scores) {
      println(k + "::" + v)
    }

    //scala默认使用Hash表实现Map, 也可以使用平衡树,实现排序Map, 注意没有可变的树形Map, 如要使用,只能用java的TreeMap
    val sortedScores = immutable.SortedMap("Alice" -> 50,"Ketty"->89, "Bob" -> 70, "Cindy" -> 98)
    val linkedScores = mutable.LinkedHashMap("Alice" -> 50,"Ketty"->89, "Bob" -> 70, "Cindy" -> 98)//按插入顺序

  }

  def transformMap() = {
    //java的map转为scala的map
    import scala.collection.JavaConversions.mapAsScalaMap  //方法1
    val scores:mutable.Map[String, Int] =  new util.TreeMap[String, Int]

    import scala.collection.JavaConversions.propertiesAsScalaMap  //方法2
    val props:scala.collection.Map[String, String] = System.getProperties

    //scala的map转为java的map
    import scala.collection.JavaConversions.mapAsJavaMap
    import java.awt.font.TextAttribute._
    val attrs = Map(FAMILY -> "Serif", SIZE -> 12) //用到的键属于TextAttribute
    val font = new java.awt.Font(attrs)  //该方法返回java的map

  }

  def basicTuple() ={
    val t:Tuple3[Int, Double, String] = (1, 3.14, "Fred")
    val (first, second, _) = t //模式匹配,获取first, second, 无需匹配的直接用_代替
    val par = "New York".partition(_.isUpper) //partition方法返回Tuple, ("NY", "ew ork")
    //使用元组可以将多个值绑定起来(zip也类似)
  }
}
