package nn

import helpers.errors.SizeMismatch
import helpers.extensions.matrix.n
import matrix.Matrix
import vector.Vector

interface Layer {
    val numInputs: Int
    val numOutputs: Int

    val activation: Activation
    val weights: Weights

    fun activate(X: Matrix): Matrix {
        if (X.n != numInputs) throw SizeMismatch("num Inputs", X.n, numInputs)
        activation.activate(weights, X)
        return activation
    }

    fun train(X: Matrix, Y: Matrix): Vector {
        weights.train(X, Y)
        return weights
    }

    companion object {
        operator fun invoke(numInputs: Int, numOutputs: Int, activation: Activation, weights: Weights) = object : Layer {
            override val numInputs = numInputs
            override val numOutputs = numOutputs
            override val weights = weights
            override val activation = activation
        }

        operator fun invoke(numInputs: Int, numOutputs: Int, numWeights: Int, onActivate: (weights: Vector, x: Vector) -> Vector, onTrain: (X: Matrix, Y: Matrix) -> Vector) =
            invoke(numInputs, numOutputs, Activation(numOutputs, onActivate), Weights(numWeights, onTrain))
    }
}
