@file:Suppress("LocalVariableName")

import matrix.M
import matrix.Matrix
import matrix.divAssign
import matrix.m
import nn.NeuralNetwork.Companion.nnOf
import nn.SupervisedLearner
import nn.layers.Leaky
import nn.layers.Linear
import nn.layers.Tanh

fun main() {
    val learner = nnOf(
        Linear(784, 80),
        Tanh(80),
        Linear(80, 30),
        Tanh(30),
        Linear(30, 10),
        Tanh(10),
        Linear(10, 1),
        Leaky(1)
    )

    val data = loadData("mnist")

    data.trainX /= 256
    data.testX /= 256

    test(learner, data)
}

data class Data(val challenge: String, val trainX: Matrix, val trainY: Matrix, val testX: Matrix, val testY: Matrix) : List<Matrix> by listOf(trainX, trainY, testX, testY)

fun loadData(challenge: String): Data {
    val fn = "D:\\Git\\nn\\src\\main\\resources\\data\\$challenge"

    println("loading training data")
    val trainFeatures = M("${fn}_train_feat.arff")
    val trainLabels = M("${fn}_train_lab.arff")
    println("training data loaded. size=${trainFeatures.m}")

    println("loading testing data")
    val testFeatures = M("${fn}_test_feat.arff")
    val testLabels = M("${fn}_test_lab.arff")
    println("testing data loaded. size=${testFeatures.m}")

    return Data(challenge, trainFeatures, trainLabels, testFeatures, testLabels)
}

fun test(learner: SupervisedLearner, challenge: String) = test(learner, loadData(challenge))

fun test(learner: SupervisedLearner, data: Data) {
    println("learner:\n$learner\n")

    val (challenge, trainX, trainY, testX, testY) = data

    println("training")
    learner.train(trainX, trainY)

    // Measure and report accuracy
    println("testing")
    val error = learner.countMisclassifications(testX, testY)
    println("complete")
    println("Misclassifications by ${learner.name} at $challenge = $error/${testX.m}")
}

fun testLearner(learner: SupervisedLearner) {
    test(learner, "hep")
    test(learner, "vow")
    test(learner, "soy")
}
