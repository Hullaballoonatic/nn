package helpers.errors

class SizeMismatch(on: String, provided: String, expected: String) : IllegalArgumentException("Size mismatch on $on\n\tprovided: $provided\n\texpected: $expected") {
    constructor(on: String, provided: Number, expected: Number) : this(on, provided.toString(), expected.toString())
}
