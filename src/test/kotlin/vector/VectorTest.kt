package vector

import matrix.matrixOfRows
import org.junit.jupiter.api.Test

import helpers.shouldBe
import helpers.shouldNotBe

internal class VectorTest {
    val a = vectorOf(1, 2, 3)
    val b = vectorOf(4, 5, 6)

    @Test
    fun copyOf() {
        val tmp = a.copyOf()
        tmp[1] += 4.0
        tmp shouldNotBe a
    }

    @Test
    fun times() {
        4 * a shouldBe vectorOf(4, 8, 12)
    }

    @Test
    fun inner() {
        a * b shouldBe 32.0
    }

    @Test
    fun outer() {
        a o b shouldBe matrixOfRows(a.size, 4, 5, 6, 8, 10, 12, 12, 15, 18)
    }

    @Test
    fun plus() {
        a + b shouldBe vectorOf(5, 7, 9)
    }

    @Test
    fun equals1() {
        a shouldBe doubleArrayOf(1.0, 2.0, 3.0)
        a shouldBe listOf(1, 2, 3)
    }
}
