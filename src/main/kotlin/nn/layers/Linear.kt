@file:Suppress("PrivatePropertyName", "LocalVariableName")

package nn.layers

import nn.Layer
import nn.Weights
import nn.activations.Linear
import nn.trainers.ordinaryLeastSquares
import vector.toMatrixByRows
import vector.toVector

object Linear {
    operator fun invoke(numInputs: Int, numOutputs: Int): Layer {
        val weights = Weights(numOutputs * numInputs + numOutputs, ordinaryLeastSquares)
        val activation = Linear(numOutputs, { it.take(numOutputs * numInputs).toMatrixByRows(numOutputs) }, { it.takeLast(numOutputs).toVector() })

        return Layer(numInputs, numOutputs, activation, weights)
    }
}

