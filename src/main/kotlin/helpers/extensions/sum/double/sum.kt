package helpers.extensions.sum.double

fun sum(over: IntRange, op: (Int) -> Double): Double = over.sumByDouble(op)
fun sum(to: Int, op: (Int) -> Double): Double = sum(0 until to, op)
