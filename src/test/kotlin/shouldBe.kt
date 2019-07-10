@file:Suppress("UNCHECKED_CAST")

import helpers.errors.SizeMismatch
import helpers.rand
import matrix.*
import vector.Vector
import kotlin.math.abs

fun Vector.addNoise() {
    for (i in indices) {
        this[i] += rand.nextGaussian()
    }
}

fun Matrix.addNoise() {
    m_data.forEach {
        for (i in it.indices)
            it[i] += rand.nextGaussian()
    }
}

infix fun Any?.shouldBe(that: Any?) {
    if (this != that) error("$this\n ↑ SHOULD BE ↓\n$that")
}

infix fun Any?.shouldNotBe(that: Any?) {
    if (this == that) error("$this\n ↑ SHOULD NOT BE ↓\n$that")
}

infix fun Number.shouldBeAbout(that: Number) {
    if (!(toDouble() near that.toDouble())) error("$this\n ↑ SHOULD BE ABOUT ↓\n$that")
}

infix fun Iterable<Number>.shouldBeAbout(that: Iterable<Number>) {
    if (zip(that) { a, b -> a near b }.any { false }) error("$this\n ↑ SHOULD BE ABOUT ↓\n$that")
}

infix fun Matrix.shouldBeAbout(that: Matrix) {
    if (dims != that.dims)
        throw SizeMismatch("A should be about B", "A=$dimsStr, B=${that.dimsStr}", "Equal sizes of both matrices.")
    for (i in indices)
        if (this[i] notNear that[i])
            error("A$i=${this[i]} SHOULD BE ABOUT B$i=${that[i]}")
}

infix fun DoubleArray.shouldBeAbout(that: DoubleArray) {
    if (zip(that) { a, b -> a near b }.any { false }) error("$this\n ↑ SHOULD BE ABOUT ↓\n$that")
}

infix fun Number.shouldNotBeAbout(that: Number) {
    if (!(toDouble() near that.toDouble())) error("$this\n ↑ SHOULD NOT BE ABOUT ↓\n$that")
}

infix fun Iterable<Number>.shouldNotBeAbout(that: Iterable<Number>) {
    if (zip(that) { a, b -> a near b }.any { true }) error("$this\n ↑ SHOULD NOT BE ABOUT ↓\n$that")
}

infix fun DoubleArray.shouldNotBeAbout(that: DoubleArray) {
    if (zip(that) { a, b -> a near b }.any { true }) error("$this\n ↑ SHOULD NOT BE ABOUT ↓\n$that")
}

infix fun Number.notNear(other: Number) = !abs(toDouble() - other.toDouble()).isSmall
infix fun Number.near(other: Number) = abs(toDouble() - other.toDouble()).isSmall

val Number.isSmall get() = abs(toDouble()) <= 1e-16
