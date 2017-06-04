package com.zolando.problem

package object data {
  case class TestCase(var numOfTest: Int, var clients: List[SimpleClient], var numOfPaints: Int)

  case class SimplePaint(paintNum: Int, paintType: PaintType)
  case class SimpleClient(clientNum: Int, paints: Set[SimplePaint])
  case class Client(clientNum: Int, favorites: Set[Paint])
  case class Paint(
    paintNum: Int,
    paintType: PaintType,
    clients: Set[Int],
    nextClients: Set[Int],
    nextPaints: Option[(Paint, Paint)]
  ) extends Ordered[Paint] {
    def compare(that: Paint): Int =
      if (this.paintNum < that.paintNum) 1
      else if (this.paintNum > that.paintNum) -1
      else if (this.paintType == PAINT_TYPE_0 && that.paintType == PAINT_TYPE_1) 1
      else if (this.paintType == PAINT_TYPE_1 && that.paintType == PAINT_TYPE_0) -1
      else 0

    override def toString(): String = {
      return "P[" + paintNum + "; " + paintType + "];"
    }
  }

  case class PathPaint(
    paint: Paint,
    path: Set[Paint],
    foundClients: Set[Int],
    notFoundClients: Set[Int],
    weight: Int
  ) extends Ordered[PathPaint] {
    def compare(that: PathPaint): Int = that.weight compare this.weight
  }


  trait PaintType
  case class _P0() extends PaintType
  case class _P1() extends PaintType
  // TODO enum ???
  implicit val PAINT_TYPE_0: _P0 = _P0()
  implicit val PAINT_TYPE_1: _P1 = _P1()

}
