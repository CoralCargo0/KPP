package com.example.KPP.cache

import com.example.KPP.models.ClashJPARepository
import com.example.KPP.models.DBentity
import com.example.KPP.models.InelasticClashOfTwo
import com.example.KPP.models.calculateSpeedAfterClash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ClashCache {

    @Autowired
    val repo: ClashJPARepository? = null

    private val cache = HashMap<Long, Pair<InelasticClashOfTwo, Double>>()
    var id: Long = 0L

    fun isContain(key: Long): Boolean {
        return cache.containsKey(key)
    }

    fun addClash(result: InelasticClashOfTwo): Long {
        var idOfExisting: Long = id
        cache.forEach { (k, v) ->

            if (v.first.speedOfFirst == result.speedOfFirst &&
                v.first.speedOfSecond == result.speedOfSecond &&
                v.first.weightOfFirst == result.weightOfFirst &&
                v.first.weightOfSecond == result.weightOfSecond
            ) {
                idOfExisting = k
            }
        }
        if (idOfExisting == id) {
            repo?.save(
                DBentity(
                    weightOfFirst = result.weightOfFirst,
                    weightOfSecond = result.weightOfSecond,
                    speedOfSecond = result.speedOfSecond,
                    speedOfFirst = result.speedOfFirst
                )
            )
            cache[id] = Pair(result, result.calculateSpeedAfterClash())
            id++
        }
        println(repo?.findAll().toString())
        return idOfExisting
    }

    fun getClash(key: Long): Pair<InelasticClashOfTwo, Double>? {
        return if (cache[key] != null) {
            Pair(cache[key]!!.first, cache[key]!!.second)
        } else
            null
    }

//    fun getAllClashes(): HashMap<Long, InelasticClashOfTwo> {
//        return cache
//    }
}