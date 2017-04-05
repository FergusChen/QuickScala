package org.ferguschen.oo

import java.util.Date

import scala.beans.BeanProperty
import scala.collection.mutable.ArrayBuffer

/**
 * Created by yudong on 17/3/21.
 * 类中的字段都自动生成getter和setter,但仍然可以定制getter和setter,以替换掉字段的定义 "统一访问原则"
 * scala自动生成的getter和setter并不符合javaBean规范,但可以用@BeanProperty注解生成JavaBeans的getXxx/setXxx方法
 * 每个类都有一个主要构造器, 该构造器与类定义"交织"在一起,参数即类字段。可以这样理解:在scala中,类也接受参数,就像方法一样
 * 辅助构造器是可选的,可以有任意多个, 辅助构造器的名字都是this
 *
 */
object ClassDemo {
  def main(args: Array[String]) {
    val counter = new Counter
    counter.increment() //set带括号
    println(counter.current) //get不带括号, 是一个良好风格

    val chatter = new Network
    val myFace = new Network
    val fred = chatter.join("Fred")
    val wilma = chatter.join("Wilma")
    fred.contacts += wilma   //同一个Network对象可以添加相同的Member对象, 因为其类型都是chatter.Member

    val barney = myFace.join("Barney")
//    fred.contacts += barney  //出错, 因为barney的类型是myFace.Member, 不是chatter.Member, 虽然都是Member,但属于不同的类
    //在Network示例中,就是可以在各自的网络中添加成员, 但不能跨网添加成员. 如果不想要这种细粒度的效果, 有两种方法:
    //1. 将嵌套类定义在Network的伴生对象
    //2. 嵌套类用类型投影来定义ArrayBuffer,指定是任何Network的Member val contacts = new ArrayBuffer[Network#Member]
  }
}

/**
 * scala类默认是public的
 * 字段自动生成getter和setter方法(var类型)
 * 这个例子就是自定义getter和setter,即private + current和increment
 * scala的私有字段有两种:
 * 1. 所有本类对象,在类定义中访问 (private)
 * 2. 只能在类定义中直接访问, 而不能以"本类对象.属性"的方法访问
 */
class Counter{
  private var value = 0 //自动生成private的getter和setter。这是个很好的例子:对象不能直接修改value,只能用increment进行更新
  private[this] var age = 20
  def increment() { value += 1}
  def current = value //这是一个方法,用.current()调用是语法错误
  def isLess(other:Counter) = value < other.value //合法
//  def isAgeLess(other:Counter) = age < other.age //不合法
}

class Message{
  val timeStamp = new Date() //val类型,只生成final的getter,没有setter, 且timeStamp在对象生成时构造
  @BeanProperty var text:String = _   //这样会生成4个方法,scala的两个,javaBean的两个。 必须是abstract的类,或实现text,不然的话就不能用@BeanProperty注解。
}

//class Person(@BeanProperty var text:String) //在主构造器中使用BeanProperty
//class Person(val name:String="", val age:Int = 0){} //主构造器直接在类名之后, 可以带默认值。如果没有参数,就是无参构造函数。
// 主构造方法会执行类定义中的所有语句
//class Person private(val id:Int){}  //私有化主构造器, 这样就只能通过辅助构造器进行初始化
/**
 * 一个类如果没有显式定义主构造器,就有一个无参主构造器
 * 定义辅助构造器,都是用this定义,但辅助构造器需要以其它构造器为基础
 */
class Person{
  private var name = ""
  private var age = 0
  //辅助构造器
  def this(name:String){
    this()
    this.name = name
  }
  //辅助构造器
  def this(name:String, age:Int){
    this(name)
    this.age = age
  }
}

/**
 * 在java中, 几乎可以在任何语法结构中内嵌任何语法结构。 当然, 可以嵌套类
 */

class Network{
  //嵌套类, 一个社交网络的成员
  class Member(val name:String){
    val contacts = new ArrayBuffer[Member]()

  }

  private val members = new ArrayBuffer[Member]()
  def join(name:String) = {
    val m = new Member(name)
    members += m
    m
  }

}
