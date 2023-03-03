package dev.learning.kotlin

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis


internal class SimpleAsync {
    fun main() = runBlocking {
        launch { doWorld() }
        println("Hello")
    }

    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }
}

internal class MultipleSuspendingFunctions {
    fun main() = runBlocking {
        doWorld()
        println("Done")
    }

    // Concurrently executes both sections
    suspend fun doWorld() = coroutineScope { // this: CoroutineScope
        launch {
            delay(2000L)
            println("World 2")
        }
        launch {
            delay(1000L)
            println("World 1")
        }
        println("Hello")
    }
}

internal class LightweightCoroutines {
    fun main() = runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(1000L)
                print(".")
            }
        }
    }
}

internal class Flows {
    fun simple(): Flow<Int> = flow { // flow builder
        for (i in 1..3) {
            delay(100) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }

    fun main() = runBlocking {
        // Launch a concurrent coroutine to check if the main thread is blocked
        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(100)
            }
        }
        // Collect the flow
        simple().collect { value -> println(value) }
    }
}

internal class FlowsWithTimeouts {
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            println("Emitting $i")
            emit(i)
        }
    }

    fun main() = runBlocking {
        withTimeoutOrNull(250) { // Timeout after 250ms
            simple().collect { value -> println(value) }
        }
        println("Done")
    }
}

internal class SuspendedFunctionsSequential {
    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun main() = runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }
}

internal class SuspendedFunctionsAsync {
    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    fun main() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")        }
        println("Completed in $time ms")
    }
}

fun main() {
//    SimpleAsync().main()
//
//    MultipleSuspendingFunctions().main()
//
//    LightweightCoroutines().main()


    /**
     * A suspending function asynchronously returns a single value, but how can we return multiple asynchronously computed values? This is where Kotlin Flows come in.
     */
    Flows().main()

    FlowsWithTimeouts().main()

    SuspendedFunctionsSequential().main()

    SuspendedFunctionsAsync().main()

}