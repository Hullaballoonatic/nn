package nn

import helpers.errors.SizeMismatch
import vector.Vector
import vector.plusAssign

// TODO: Refactor out weighted layer from layer
abstract class Layer {
    open val name: String = javaClass.simpleName

    abstract val numInputs: Int
    abstract val numOutputs: Int
    abstract val numWeights: Int

    val activation by lazy { Vector(numOutputs) }
    val blame by lazy { Vector(numOutputs) }
    val weights by lazy { Vector(numWeights) }
    val gradient by lazy { Vector(numWeights) }

    abstract val onActivate: (weights: Vector, x: Vector) -> Vector
    abstract val onBackProp: (weights: Vector, blame: Vector) -> Vector
    abstract val onUpdateGradient: (x: Vector, blame: Vector) -> Vector

    fun activate(x: Vector): Vector {
        if (x.size != numInputs) throw SizeMismatch("num Inputs", x.size, numInputs)
        activation.copy(onActivate(weights, x))
        return activation
    }

    /**
     * this layer's Back Propagation method
     *
     * @param blame (required) this layer's new blame, typically computed via the subsequent layer.
     * @return the blame for the layer preceding this one, computed by {@link onBackProp}
     */
    fun backProp(blame: Vector): Vector {
        this.blame.copy(blame)
        return onBackProp(weights, blame)
    }

    fun updateGradient(x: Vector): Vector {
        gradient += onUpdateGradient(x, blame)
        return activation
    }

    override fun toString() = "$name: $numInputs->$numOutputs, Weights=$numWeights"
}
