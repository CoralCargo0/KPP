package com.example.KPP

import com.example.KPP.cache.ClashCache
import com.example.KPP.dto.ResultDto
import com.example.KPP.models.ClashJPARepository
import com.example.KPP.models.InelasticClashOfTwo
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class InelasticClashService {

    @Autowired
    val cache: ClashCache? = null


    private val logger: Logger = LogManager.getLogger(InelasticClashService::class.java)

    var mainId: Long = 0L

    fun getLastClashResult(): ResultDto {
        val tmp = cache?.getClash(mainId)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Last clash not found"
            )
        return ResultDto(tmp.second)
    }

    fun getClashResult(_id: Long): ResultDto {
        val tmp = cache?.getClash(_id)
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "Clash not found"
            )
        return ResultDto(tmp.second)
    }

    fun addClash(
        weightOfFirst: String,
        speedOfFirst: String,
        weightOfSecond: String,
        speedOfSecond: String
    ): ResultDto {

        var id: Long
        try {
            val clash = InelasticClashOfTwo(
                weightOfFirst = weightOfFirst.toDouble(),
                speedOfFirst = speedOfFirst.toDouble(),
                weightOfSecond = weightOfSecond.toDouble(),
                speedOfSecond = speedOfSecond.toDouble()
            )
            //id = clashCounter?.getStat() ?: 0
            id = cache?.addClash(clash) ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "No cache class"
            )
            return getClashResult(id)
        } catch (e: NumberFormatException) {
            logger.error("Error when adding clash - $e")
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY, "Bad input", e
            )
        }
//        try {
//            val clash = InelasticClashOfTwo(
//                weightOfFirst = weightOfFirst.toDouble(),
//                speedOfFirst = speedOfFirst.toDouble(),
//                weightOfSecond = weightOfSecond.toDouble(),
//                speedOfSecond = speedOfSecond.toDouble()
//            )
//            mainId = (clashCounter?.getStat() ?: cache?.addClash(mainId, clash)) as Long
//            println("Nums of clashes - ${mainId + 1}")
//        } catch (e: NumberFormatException) {
//            logger.info("Error when adding clash - $e")
//            throw ResponseStatusException(
//                HttpStatus.UNPROCESSABLE_ENTITY, "Bad input", e
//            )
//        }
    }

    fun addClash(
        clashOfTwo: InelasticClashOfTwo
    ): ResultDto {
        var id: Long
        try {
            id = cache?.addClash(clashOfTwo) ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "No cache class"
            )
            return getClashResult(id)
        } catch (e: NumberFormatException) {
            logger.info("Error when adding clash - $e")
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY, "Bad input", e
            )
        }
    }
}