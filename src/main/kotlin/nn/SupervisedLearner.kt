@file:Suppress("LocalVariableName")

package nn

import helpers.errors.SizeMismatch
import helpers.extensions.collections.apportion
import helpers.extensions.collections.list.without
import helpers.extensions.matrix.*
import matrix.Matrix
import vector.Vector
import kotlin.math.sqrt

interface SupervisedLearner {
    val name: String

    fun train(X: Matrix, Y: Matrix)

    fun predict(X: Matrix): Matrix

    fun checkEntries(X: Matrix, Y: Matrix): Int {
        if (X.m != Y.m) throw SizeMismatch("learner", "X.m=${X.m}, Y.m=${Y.m}", "X and Y have same number of entries.")
        return X.m
    }

    fun countMisclassifications(X: Matrix, Y: Matrix): Int {
        checkEntries(X, Y)
        return predict(X).run {
            indices.count { A[it] != Y[it] }
        }
    }

    fun sumSquaredError(X: Matrix, Y: Matrix): Double {
        checkEntries(X, Y)
        return (predict(X) - Y).rows.sumByDouble { it.sqMagnitude }
    }

    fun crossValidate(X: Matrix, Y: Matrix, numFolds: Int, repetitions: Int = 1, computeErr: (X: Matrix, Y: Matrix) -> Double = { x, y -> sumSquaredError(x, y) }): Double {
        val numEntries = checkEntries(X, Y)

        val sse = List(repetitions) {
            val xFolds = X.rows.shuffled().apportion(numFolds)
            val yFolds = Y.rows.shuffled().apportion(numFolds)

            List(numFolds) { h ->
                val x = xFolds.without(h).flatten().toMatrix()
                val y = yFolds.without(h).flatten().toMatrix()
                train(x, y)
                computeErr(xFolds[h].toMatrix(), yFolds[h].toMatrix())
            }.sum()
        }.sum()

        val mse = sse / numEntries
        val rmse = sqrt(mse)

        println("Sum-squared error = $sse")
        println("Mean-squared error = $mse")
        println("Root-mean-squared error = $rmse")

        return rmse
    }

    companion object {
        operator fun invoke() = object : SupervisedLearner {
            override val name = "Baseline"

            lateinit var mode: Vector

            override fun train(X: Matrix, Y: Matrix) {
                mode = Vector(Y.n) {
                    when (Y.valueCount(it)) {
                        0 -> Y.columnMean(it)
                        else -> Y.mostCommonValue(it)
                    }
                }
            }

            override fun predict(X: Matrix) = Matrix(X.m) { mode }
        }
    }
}
