package cn.jailedbird.tpns.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        test2()
    }

    private fun test1() = runBlocking {
        var start = System.currentTimeMillis()
        println()
        val newFlow = numbers().map {
            delay(1000)
            it * it
        }
        println("变化操作符号 并不会主动执行 因此他返回的会很快: ${System.currentTimeMillis() - start}ms")
        start = System.currentTimeMillis()
        newFlow.collect {
            println(it)
        }

        println("当出现终端操作符才会触发内部的顺序执行 耗时很多: ${System.currentTimeMillis() - start}ms")
    }

    private fun test2() = runBlocking {
        (1..5).asFlow()
            .filter {
                println("Filter $it")
                it % 2 == 0
            }
            .map {
                println("Map $it")
                "string $it"
            }.collect {
                println("Collect $it")
            }
    }

    private fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }


}