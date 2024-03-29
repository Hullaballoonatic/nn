@file:Suppress("LocalVariableName", "PrivatePropertyName")

package nn.layers

import helpers.addNoise
import helpers.rand
import helpers.shouldBe
import helpers.shouldBeAbout
import matrix.matrixOfRows
import matrix.times
import nn.trainers.OrdinaryLeastSquares
import org.junit.jupiter.api.Test
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

        layer.weights shouldBeAbout OrdinaryLeastSquares(matrixOfRows(x), matrixOfRows(y))
    }
}
