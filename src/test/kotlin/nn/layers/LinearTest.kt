@file:Suppress("LocalVariableName", "PrivatePropertyName")

package nn.layers

import addNoise
import helpers.extensions.matrix.matrixOfRows
import helpers.extensions.matrix.times
import helpers.rand
import nn.trainers.ols
import org.junit.jupiter.api.Test
import shouldBe
import shouldBeAbout
import vector.Vector
import vector.fillByValue
import vector.vectorOf

internal class LinearTest {
    @Test
    fun linearOperation() {
        val x = vectorOf(0, 1, 2)
        val M = matrixOfRows(2, 1, 2, 3, 2, 1, 0)
        val b = vectorOf(1, 5)
        M * x + b shouldBe vectorOf(9, 6)
    }

    @Test
    fun ols() {
        val layer = Linear(10, 2)
        layer.weights.fillByValue { rand.nextGaussian() }

        val x = Vector(10) { rand.nextGaussian() }
        val y = layer.activate(x)

        y.addNoise()

        layer.weights shouldBeAbout ols(matrixOfRows(x), matrixOfRows(y))
    }
}
