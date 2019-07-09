package nn

import helpers.extensions.matrix.*
import nn.layers.Linear
import org.junit.jupiter.api.Test
import shouldBe
import shouldBeAbout
import kotlin.math.sqrt

internal class NeuralNetworkTest {
    @Test
    fun linear() {
        val X = Matrix("D:\\Git\\nn\\src\\main\\resources\\data\\housing_feat.arff")
        val Y = Matrix("D:\\Git\\nn\\src\\main\\resources\\data\\housing_lab.arff")
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

    val A = Matrix(3, 3) { 1 }
    val B = Matrix(3, 3) { 2 }
    val C = Matrix(3, 3) { 3 }

    @Test
    fun plus() {
        (A + B) shouldBe C
    }

    @Test
    fun minus() {
        (C - B) shouldBe A
    }
}
