@file:Suppress("LocalVariableName")

import matrix.M
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

    println(learner)

    test(learner, "mnist")
}

fun test(learner: SupervisedLearner, challenge: String) {
    // Load the training data
    val fn = "D:\\Git\\nn\\src\\main\\resources\\data\\$challenge"
    val trainFeatures = M("${fn}_train_feat.arff")
    val trainLabels = M("${fn}_train_lab.arff")

    // Train the model
    learner.train(trainFeatures, trainLabels)

    // Load the test data
    val testFeatures = M("${fn}_test_feat.arff")
    val testLabels = M("${fn}_test_lab.arff")

    // Measure and report accuracy
    val error = learner.countMisclassifications(testFeatures, testLabels)
    println("Misclassifications by ${learner.name} at $challenge = $error/${testFeatures.rows()}")
}

fun testLearner(learner: SupervisedLearner) {
    test(learner, "hep")
    test(learner, "vow")
    test(learner, "soy")
}
