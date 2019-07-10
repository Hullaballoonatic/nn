@file:Suppress("NAME_SHADOWING", "LocalVariableName")

package nn

import helpers.rand
import matrix.*
import nn.layers.Linear
import nn.trainers.OrdinaryLeastSquares
import org.junit.jupiter.api.Test
import shouldBeAbout
import vector.Vector
import vector.fill
import kotlin.math.sqrt

internal class NeuralNetworkTest {
    @Test
    fun linear() {
        val X = M("D:\\Git\\nn\\src\\main\\resources\\data\\housing_feat.arff")
        val Y = M("D:\\Git\\nn\\src\\main\\resources\\data\\housing_lab.arff")
        val nn = NeuralNetwork(Linear(X.n, Y.n))
        val error = nn.crossValidate(X, Y, 10, 5)
        println("sse = $error")
    }

    @Test
    fun crossValidation() {
        val X = matrixOfRows(3, 0, 0, 0)
        val Y = matrixOfRows(3, 2, 4, 6)
        val learner = SupervisedLearner()
        learner.crossValidate(X, Y, 3, 1) shouldBeAbout sqrt(6.0)
    }

    @Test
    fun refineWeights() {
        val X = M("D:\\Git\\nn\\src\\main\\resources\\data\\housing_feat.arff")
        val Y = M("D:\\Git\\nn\\src\\main\\resources\\data\\housing_lab.arff")

        val nn = NeuralNetwork(Linear(X.n, Y.n))

        val numEntries = X.m

        val indices = 0 until numEntries

        repeat(10 * numEntries) {
            val i = indices.random()
            nn.refineWeights(X[i], Y[i])
        }

        nn.weights shouldBeAbout OrdinaryLeastSquares(X, Y)
    }

    @Test
    fun updateGradient() {
        val nn = NeuralNetwork(Linear(5, 1))

        val x = Vector(5) { rand.nextGaussian() }
        val y = Vector(1) { rand.nextGaussian() }

        val h = Vector(5) { 1e-6 } // technically h/2

        val f: (Vector) -> Vector = { x ->
            nn.run {
                backProp(x, y)
                updateGradient(x)

                val res = gradient

                // reset
                forEach { layer ->
                    layer.gradient.fill(0) // no need to clear activation, because it is completely overwritten by each f
                    layer.blame.fill(0) // no need to clear weights, because they are not altered by f
                }

                res
            }
        }

        //  Central Diff
        f(x + h) - f(x - h) shouldBeAbout f(x)
    }
}
