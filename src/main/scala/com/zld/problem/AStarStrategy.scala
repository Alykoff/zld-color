package com.zld.problem

import com.zld.problem.data.{Paint, PaintType, PathPaint, SimpleClient, TestCase}
import com.zld.problem.data._
import com.zld.problem.data.Constants._

import scala.annotation.tailrec
import scala.collection.mutable

class AStarStrategy {

  case class PaintBuilder(
    paintNum: Int,
    paintType: PaintType,
    clients: Set[Int]
  )

  def calc3(testCase: TestCase) = {
    val simpleClients = testCase.clients
    val numOfPaints = testCase.numOfPaints
    val clients = testCase.clients
    val paints = buildPaints(simpleClients, numOfPaints)
    val queue = mutable.PriorityQueue[PathPaint]()
    val firstPaths = buildFirstPaths(paints, clients.map(_.clientNum).toSet)
    firstPaths.foreach(path => queue += path)
    calc2(queue, clients.size) match {
      case None => println("Not found")
      case xs => println(xs)
    }
  }

  @tailrec
  private def calc2(queue: mutable.PriorityQueue[PathPaint], numOfClients: Int): Option[Set[Paint]] = {
    if (queue.isEmpty) {
      None
    } else {
      val priorityPath = queue.dequeue()

      val path = priorityPath.path
      val paint = priorityPath.paint
      val foundClients = priorityPath.foundClients
      val notFoundClients = priorityPath.notFoundClients
      val weight = priorityPath.weight

      val newFoundClients = foundClients ++ paint.clients
      val newPath = path + paint
      val newNotFoundClients = notFoundClients.diff(newFoundClients)
      // гарантируем что обойдем всех клиентов
      if ((paint.nextClients ++ newFoundClients).size != numOfClients) {
        calc2(queue, numOfClients)
      } else if (newNotFoundClients.isEmpty) {
        Some(buildMinimumPath(paint, newPath))
      } else if (paint.nextPaints.isEmpty) {
        calc2(queue, numOfClients)
      } else {
        val paintNext1 = paint.nextPaints.get._1
        val paintNext2 = paint.nextPaints.get._2
        val pathNext1 = PathPaint(paintNext1, newPath, newFoundClients, newNotFoundClients, weight)
        queue += pathNext1

        val newClientsFromPaint1 = paintNext1.clients.intersect(newNotFoundClients)
        val newClientsFromPaint2 = paintNext2.clients.intersect(newNotFoundClients)
        // оптимизация
        if (paintNext2.clients.nonEmpty
            || newClientsFromPaint2.isEmpty
            || newClientsFromPaint1.diff(newClientsFromPaint2).isEmpty
        ) {
          val pathNext2 = PathPaint(paintNext2, newPath, newFoundClients, newNotFoundClients, weight + weightOfMatte)
          queue += pathNext2
        }

        calc2(queue, numOfClients)
      }
    }
  }

  @tailrec
  final def buildMinimumPath(paint: Paint, acc: Set[Paint]): Set[Paint] = {
    if (paint.nextPaints.isEmpty) {
      acc
    } else {
      val minPaint = paint.nextPaints.get._1
      buildMinimumPath(minPaint, acc + minPaint)
    }
  }

  private def buildFirstPaths(paints: List[Paint], clients: Set[Int]): List[PathPaint] = {
    List(
      PathPaint(
        paints.filter(x => x.paintNum == firstPaintNum && x.paintType == PAINT_GLOSSY).head,
        Set.empty,
        Set.empty,
        clients,
        weightOfGlossy
      ),
      PathPaint(
        paints.filter(x => x.paintNum == firstPaintNum && x.paintType == PAINT_MATTE).head,
        Set.empty,
        Set.empty,
        clients,
        weightOfMatte
      )
    )
  }

  private def buildPaints(simpleClients: List[SimpleClient], numOfPaints: Int): List[Paint] = {
    def getClientsWithPaint(paintNum: Int, paintType: PaintType) = {
      simpleClients.filter(
          _.paints.exists(
            x => x.paintNum == paintNum && x.paintType == paintType
          )
      ).map(_.clientNum)
      .toSet
    }
    val paintInBuildForm = for (paintNum <- firstPaintNum to numOfPaints) yield {
      List(
        PaintBuilder(paintNum, PAINT_GLOSSY, getClientsWithPaint(paintNum, PAINT_GLOSSY)),
        PaintBuilder(paintNum, PAINT_MATTE, getClientsWithPaint(paintNum, PAINT_MATTE))
      )
    }
    val paints = paintInBuildForm.foldRight (List.empty[List[Paint]]) ((builders, acc) => {
      val (prevPaintsOption, nextClients) =
        if (acc.isEmpty) {
          (None, Set.empty[Int])
        } else {
          val prevPaints = acc.last
          (
            Some(acc.last.head, acc.last(1)),
            prevPaints.flatMap(x => x.nextClients ++ x.clients).toSet
          )
        }

      val newPaints = builders.map(builder => {
        Paint(
          builder.paintNum,
          builder.paintType,
          builder.clients,
          nextClients,
          prevPaintsOption
        )
      })

      acc :+ newPaints
    }).flatten

    paints
  }

  private def removeUnusedClients(clients: List[Client]): List[Client] = {
    def isAnyDoubledPaintNum(client: Client): Boolean = {
      val map = client
        .favorites
        .map(_.paintNum)
        .groupBy(x => x)
        .map(x => x._2.size)
        .toList
      !map.exists(x => x > 1)
    }

    clients.filter(isAnyDoubledPaintNum)
  }
}

