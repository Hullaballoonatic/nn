package helpers.extensions.collections.list

fun <T> List<T>.without(index: Int): List<T> = when (index) {
    lastIndex -> dropLast(1)
    0 -> drop(1)
    else -> take(index) + drop(index + 1)
}
