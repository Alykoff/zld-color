package com.zolando.problem

import com.zolando.problem.data.{SimpleClient, SimplePaint, TestCase}
import com.zolando.problem.data._

import scala.annotation.tailrec
import scala.io.Source

object Loader {
  def loadTests(filename: String): List[TestCase] = {
    val lines = Source.fromFile(filename).getLines.toList
    val totalTests = lines.head.toInt
    buildTestCases(totalTests, 1, lines.tail, List.empty, None, None, List.empty)
  }

  @tailrec
  private def buildTestCases(
      numOfTests: Int,
      numTest: Int,
      lines: List[String],
      currentClients: List[SimpleClient],
      currentNumOfPaint: Option[Int],
      currentNumOfClient: Option[Int],
      acc: List[TestCase]
  ): List[TestCase] = {
    if (lines.isEmpty) {
      acc :+ TestCase(numTest, currentClients, currentNumOfPaint.get)
    } else if (currentNumOfPaint.isEmpty) {
      buildTestCases(
        numOfTests, numTest, lines.tail, currentClients, Some(lines.head.toInt), currentNumOfClient, acc
      )
    } else if (currentNumOfClient.isEmpty) {
      buildTestCases(
        numOfTests, numTest, lines.tail, currentClients, currentNumOfPaint, Some(lines.head.toInt), acc
      )
    } else if (currentNumOfClient.get == 0) {
      throw new RuntimeException("num of clients is 0")
    } else if (currentClients.size == currentNumOfClient.get) {
      val newTestCase = TestCase(numTest, currentClients, currentNumOfPaint.get)
      buildTestCases(
        numOfTests, numTest + 1, lines, List.empty, None, None, acc :+ newTestCase
      )
    } else {
      val paints = lines.head.trim
        .split(" ")
        .map(x => x.toInt)
        .tail
        .sliding(2, 2)
        .map(x => SimplePaint(
          x(0),
          if (x(1) == 0) PAINT_TYPE_0 else PAINT_TYPE_1
        )).toSet
      val clientNum = currentClients.size + 1
      val newClient = SimpleClient(clientNum, paints)
      buildTestCases(
        numOfTests, numTest, lines.tail, currentClients :+ newClient, currentNumOfPaint, currentNumOfClient, acc
      )
    }
  }

}
