package com.example.KPP.cache

import com.example.KPP.models.ClashJPARepository
import com.example.KPP.models.DBEntity
import com.example.KPP.models.InelasticClashOfTwo
import com.example.KPP.models.calculateSpeedAfterClash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component


@Component
class ClashCache {

    @Autowired
    val repo: ClashJPARepository? = null

    private val cache = HashMap<Long, Pair<InelasticClashOfTwo, Double>>()
    var id: Long = 0L

    fun initId() {
        id = repo?.findAll(Sort.by(Sort.Direction.ASC, "id"))?.last()?.id ?: 0L
    }

    fun addClash(result: InelasticClashOfTwo): Long {
        id = repo?.count() ?: 0L
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
                DBEntity(
                    id = idOfExisting,
                    weightOfFirst = result.weightOfFirst,
                    weightOfSecond = result.weightOfSecond,
                    speedOfSecond = result.speedOfSecond,
                    speedOfFirst = result.speedOfFirst,
                    result = result.calculateSpeedAfterClash()
                )
            )
            cache[id] = Pair(result, result.calculateSpeedAfterClash())
            id++
        }
        return idOfExisting
    }

    fun getNewId(): Long = id++

    fun getClash(key: Long): Pair<InelasticClashOfTwo, Double>? {
        return if (cache[key] != null) {
            Pair(cache[key]!!.first, cache[key]!!.second)
        } else
            null
    }


    fun async(result: InelasticClashOfTwo, _idd: Long): Boolean {
        repo?.save(
            DBEntity(
                id = _idd,
                weightOfFirst = result.weightOfFirst,
                weightOfSecond = result.weightOfSecond,
                speedOfSecond = result.speedOfSecond,
                speedOfFirst = result.speedOfFirst,
                result = result.calculateSpeedAfterClash()
            )
        )
        cache[_idd] = Pair(result, result.calculateSpeedAfterClash())
        return true
    }

    fun getById(id: Long): DBEntity? {
        return try {
            repo?.findById(id)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }
}