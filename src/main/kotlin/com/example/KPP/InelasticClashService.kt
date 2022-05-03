package com.example.KPP

import com.example.KPP.cache.ClashCache
import com.example.KPP.counter.ClashCounter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class InelasticClashService(
    val cache: ClashCache,
    val clashCounter: ClashCounter,
) {
    private val logger: Logger = LogManager.getLogger(InelasticClashService::class.java)

    var id: Long = 0L

    fun getLastClashResult(): ResultDto {
        val tmp = cache.getClash(id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Last clash not found"
            )
        return ResultDto(tmp.calculateSpeedAfterClash())
    }

    fun addClash(
        weightOfFirst: String,
        speedOfFirst: String,
        weightOfSecond: String,
        speedOfSecond: String
    ) {
        try {
            val clash = InelasticClashOfTwo(
                weightOfFirst = weightOfFirst.toDouble(),
                speedOfFirst = speedOfFirst.toDouble(),
                weightOfSecond = weightOfSecond.toDouble(),
                speedOfSecond = speedOfSecond.toDouble()
            )
            id = clashCounter.getStat()
            cache.addClash(id, clash)
            println("Nums of clashes - ${id + 1}")
        } catch (e: NumberFormatException) {
            logger.info("Error when adding clash - $e")
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY, "Bad input", e
            )
        }
    }

    fun addClash(
        clashOfTwo: InelasticClashOfTwo
    ) {
        try {
            id = clashCounter.getStat()
            cache.addClash(id, clashOfTwo)
            println("Nums of clashes - ${id + 1}")
        } catch (e: NumberFormatException) {
            logger.info("Error when adding clash - $e")
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY, "Bad input", e
            )
        }
    }
}