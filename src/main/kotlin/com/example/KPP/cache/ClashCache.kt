package com.example.KPP.cache

import com.example.KPP.InelasticClashOfTwo
import org.springframework.stereotype.Component


@Component
class ClashCache {
    private val cache = HashMap<Long, InelasticClashOfTwo>()

    fun isContain(key: Long): Boolean {
        return cache.containsKey(key)
    }

    fun addClash(key: Long, result: InelasticClashOfTwo) {
        cache[key] = result
    }

    fun getClash(key: Long): InelasticClashOfTwo? {
        return cache[key]
    }

    fun getAllClashes(): HashMap<Long, InelasticClashOfTwo> {
        return cache
    }
}