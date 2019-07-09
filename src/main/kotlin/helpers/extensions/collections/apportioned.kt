package helpers.extensions.collections

fun <T> Collection<T>.apportion(numPortions: Int) = when {
    numPortions <= 0 -> error("Cannot separate the collection into $numPortions portions.")
    numPortions == 1 -> listOf(this)
    numPortions == size -> map { listOf(it) }
    numPortions > size -> error("Cannot separate the collection into a number of portions greater than the number of elements in the collection.")
    else -> chunked(size / numPortions).run { if (size % numPortions > 0) dropLast(1) else this }
}

fun <T> List<T>.apportion(numPortions: Int) = when {
    numPortions <= 0 -> error("Cannot separate the collection into $numPortions portions.")
    numPortions == 1 -> listOf(this)
    numPortions == size -> map { listOf(it) }
    numPortions > size -> error("Cannot separate the collection into a number of portions greater than the number of elements in the collection.")
    else -> chunked(size / numPortions).run { if (size % numPortions > 0) dropLast(1) else this }
}
