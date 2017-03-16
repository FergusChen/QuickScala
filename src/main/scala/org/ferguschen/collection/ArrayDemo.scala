package org.ferguschen.collection

import java.util

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by yudong on 17/3/8.
 * 长度固定:Array, 长度不固定: ArrayBuffer
 * 初始化时无需使用new, 可以使用伴生对象的Apply方法 ()
 * 用()访问元素
 * 用for(elem <- arr) 遍历元素
 * 用for(elem <- arr if ...) yield... 将原数组转型为新数组
 * Scala数组和Java数组可以互操作, 用ArrayBuffer, 使用scala.collection.JavaConversions中的转换函数
 */
object ArrayDemo {
  def main(args: Array[String]) {
    initArray()
  }

  def initArray(): Unit = {
    //定长数组
    val nums = new Array[Int](10) //大小为10, 必须声明类型
    val s = Array("hello", "world") //推断是2个元素的字符串数组
    s(0) = "goodbye" //元素取值和赋值
    println(s)
    //变长数组
    val b = ArrayBuffer[Int]()
    b += 1 //b的末尾添加元素1
    b += (1, 3, 5, 2) //b的末尾添加多个元素
    b ++= nums //b的末尾添加数组
    b.trimEnd(3) //移除b末尾的5个元素
    /**在数组末尾添加或移除元素是高效的, 但中间位置插入和删除效率很低, 需要平移元素*/
    b.insert(1, 6) //1的位置插入元素6
    b.insert(2, 6, 7, 9)
    b.remove(2)
    b.remove(2, 3) //位置2移除3个元素
    println(b)

    //可变数组与不可变数组的转换
    val arr1 = b.toArray
    val arr2 = nums.toBuffer

    for ( i <- 0 until b.length){ //0 到 b.length - 1, 不包含b.length
      println(i + ":" + b(i))
    }
    0 until(b.length, 2) //步长为2的序列 0, 2, 4, 6...
    (0 until b.length).reverse //倒序序列

    val res = for (elem <- b if elem % 2 == 0) yield  2 * elem  //for(...) yield表达式由原始集合创建新集合,用守卫过滤
    //for + yield 相当于 filter + map, 两种方案都可以。

    println(b.mkString(" and "))
    println(b.mkString("{", ", ", "}"))

    /**scala和java的集合类型的转换*/
    import scala.collection.JavaConversions.bufferAsJavaList
    val command = ArrayBuffer("ls", "-al", "/home/yudong")
    val pb = new ProcessBuilder(command) //这一步隐式转换成了ProcessBuilder需要的List类型
    import scala.collection.JavaConversions.asScalaBuffer
    val cmd:mutable.Buffer[String] = pb.command()  //这里隐式转换为Buffer, 不能直接转为ArrayBuffer

    val jArray = new util.ArrayList[Int]()
    import scala.collection.JavaConverters._
    val sArray = jArray.asScala  //这里也是转为Buffer
  }
  /**常见算法*/
  def arrayAlgo(): Unit ={
    val arr3 = Array(1, 7, 2, 9)
    val sum = arr3.sum
    val max = arr3.max
    val bSorted1 = arr3.sortWith((x,y)=>x > y)  //降序排序, 并不改变原数组
    print("sortWith: ")
    bSorted1.map(x => print(x + ","))
    println()
    val bSorted2 = arr3.sorted(math.Ordering[Int]) //添加.reverse降序
    print("sorted: ")
    bSorted2.map(x => print(x + ","))

    scala.util.Sorting.quickSort(arr3) //此时arr3是排序后的结果

  }

  def dimArray(): Unit ={
    val matrix = Array.ofDim[Double](3, 4) // 3行4列的二维数组
    matrix(0)(2) = 78.2 // 0行2列元素赋值
    //创建不规则二维数组
    val triangle = new Array[Array[Int]](10)
    for (i <- 0 until triangle.length){
      triangle(i) = new Array[Int](i + 1)
    }
  }

  /**
   * 移除第1个负数之外的所有负数
   * (ArrayBuffer的尾部删除效率较高)
   * @param arr  待处理的数组
   */
  def removeNegativeExceptFirst(arr:ArrayBuffer[Int]) ={
    var first = true
    //获取所有非负数和第1个负数的index
    val indexes = for(i <- 0 until arr.length if first || arr(i) >= 0) yield {
      if(arr(i) < 0) {
        first = false
      }
      i
    }

    for(i <- 0 until indexes.length) arr(i) = arr(indexes(i))
    arr.trimEnd(arr.length - indexes.length)
  }
}
