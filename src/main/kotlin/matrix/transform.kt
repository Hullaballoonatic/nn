package matrix

import vector.Vector

fun <T> Matrix.mapRows(op: (Vector) -> T): List<T> = rows.map(op)
fun <T> Matrix.mapCols(op: (Vector) -> T): List<T> = cols.map(op)

val Matrix.transposed: Matrix get() = transpose()
val Matrix.t get() = transpose()

fun Matrix.flattenToVector() = Vector(m * n) { get(it / n, it % n) }
