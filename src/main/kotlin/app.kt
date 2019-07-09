@file:Suppress("LocalVariableName")

import helpers.extensions.matrix.Matrix
import nn.SupervisedLearner

fun main() {
    // testLearner(SupervisedLearner())
}

fun unaryDataTest(learner: SupervisedLearner, challenge: String) {
    val fn = "data/$challenge"
    val X = Matrix("${fn}_feat.arff")
    val Y = Matrix("${fn}_lab.arff")

    val error = learner.crossValidate(X, Y, 5, 10)

    println("sse = $error")
}

fun test(learner: SupervisedLearner, challenge: String) {
    // Load the training data
    val fn = "data/$challenge"
    val trainFeatures = Matrix("${fn}_train_feat.arff")
    val trainLabels = Matrix("${fn}_train_lab.arff")

    // Train the model
    learner.train(trainFeatures, trainLabels)

    // Load the test data
    val testFeatures = Matrix("${fn}_test_feat.arff")
    val testLabels = Matrix("${fn}_test_lab.arff")

    // Measure and report accuracy
    val error = learner.countMisclassifications(testFeatures, testLabels)
    println("Misclassifications by ${learner.name} at $challenge = $error/${testFeatures.rows()}")
}

fun testLearner(learner: SupervisedLearner) {
    test(learner, "hep")
    test(learner, "vow")
    test(learner, "soy")
}
