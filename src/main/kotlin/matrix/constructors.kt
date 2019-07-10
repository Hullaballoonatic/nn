package matrix

import helpers.Json
import helpers.errors.SizeMismatch
import vector.Vector

fun Matrix.copyOf() = Matrix(0, n).also { res ->
    repeat(m) { i ->
        res.takeRow(row(i).data.copyOf())
    }
}

object M {
    operator fun invoke(numRows: Int, numCols: Int, valueByIndex: (p: Pair<Int, Int>) -> Number = { 0.0 }) = Matrix(0, numCols).apply {
        repeat(numRows) { i ->
            takeRow(DoubleArray(numCols) { j -> valueByIndex(i to j).toDouble() })
        }
    }

    operator fun invoke(numRows: Int, rowByRowIndex: (i: Int) -> Vector) = List(numRows) { rowByRowIndex(it) }.toMatrix()

    operator fun invoke(fp: String) = when (fp.split(".").last()) {
        "arff" -> Matrix().apply { loadARFF(fp) }
        "json" -> Matrix(Json.load(fp))
        else -> error("unrecognized file extension.")
    }
}

fun matrixOfRows(vararg rows: Vector): Matrix = Matrix(0, rows[0].size).apply {
    rows.forEach {
        takeRow(it.data)
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
fun List<Vector>.toMatrix() = M(size, first().size) { (i, j) -> this[i][j] }

@JvmName("DoubleListListToMatrix")
fun List<List<Double>>.toMatrix() = Matrix(0, first().size).apply {
    forEach {
        takeRow(it.toDoubleArray())
    }
}

private fun List<Matrix>.collectMismatchedHeights(height: Int = first().m): Map<Int, Int> {
    val indexToHeight = mutableMapOf<Int, Int>()
    for (i in indices) {
        val m = get(i).m
        if (m != height)
            indexToHeight[i] = m
    }
    return indexToHeight
}

private fun List<Matrix>.verifyAllHeights(): Int {
    val res = first().m
    collectMismatchedHeights().let {
        if (it.isNotEmpty())
            throw SizeMismatch("[Matrix].joinToMatrix", it.entries.joinToString { (i, v) -> "M[$i]=$v" }, "All have height of $res")
    }
    return res
}

fun Matrix.fillByIndex(op: (Pair<Int, Int>) -> Number) {
    for (i in indices)
        this[i] = op(i).toDouble()
}

fun Matrix.fillByValue(op: (Double) -> Number) {
    for (i in indices)
        this[i] = op(this[i]).toDouble()
}

fun Matrix.transformedByValue(op: (Double) -> Number) = M(m, n) { op(this[it]).toDouble() }
fun Matrix.transformedByIndex(op: (Pair<Int, Int>) -> Number) = M(m, n) { op(it).toDouble() }

fun List<Vector>.joinToVector() = drop(1).fold(first().copyOf()) { acc, v -> acc.append(v) }

fun List<Matrix>.joinToMatrix(): Matrix = when (size) {
    0 -> Matrix(0, 0)
    1 -> first()
    else -> M(verifyAllHeights()) { i -> this@joinToMatrix.map { it[i] }.joinToVector() }
}
