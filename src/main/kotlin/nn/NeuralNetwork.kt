package nn

import matrix.Matrix

class NeuralNetwork(layers: List<Layer>) : List<Layer> by layers, SupervisedLearner {
    constructor(vararg layers: Layer) : this(layers.toList())

    override val name = "Neural Network"

    override fun train(X: Matrix, Y: Matrix) {
        when (size) {
            0 -> error("No layers to train.")
            1 -> first().train(X, Y)
            else -> TODO("fold throughout layers")
        }
    }

    override fun predict(X: Matrix) = when (size) {
        0 -> error("No layers to make predictions.")
        1 -> first().activate(X)
        else -> error("")
    }
}
