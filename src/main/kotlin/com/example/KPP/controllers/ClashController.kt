package com.example.KPP.controllers

import com.example.KPP.InelasticClashOfTwo
import com.example.KPP.InelasticClashService
import com.example.KPP.ResultDto
import com.example.KPP.common.Constants
import com.example.KPP.counter.ClashCounter
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
class ClashController(
    val service: InelasticClashService,
    val clashCounter: ClashCounter
) {
    private val logger: Logger = LogManager.getLogger(ClashController::class.java)

    @GetMapping("/calculate")
    fun calculate(
        @RequestParam(value = Constants.WEIGHT_OF_FIRST, defaultValue = "0.0") weightOfFirst: String,
        @RequestParam(value = Constants.SPEED_OF_FIRST, defaultValue = "0.0") speedOfFirst: String,
        @RequestParam(value = Constants.WEIGHT_OF_SECOND, defaultValue = "0.0") weightOfSecond: String,
        @RequestParam(value = Constants.SPEED_OF_SECOND, defaultValue = "0.0") speedOfSecond: String
    ): ResultDto {
        val result: ResultDto
        clashCounter.increment()
        logger.info(
            "GET /calculate request params:${Constants.WEIGHT_OF_FIRST} - $weightOfFirst, " +
                    "${Constants.SPEED_OF_FIRST} - $speedOfFirst, " +
                    "${Constants.WEIGHT_OF_SECOND} - $weightOfSecond, " +
                    "${Constants.SPEED_OF_SECOND} - $speedOfSecond"
        )
        service.addClash(
            weightOfFirst,
            speedOfFirst,
            weightOfSecond,
            speedOfSecond
        )
        try {
            result = service.getLastClashResult()
        } catch (e: RuntimeException) {
            logger.info("GET /calculate Error:$e")
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, e.message, e
            )
        }
        clashCounter.decrement()
        return result
    }


    @PostMapping("/bulk/calculate")
    fun bulkCalculate(@RequestBody clashes: List<InelasticClashOfTwo>): ResultDto {
        val result: ResultDto
        clashCounter.increment()
        logger.info("POST /bulk/calculate params: $clashes")
        service.addClash(
            clashes[0]
        )
        try {
            result = service.getLastClashResult()
        } catch (e: RuntimeException) {
            logger.info("POST /bulk/calculate Error:$e")
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, e.message, e
            )
        }
        clashCounter.decrement()
        return result
    }
}