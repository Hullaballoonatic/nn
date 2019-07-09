@file:Suppress("LocalVariableName", "PrivatePropertyName")

package nn.layers

import addNoise
import helpers.extensions.matrix.Matrix
import helpers.extensions.matrix.matrixOfRows
import helpers.extensions.matrix.times
import helpers.rand
import nn.trainers.ordinaryLeastSquares
import org.junit.jupiter.api.Test
import shouldBe
import shouldBeAbout
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
        val numInputs = 10
        val numOutputs = 2
        val numEntries = 20

        val layer = Linear(numInputs, numOutputs)

        val X = Matrix(numEntries, numInputs) { rand.nextGaussian() }
        val Y = layer.activate(X)

        Y.addNoise()

        val res = ordinaryLeastSquares(X, Y)

        layer.weights shouldBeAbout res
    }
}
