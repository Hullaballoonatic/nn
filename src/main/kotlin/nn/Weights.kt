package nn

import helpers.rand
import matrix.Matrix
import vector.Vector

class Weights(weights: DoubleArray, val onTrain: (X: Matrix, Y: Matrix) -> Vector) : Vector(weights) {
    constructor(size: Int, onTrain: (X: Matrix, Y: Matrix) -> Vector) : this(DoubleArray(size) { rand.nextGaussian() }, onTrain)

    fun train(X: Matrix, Y: Matrix) {
        copy(onTrain(X, Y))
    }
}
