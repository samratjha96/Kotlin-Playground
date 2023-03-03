package dev.learning.kotlin

import java.util.Observable

fun main() {

    // Varargs
    fun asList(vararg strings: String): List<String> {
        val list = mutableListOf<String>()
        list.addAll(strings)
        return list
    }

    val otherList = arrayOf("a", "b", "c")
    println(asList("Test", "test2", *otherList))

    // Single expression functions
    fun double(x: Int): Int = x * 2
    println(double(2))

    // Infix functions
    /**
        Infix functions must meet the following requirements:

        They must be member functions or extension functions.

        They must have a single parameter.

        The parameter must not accept variable number of arguments and must have no default value.
     */
    infix fun <T> Boolean.then(param: T): T? = if (this) param else null

    println((2 + 2 == 4) then "yes" ?: "no")

    val car = Car()
    car turn TurnDirection.left then TurnDirection.right

    // Higher order functions
    fun rollDice(
        range: IntRange,
        time: Int,
        callback: (result: Int) -> Unit
    ) {
        for (i in 0 until time) {
            val result = range.random()
            //As the last parameter is a function
            //we can call it as a function
            callback(result)
        }
    }
    rollDice(0..6, 3) { result ->
        println(result)
    }

    // Operator Overloading
    println(Counter(2) + 5)
    println(Counter("2") + 5)
    println(Counter("2").not())


    // Local returns
    fun foo() {
        listOf(1,2)
            .map{ intValue ->
                return@map intValue.toString()
            }
    }
    foo()
}

enum class TurnDirection { left, right }

class TurnDirectionBuilder {
    infix fun then(turnDirection: TurnDirection) { println("Turning $turnDirection") }
}

class Car {
    infix fun turn(turnDirection: TurnDirection): TurnDirectionBuilder {
        println("Turning $turnDirection")
        return TurnDirectionBuilder()
    }
}

data class Counter(val initial: Int) {
    companion object {
        operator fun invoke(initialString: String): Counter {
            return Counter(initialString.toInt())
        }
    }
    operator fun plus(increment: Int): Counter {
        return Counter(initial + increment)
    }

    operator fun not(): Counter {
        return Counter(-initial )
    }
}