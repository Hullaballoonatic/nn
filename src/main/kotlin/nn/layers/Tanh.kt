package nn.layers

import nn.Layer
import vector.Vector
import vector.toVector
import kotlin.math.pow
import kotlin.math.tanh

class Tanh(override val numInputs: Int) : Layer() {
    override val numOutputs = numInputs
    override val numWeights = 0

    override val onUpdateGradient: (x: Vector, blame: Vector) -> Vector = { _, _ -> weights } // no weights so does nothing

    override val onActivate: (weights: Vector, x: Vector) -> Vector = { _, x -> x.map { tanh(it) }.toVector() }

    override val onBackProp: (weights: Vector, blame: Vector) -> Vector = { _, blame -> blame.mapIndexed { i, v -> v * (1 - (activation[i].pow(2))) }.toVector() }
}
