package nn

import helpers.extensions.matrix.clear
import helpers.extensions.matrix.get
import helpers.extensions.matrix.m
import matrix.Matrix
import vector.Vector

abstract class Activation(activation: Matrix) : Matrix(activation) {
    constructor(numOutputs: Int) : this(Matrix(0, numOutputs))

    abstract val onActivate: (weights: Vector, x: Vector) -> Vector

    fun activate(weights: Vector, X: Matrix) {
        clear()
        repeat(X.m) {
            takeRow(onActivate(weights, X[it]).data)
        }
    }

    companion object {
        operator fun invoke(activation: Matrix, onActivate: (weights: Vector, x: Vector) -> Vector) = object : Activation(activation) {
            override val onActivate = onActivate
        }

        operator fun invoke(numOutputs: Int, onActivate: (weights: Vector, x: Vector) -> Vector) = invoke(Matrix(0, numOutputs), onActivate)
    }
}
