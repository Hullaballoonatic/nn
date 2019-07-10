package matrix

import helpers.extensions.pair.until
import vector.Vector

val Matrix.dims get() = m to n
val Matrix.dimsStr get() = "${m}x$n"

val Matrix.indices get() = (0 to 0) until (m to n)

val Matrix.m get() = rows()
val Matrix.n get() = cols()

val Matrix.centroid get() = Vector(n) { columnMean(it) }

val Matrix.cols get() = List(n) { j -> Vector(m) { i -> m_data[i][j] } }
val Matrix.rows get() = List(m) { Vector(m_data[it].copyOf()) }
