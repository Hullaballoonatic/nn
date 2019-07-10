package nn

import helpers.rand
import matrix.Matrix
import matrix.get
import matrix.joinToVector
import matrix.m
import vector.Vector
import vector.fillBy
import vector.fillByIndex
import vector.plusAssign
import kotlin.math.max

interface NeuralNetwork : List<Layer>, SupervisedLearner {
    val gradient get() = map { it.gradient }.joinToVector()
    val weights get() = map { it.weights }.joinToVector()

    fun initWeights(valueBy: (Layer) -> Double = { max(0.03, 1.0 / it.numInputs) * rand.nextGaussian() }) {
        forEach { it.weights.fillBy { valueBy(it) } }
    }

    fun refineWeights(x: Vector, y: Vector, lr: Double = 1e-3) {
        backProp(x, y)
        updateGradient(x)
        forEach {
            it.weights += it.gradient * lr
        }
    }

    override fun train(X: Matrix, Y: Matrix) {
        when (size) {
            0 -> error("No layers to train.")
            else -> {
                val numEntries = X.m
                val indices = 0 until numEntries
                repeat(10 * numEntries) {
                    val i = indices.random()
                    refineWeights(X[i], Y[i])
                }
            }
        }
    }

    override fun predict(x: Vector) = fold(x) { previousActivation, layer -> layer.activate(previousActivation) }

    fun backProp(x: Vector, y: Vector) {
        asReversed().fold(y - predict(x)) { blame, layer -> layer.backProp(blame) }
    }

    fun updateGradient(x: Vector) {
        fold(x) { previousActivation, layer -> layer.updateGradient(previousActivation) }
    }

    fun generateRandomWeights() {
        forEach {
            it.weights.fillByIndex { rand.nextGaussian() }
        }
    }

    companion object { // Basic Implementation
        private fun List<Layer>.verifyInputsOutputsMatchUp(): Int {
            var numOutputs = first().numInputs
            forEach {
                if (it.numInputs != numOutputs) error("Each layer must have a number of inputs equal to the previous layer's number of outputs.")
                numOutputs = it.numOutputs
            }
            return numOutputs
        }

        operator fun invoke(layers: List<Layer>, initWeights: Boolean = true): NeuralNetwork {
            layers.verifyInputsOutputsMatchUp()
            return object : NeuralNetwork, List<Layer> by layers {
                override val name = "Neural Network"
                override fun toString() = indices.joinToString("\n") { "$it) [${get(it)}]" }
            }.apply { if (initWeights) initWeights() }
        }

        fun nnOf(vararg layers: Layer) = invoke(layers.toList())
    }
}
