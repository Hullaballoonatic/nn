@file:Suppress("UNCHECKED_CAST")

import helpers.rand
import matrix.Matrix
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

infix fun Number.near(other: Number) = abs(toDouble() - other.toDouble()) < 1e-16

val Number.isSmall get() = abs(toDouble()) < 1e-16
