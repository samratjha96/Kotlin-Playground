package dev.learning.kotlin

import kotlin.random.Random

sealed class Response
data class Success(val content: String) : Response()
data class Error(val code: Int, val message: String) : Response()

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
}

