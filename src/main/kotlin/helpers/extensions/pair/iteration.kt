package helpers.extensions.pair

operator fun Pair<Int, Int>.rangeTo(that: Pair<Int, Int>) = object : Iterable<Pair<Int, Int>> {
    override fun iterator() = object : Iterator<Pair<Int, Int>> {
        var i = this@rangeTo.first
        var j = this@rangeTo.second
        val m = that.first - i
        val n = that.second - j

        override fun hasNext() = i <= m && j <= n

        override fun next(): Pair<Int, Int> {
            val res = i to j
            if (j == n) {
                j = 0
                i++
            } else j++
            return res
        }
    }
}

infix fun Pair<Int, Int>.until(that: Pair<Int, Int>) = rangeTo((that.first - 1) to (that.second - 1))
