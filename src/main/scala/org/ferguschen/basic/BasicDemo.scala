package org.ferguschen.basic

/**
 * Created by yudong on 17/3/2.
 */

import java.io.IOException
import java.net.{MalformedURLException, URL}

import scala.math._

//引入math包下的所有函数

object BasicDemo {
  def main(args: Array[String]) {
    println(sqrt(2))
    //    println("素数:" + BigInt.probablePrime(100, scala.util.Random)) //指定100bits的1个素数
    println("Hello".distinct) //不带参数的scala方法通常不用圆括号

    println("类的伴生对象的apply方法用于初始化, 而对象的apply方法有特定用途,如String类型是第i个字符, Array则是第i个元素---")
    println("Hello" (4) + "==" + "Hello".apply(4) + ",  " + BigInt("123456789")); //同:BigInt.apply("123456789"),常用! 无需new

    expressUsage(5)

    //    val res = sum(1 to 5) //错误
    val res1 = sum(1 to 5: _*) //将这个参数当做参数序列处理

  }

  def expressUsage(x: Int): Unit = {
    val s = if (x > 0) 1 else -1 //表达式是有值的
    val s1 = if (x > 0) "positive" else -1 //s1为混合类型, Any
    val s2 = if (x > 0) 1 //相当于val s2 = if(x > 0) 1 else (), s2 为AnyVal类型, ()相当于Unit,表示"无值"的值
    val x0, x1: Double = 2.7 //x0, x1为double类型,初始化为2.7
    val y0 = 7.2
    val y1 = 2.7
    val distance = {
      val dx = x1 - x0
      val dy = y1 - y0
      sqrt(dx * dx + dy * dy)
    } // 块语句的值为块中最后一个表达式的值

    //for循环
    val s3 = "Hello"
    var sum = 0
    for (i <- 0 until s3.length) {
      // 1 to n : 序列1到n,包含n, 1 until n: 序列1到n-1, 不包含n
      sum += s3(i)
    }
    for (ch <- "Hello") sum += ch //更简单的循环
    /** 注: scala并没有提供continue和break, 一般可以用Boolean型变量(守卫)和return来控制循环*
      * 更常用的是: 高级for循环
      */
    print("嵌套for循环, 可以添加多个生成器: ")
    for (i <- 1 to 3; j <- 0 to 2) {
      print(i * 10 + j + " ")
    } //嵌套循环

    print("\n利用守卫,跳出循环: ") //注意,守卫前没有分号
    for (i <- 1 to 3; j <- 0 to 2 if i != j) {
      print(i * 10 + j + " ")
    } //守卫,跳出循环

    //循环体以yield开始, 可以构造集合, 【for推导式】, 其返回类型与第1个生成器类型兼容
    val v0 = for (i <- 1 to 10) yield i % 3
    val v1 = for (c <- "Hello"; i <- 0 to 1) yield (c + i).toChar //返回String = HIeflmlmop
    val v2 = for (i <- 0 to 1; c <- "Hello") yield (c + i).toChar //返回scala.collection.immutable.IndexedSeq[Char] = Vector(H, e, l, l, o, I, f, m, m, p)


    //输入输出
    val name = readLine("Your name:")
    print("your age:")
    val age = readInt()
    printf("Hello, %s! Next year ,your age: %d", name, age + 1)


  }

  /** 默认参数的函数 */
  def decorate(str: String, left: String = "[", right: String = "]") = {
    left + str + right
  }

  /** 可变长参数的函数, 函数得到的args是类型为Seq的参数, 调用的时候,如果是一个区间, 要声明成参数序列 */
  def sum(args: Int*) = {
    var result = 0
    for (arg <- args) result += arg
    result
  }

  def recursiveSum(args: Int*): Int = {
    if (args.length == 0) 0
    else args.head + recursiveSum(args.tail: _*) //可变长度的参数传入
  }

  /**
   * scala异常
   * scala没有受检异常, 不用在方法中声明抛出异常。
   * scala异常的捕获是采用模式匹配
   * 注:无返回值,是过程,不是函数。
   */
  def exceptionDemo(x: Int): Unit = {
    val res = if (x > 0) {
      sqrt(x)
    } else {
      throw new IllegalArgumentException("x should be positive!") //throw表达式返回类型是Nothing, 这样, if...else返回另一个分支类型
    }

    //捕获异常
    val url = "http://horstmann.com/fred-tiny.gif"
    try {
      new URL(url)
    } catch {
      case _: MalformedURLException => println("Bad URL: " + url)
      case ex: IOException => ex.printStackTrace()
    }
  }
}
