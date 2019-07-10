package helpers.extensions.collections

val Collection<Boolean>.all get() = all { true }
val Collection<Boolean>.none get() = none { true }
val Collection<Boolean>.any get() = any { true }
val Collection<Boolean>.count get() = count { true }
