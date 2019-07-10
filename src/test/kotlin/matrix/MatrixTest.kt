@file:Suppress("PrivatePropertyName")

package matrix

import org.junit.jupiter.api.Test
import shouldBe

internal class MatrixTest {
    private val A = M(3, 3) { 1 }
    private val B = M(3, 3) { 2 }
    private val C = M(3, 3) { 3 }

    @Test
    fun plus() {
        (A + B) shouldBe C
    }

    @Test
    fun minus() {
        (C - B) shouldBe A
    }
}
