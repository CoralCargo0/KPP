package com.example.KPP.counter

import org.springframework.stereotype.Component

@Component
class ClashCounter {
    private var allNum: Long = 0L
    private var num: Long = 0L

    @Synchronized
    fun increment(): Long {
        allNum++
        return num++
    }

    @Synchronized
    fun decrement(): Long {
        return --num
    }

    fun getStat(): Long = allNum
    fun getCurrent(): Long = num
}