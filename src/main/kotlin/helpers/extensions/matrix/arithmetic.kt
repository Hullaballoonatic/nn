package helpers.extensions.matrix

import helpers.errors.SizeMismatch
import matrix.Matrix
import vector.Vector
import vector.times

operator fun Matrix.times(v: Vector): Vector = when {
    v.size != n -> throw SizeMismatch("M*v", "M.n=$n, v=${v.size}", "M.n == v")
    else -> Vector(m) { v * row(it) }
}

val Matrix.A get() = this

operator fun Matrix.times(B: Matrix): Matrix = Matrix.multiply(this, B, false, false)
operator fun Matrix.times(s: Int): Matrix = copyOf().apply { scale(s.toDouble()) }
operator fun Matrix.times(s: Double): Matrix = copyOf().apply { scale(s) }
operator fun Int.times(A: Matrix): Matrix = A.copyOf().apply { scale(toDouble()) }
operator fun Double.times(A: Matrix): Matrix = A.apply { scale(this@times) }

operator fun Matrix.plus(B: Matrix) = when {
    m != B.m -> throw SizeMismatch("A+B", "A.m=$m, B.m=${B.m}", "A.m==B.m")
    n != B.n -> throw SizeMismatch("A+B", "A.n=$n, B.n=${B.n}", "A.n==B.n")
    else -> Matrix(m, n) { A[it] + B[it] }
}

operator fun Matrix.minus(B: Matrix) = when {
    m != B.m -> throw SizeMismatch("A-B", "A.m=$m, B.m=${B.m}", "A.m==B.m")
    n != B.n -> throw SizeMismatch("A-B", "A.n=$n, B.n=${B.n}", "A.n==B.n")
    else -> Matrix(m, n) { A[it] - B[it] }
}

operator fun Matrix.div(B: Matrix) = times(B.inverse())

operator fun Matrix.div(s: Number) = times(1 / s.toDouble())

operator fun List<Vector>.div(B: List<Vector>) = toMatrix() / B.toMatrix()
fun Matrix.inverse(): Matrix = pseudoInverse()

@JvmName("vectorListListSum")
fun List<List<Vector>>.sum(): Matrix = map { it.toMatrix() }.sum()

fun List<Matrix>.sum(): Matrix {
    val res = first().copyOf()
    for (i in indices.drop(1)) {
        val it = get(i)
        if (res.dims != it.dims)
            throw SizeMismatch(
                "matrix.Matrix list sum",
                "[0]=${res.dimsStr}, [$i]=${it.dimsStr}",
                "all matrices in the list should have the same dimensions."
            )
        res += it
    }
    return res
}

operator fun Matrix.unaryMinus() = Matrix(m, n) { -get(it) }
