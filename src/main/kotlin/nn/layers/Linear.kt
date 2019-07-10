@file:Suppress("PrivatePropertyName", "LocalVariableName")

package nn.layers

import matrix.Matrix
import matrix.flattenToVector
import matrix.t
import matrix.times
import nn.Layer
import vector.Vector
import vector.toMatrixByRows
import vector.toVector

class Linear(override val numInputs: Int, override val numOutputs: Int) : Layer() {
    override val numWeights = numOutputs * numInputs + numOutputs

    private val M get() = weights.take(numOutputs * numInputs).toMatrixByRows(numOutputs)
    private val b get() = weights.takeLast(numOutputs).toVector()

    private val toWeights: (M: Matrix, b: Vector) -> Vector = { M, b -> M.flattenToVector().append(b) }

    override val onActivate: (Vector, Vector) -> Vector = { _, x -> M * x + b }
    override val onBackProp: (Vector, Vector) -> Vector = { _, _ -> M.t * blame }
    override val onUpdateGradient: (Vector, Vector) -> Vector = { x, _ -> toWeights(blame o x, blame) }
}

