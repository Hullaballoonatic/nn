package nn

import helpers.errors.SizeMismatch
import helpers.extensions.matrix.m
import matrix.Matrix
import vector.Vector
import vector.plusAssign

interface Layer {
    val numInputs: Int
    val numOutputs: Int

    val activation: Vector
    val weights: Vector
    val blame: Vector
    val gradient: Vector

    val onActivate: (weights: Vector, x: Vector) -> Vector
    val onTrain: (X: Matrix, Y: Matrix) -> Vector
    val onBackProp: (weights: Vector, blame: Vector) -> Vector
    val onUpdateGradient: (x: Vector, blame: Vector) -> Vector

    fun activate(x: Vector): Vector {
        if (x.size != numInputs) throw SizeMismatch("num Inputs", x.size, numInputs)
        activation.copy(onActivate(weights, x))
        return activation
    }

    fun train(X: Matrix, Y: Matrix): Vector {
        if (X.m != Y.m) throw SizeMismatch("train", "X.m=${X.m}, Y.m=${Y.m}", "#entries on features and labels equal.")
        weights.copy(onTrain(X, Y))
        return weights
    }

    /**
     * this layer's Back Propagation method
     *
     * @param blame (required) this layer's new blame, typically computed via the subsequent layer.
     * @return the blame for the layer preceding this one, computed by {@link onBackProp}
     */
    fun backProp(blame: Vector): Vector {
        this.blame.copy(blame)
        return onBackProp(weights, blame)
    }

    fun updateGradient(x: Vector): Vector {
        gradient += onUpdateGradient(x, blame)
        return activation
    }
}
