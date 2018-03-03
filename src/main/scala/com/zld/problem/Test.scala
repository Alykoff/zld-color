package com.zld.problem

object Test {

  def main(args: Array[String]): Unit = {
    val aStar = new AStarStrategy
    val tests = Loader.loadTests("/Users/sherlock/dev/projects/zld/scala-test/test2.txt")
//    println(tests)
    println(aStar.calc3(tests.head))
  }

}
