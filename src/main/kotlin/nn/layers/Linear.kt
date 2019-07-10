@file:Suppress("PrivatePropertyName", "LocalVariableName")

package nn.layers

import helpers.extensions.matrix.flattenToVector
import helpers.extensions.matrix.t
import helpers.extensions.matrix.times
import matrix.Matrix
import nn.Layer
import nn.trainers.OrdinaryLeastSquares
import vector.Vector
import vector.toMatrixByRows
import vector.toVector

class Linear(override val numInputs: Int, override val numOutputs: Int) : Layer {
    private val M get() = weights.take(numOutputs * numInputs).toMatrixByRows(numOutputs)
    private val b get() = weights.takeLast(numOutputs).toVector()
    private val toWeights: (M: Matrix, b: Vector) -> Vector = { M: Matrix, b: Vector -> M.flattenToVector().append(b) }

    override val activation = Vector(numOutputs)
    override val blame = Vector(numOutputs)
    override val weights = Vector(numOutputs * numInputs + numOutputs)
    override val gradient = Vector(weights.size)

    override val onTrain = OrdinaryLeastSquares(toWeights)
    override val onActivate = { _: Vector, x: Vector -> M * x + b }
    override val onBackProp = { _: Vector, blame: Vector -> M.t * blame }
    override val onUpdateGradient = { x: Vector, blame: Vector -> toWeights(blame o x, blame) }
}

