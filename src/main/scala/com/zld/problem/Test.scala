package com.zld.problem

object Test {

  def main(args: Array[String]): Unit = {
    val aStar = new AStarStrategy
    val tests = Loader.loadTests("/Users/g.alykov/_dev/zld-color/src/test/resources/test2.txt")
//    println(tests)
    println(aStar.calc3(tests.head))
  }

}
