package vector

import helpers.errors.SizeMismatch
import matrix.M
import vec.Vec

fun List<Double>.toVector() = Vector(this)
fun DoubleArray.toVector() = Vector(this)
fun List<Double>.toMatrixByRows(m: Int) = (size / m).let { n -> M(m, n) { (i, j) -> this[i * n + j] } }

fun List<Vector>.sumByVector(op: (v: Vector) -> Vector) = drop(1).fold(op(first())) { acc, it -> acc + op(it) }
fun List<Vector>.sum() = sumByVector { it }

infix fun DoubleArray.o(that: DoubleArray) =
    M(this.size, that.size) { (i, j) -> this[i] * that[j] }

val Vec.sqMagnitude get() = squaredMagnitude()

operator fun Vec.plus(w: Vec) = data.zip(w.data) { a, b -> a + b }.toDoubleArray()

operator fun Vector.plusAssign(w: Vector) {
    when {
        size != w.size() -> throw SizeMismatch("v+w", "v=$size, w=${w.size()}", "v==w")
        else -> indices.forEach {
            this[it] += w[it]
        }
    }
}

fun Vector.fillByIndex(op: (Int) -> Double) {
    for (i in indices)
        this[i] = op(i)
}

fun Vector.fillByValue(op: (Double) -> Double) {
    forEachIndexed { i, v ->
        this[i] = op(v)
    }
}

fun Vector.fillBy(op: () -> Double) {
    for (i in indices) this[i] = op()
}

fun Vector.fill(v: Number) = fill(v.toDouble())

fun Vector.fill(v: Double) {
    for (i in indices)
        this[i] = v
}

operator fun Vector.plus(w: Vec) = when {
    size != w.size() -> throw SizeMismatch("v+w", "v=$size, w=${w.size()}", "v==w")
    else -> Vector(size()) { this[it] + w[it] }
}

operator fun Vec.plus(w: DoubleArray) = when {
    size() != w.size -> throw SizeMismatch("v+w", "v=${size()}, w=${w.size}", "v==w")
    else -> Vector(size()) { this[it] + w[it] }
}

operator fun Vec.plus(w: List<Double>) = when {
    size() != w.size -> throw SizeMismatch("v+w", "v=${size()}, w=${w.size}", "v==w")
    else -> Vector(size()) { this[it] + w[it] }
}

operator fun Vec.minus(w: Vec) = data.zip(w.data) { a, b -> a - b }.toVector()

operator fun Vector.minus(w: Vec) = when {
    size != w.size() -> throw SizeMismatch("v-w", "v=$size, w=${w.size()}", "v==w")
    else -> Vector(size()) { this[it] - w[it] }
}

operator fun Vec.minus(w: DoubleArray) = when {
    size() != w.size -> throw SizeMismatch("v-w", "v=${size()}, w=${w.size}", "v==w")
    else -> Vector(size()) { this[it] - w[it] }
}

operator fun Vec.minus(w: List<Double>) = when {
    size() != w.size -> throw SizeMismatch("v-w", "v=${size()}, w=${w.size}", "v==w")
    else -> Vector(size()) { this[it] - w[it] }
}

operator fun Vec.times(w: DoubleArray): Double = data.zip(w) { a, b -> a * b }.sum()

operator fun Vec.times(w: Vec): Double = data.zip(w.data) { a, b -> a * b }.sum()

operator fun Vec.times(w: List<Double>): Double = data.zip(w) { a, b -> a * b }.sum()

operator fun Vec.times(s: Int) = Vector(size()) { s * this[it] }

fun vectorOf(vararg values: Double) = Vector(values)
fun vectorOf(vararg values: Number) = Vector(DoubleArray(values.size) { values[it].toDouble() })

operator fun Int.times(v: Vector) = v.copyOf().also { it.scale(toDouble()) }
operator fun Double.times(v: Vector) = v.copyOf().also { it.scale(this) }
operator fun Float.times(v: Vector) = v.copyOf().also { it.scale(toDouble()) }
operator fun Byte.times(v: Vector) = v.copyOf().also { it.scale(toDouble()) }
