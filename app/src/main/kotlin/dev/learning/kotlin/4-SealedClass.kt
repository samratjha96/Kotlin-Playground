package dev.learning.kotlin

import kotlin.random.Random

sealed class Response
data class Success(val content: String) : Response()
data class Error(val code: Int, val message: String) : Response()



sealed class Either<out E, out V> {
    data class Left<out E>(val left: E) : Either<E, Nothing>()
    data class Right<out V>(val right: V) : Either<Nothing, V>()
}

fun <A, B> Either<A, B>.either(ifLeft: ((A) -> Unit)? = null, ifRight: ((B) -> Unit)? = null) {
    when(this) {
        is Either.Left -> ifLeft?.invoke(this.left)
        is Either.Right -> ifRight?.invoke(this.right)
    }
}

fun main() {
    fun getResponse(): Response {
        val valid = Random.nextBoolean()
        return if (valid) {
            Success("Content")
        } else {
            Error(404, "Not found")
        }
    }

    when (val response = getResponse()) {
        is Success -> println(response.content)
        is Error -> println(response.message)
    }

    // Union types

//    val intOrString: Either<Int, String> = if (Random.nextBoolean()) {
//        Either.Left(2)
//    } else Either.Right("Foo")
//
//    intOrString.either(
//        ifLeft = { print(it + 1) },
//        ifRight = { print(it + "Bar") }
//    )
}

