package com.example.KPP.counter

import org.springframework.stereotype.Component

@Component
class ClashCounter {
    private var allRequests: Long = 0L
    private var nowRequests: Long = 0L

    @Synchronized
    fun increment(): Long {
        allRequests++
        return ++nowRequests
    }

    @Synchronized
    fun decrement(): Long {
        return --nowRequests
    }

    fun getStat(): Long = allRequests
    fun getCurrent(): Long = nowRequests
}