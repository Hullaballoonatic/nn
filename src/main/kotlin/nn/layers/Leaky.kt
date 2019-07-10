package nn.layers

import nn.Layer
import vector.Vector
import vector.toVector

class Leaky(override val numInputs: Int) : Layer() {
    override val name = "Leaky ReLU"

    override val numOutputs = numInputs
    override val numWeights = 0

    override val onUpdateGradient: (x: Vector, blame: Vector) -> Vector = { _, _ -> weights } // no weights so does nothing

    override val onActivate: (weights: Vector, x: Vector) -> Vector = { _, x ->
        x.map {
            when {
                it < 0 -> 0.01 * it
                else -> it
            }
        }.toVector()
    }

    override val onBackProp: (weights: Vector, blame: Vector) -> Vector = { _, blame ->
        blame.map {
            when {
                it < 0 -> 0.01
                else -> 1.0
            }
        }.toVector()
    }
}
