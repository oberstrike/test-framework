package com.maju.util

import org.junit.jupiter.api.Test

class MutableMapExtTest {

    private val key = "test"
    private val value = "test"

    @Test
    fun putTest() {
        val mutableMap = mutableMap()
        val pairToPut = key to value

        assert(mutableMap.isEmpty())
        mutableMap.put(pairToPut)
        assert(mutableMap.size == 1)

        val value = mutableMap[key]
        assert(value == this.value)

    }

    @Test
    fun putAllTest() {
        val mutableMap = mutableMap()
        val pairs = (0..10).map { i -> "test$i" to "test$i" }

        assert(mutableMap.isEmpty())
        mutableMap.putAll(pairs)
        assert(mutableMap.size == pairs.size)

        val random = pairs.random()

        val randomValue = mutableMap[random.first]
        assert(random.second == randomValue)

    }

    private fun mutableMap() = mutableMapOf<String, String>()


}