package helpers.extensions.matrix

import helpers.errors.SizeMismatch
import matrix.Matrix
import vec.Vec
import vector.Vector

operator fun Matrix.get(i: Int): Vector = Vector(row(i).data)
operator fun Matrix.plusAssign(that: Matrix) {
    if (dims != that.dims) throw SizeMismatch("A+=B", "A=$dimsStr, B=${that.dimsStr}", "A.dims == B.dims")
    for ((i, j) in indices)
        this[i, j] += that[i, j]
}

operator fun Matrix.minusAssign(that: Matrix) {
    if (dims != that.dims) throw SizeMismatch("A-=B", "A=$dimsStr, B=${that.dimsStr}", "A.dims == B.dims")
    for ((i, j) in indices)
        this[i, j] -= that[i, j]
}

fun Matrix.takeRow(row: Vec) = takeRow(row.data)

operator fun Matrix.get(p: Pair<Int, Int>) = m_data[p.first][p.second]

operator fun Matrix.get(i: Int, j: Int) = m_data[i][j]

operator fun Matrix.set(i: Int, j: Int, v: Number) {
    m_data[i][j] = v.toDouble()
}

operator fun Matrix.set(p: Pair<Int, Int>, v: Number) {
    m_data[p.first][p.second] = v.toDouble()
}

fun Matrix.clear() {
    while (m > 0)
        removeRow(m - 1)
}
