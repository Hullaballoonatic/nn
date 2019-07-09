@file:Suppress("LocalVariableName")

package nn.trainers

import helpers.errors.SizeMismatch
import helpers.extensions.matrix.*
import matrix.Matrix

val ordinaryLeastSquares = { X: Matrix, Y: Matrix ->
    val m = when {
        X.m == Y.m -> X.m
        else -> throw SizeMismatch("ols", "X.m=${X.m}, Y.m=${Y.m}", "X.m == Y.m")
    }

    val yc = Y.centroid
    val xc = X.centroid

    val num = List(m) { i -> (Y[i] - yc) o (X[i] - xc) }.sum()
    val den = List(m) { i -> (X[i] - xc).squared() }.sum()

    val M = num / den

    val b = yc - M * xc

    M.flattenToVector().append(b)
}
