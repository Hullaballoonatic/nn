package vector

import helpers.errors.SizeMismatch
import helpers.extensions.matrix.n
import matrix.Matrix
import vec.Vec

open class Vector(data: DoubleArray) : Iterable<Double>, Vec(data) {
    constructor(data: List<Double>) : this(data.toDoubleArray())
    constructor(size: Int, op: (Int) -> Number = { 0.0 }) : this(DoubleArray(size) { op(it).toDouble() })

    override fun iterator() = data.iterator()

    val indices get() = 0 until size

    fun copyOf() = Vector(data.copyOf())

    val size = size()

    operator fun times(w: Vector): Double = zip(w) { a, b -> a * b }.sum()

    operator fun times(s: Double) = Vector(size) { s * this[it] }

    infix fun o(w: Vector): Matrix = outer(w)

    fun inner(w: Vector): Double = zip(w) { a, b -> a * b }.sum()

    fun outer(w: Vector): Matrix = Matrix(0, w.size).apply {
        forEach { u ->
            takeRow(DoubleArray(n) { w[it] * u })
        }
    }

    fun squared() = this o this

    operator fun plus(w: Vector) = when {
        size != w.size -> throw SizeMismatch("v+w", "v=$size, w=${w.size}", "v==w")
        else -> Vector(size) { this[it] + w[it] }
    }

    operator fun minus(w: Vector) = when {
        size != w.size -> throw SizeMismatch("v-w", "v=$size, w=${w.size}", "v==w")
        else -> Vector(size) { this[it] - w[it] }
    }

    fun append(w: Vector) = Vector(data + w.data)

    override fun equals(other: Any?): Boolean = when { // TODO: expand to add equality with IntArray?
        this === other -> true
        other == null -> false
        other is Vec -> when {
            size != other.size() -> false
            else -> data!!.contentEquals(other.data)
        }
        other is Collection<*> -> when (val it = other.first()) { // TODO: handle bad number conversions...
            null -> false
            it !is Number -> false
            size != other.size -> false
            else -> data!!.contentEquals(other.map { (it as Number).toDouble() }.toDoubleArray())
        }
        other is DoubleArray -> when {
            size != other.size -> false
            else -> data!!.contentEquals(other)
        }
        else -> false
    }

    override fun toString() = joinToString(
        prefix = "< ",
        postfix = " >"
    ) { if (it.toInt().toDouble() == it) it.toInt().toString() else it.toString().take(5) }

    override fun hashCode() = 71 * data.hashCode()

    fun takeLast(n: Int) = drop(size - n)
    fun dropLast(n: Int) = take(size - n)

    val sqMagnitude get() = squaredMagnitude()
}
