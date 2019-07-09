package nn.activations

import helpers.extensions.matrix.times
import matrix.Matrix
import nn.Activation
import vector.Vector

class Linear(numOutputs: Int, val getM: (weights: Vector) -> Matrix, val getB: (weights: Vector) -> Vector) : Activation(numOutputs) {
    override val onActivate = { weights: Vector, x: Vector ->
        val M = getM(weights)
        val b = getB(weights)
        M * x + b
    }
}
