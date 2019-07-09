package helpers.extensions.matrix

import helpers.Json
import matrix.Matrix
import vector.Vector

fun Matrix.copyOf() = Matrix(0, n).also { res ->
    repeat(m) { i ->
        res.takeRow(row(i).data.copyOf())
    }
}

object Matrix {
    operator fun invoke(m: Int, n: Int, op: (p: Pair<Int, Int>) -> Number = { 0.0 }): Matrix = Matrix(0, n).apply {
        repeat(m) { i ->
            takeRow(DoubleArray(n) { j -> op(i to j).toDouble() })
        }
    }

    operator fun invoke(m: Int, op: (i: Int) -> Vector) = List(m) { op(it) }.toMatrix()

    operator fun invoke(fp: String): Matrix = when (fp.split(".").last()) {
        "arff" -> Matrix().apply { loadARFF(fp) }
        "json" -> Matrix(Json.load(fp))
        else -> error("unrecognized file extension.")
    }
}

fun matrixOfRows(vararg rows: DoubleArray): Matrix = Matrix(0, rows[0].size).apply {
    rows.forEach {
        takeRow(it)
    }
}

fun matrixOfRows(m: Int, vararg values: Number): Matrix {
    val n = values.size / m
    return List(m) { i -> DoubleArray(n) { j -> values[i * n + j].toDouble() } }.toMatrix()
}

fun List<DoubleArray>.toMatrix() = Matrix(0, first().size).apply {
    forEach {
        takeRow(it)
    }
}

@JvmName("VectorListToMatrix")
fun List<Vector>.toMatrix() = Matrix(size, first().size) { (i, j) -> this[i][j] }

@JvmName("DoubleListListToMatrix")
fun List<List<Double>>.toMatrix() = Matrix(0, first().size).apply {
    forEach {
        takeRow(it.toDoubleArray())
    }
}
