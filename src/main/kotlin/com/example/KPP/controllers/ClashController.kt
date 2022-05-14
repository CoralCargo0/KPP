package com.example.KPP.controllers

import com.example.KPP.*
import com.example.KPP.common.Constants
import com.example.KPP.counter.ClashCounter
import com.example.KPP.dto.BulkDto
import com.example.KPP.dto.InelasticClashOfTwoPostDto
import com.example.KPP.dto.ResultDto
import com.example.KPP.dto.config
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
class ClashController {
    @Autowired
    val service: InelasticClashService? = null

    @Autowired
    val clashCounter: ClashCounter? = null
    private val logger: Logger = LogManager.getLogger(ClashController::class.java)


    @GetMapping("/calculate")
    fun calculate(
        @RequestParam(value = Constants.WEIGHT_OF_FIRST, defaultValue = "0.0") weightOfFirst: String,
        @RequestParam(value = Constants.SPEED_OF_FIRST, defaultValue = "0.0") speedOfFirst: String,
        @RequestParam(value = Constants.WEIGHT_OF_SECOND, defaultValue = "0.0") weightOfSecond: String,
        @RequestParam(value = Constants.SPEED_OF_SECOND, defaultValue = "0.0") speedOfSecond: String
    ): ResultDto {
        clashCounter?.increment()
        logger.info(
            "GET /calculate request params:${Constants.WEIGHT_OF_FIRST} - $weightOfFirst, " +
                    "${Constants.SPEED_OF_FIRST} - $speedOfFirst, " +
                    "${Constants.WEIGHT_OF_SECOND} - $weightOfSecond, " +
                    "${Constants.SPEED_OF_SECOND} - $speedOfSecond"
        )
        val result: ResultDto = service?.addClash(
            weightOfFirst,
            speedOfFirst,
            weightOfSecond,
            speedOfSecond
        ) ?: throw ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR
        )

        logger.info(
            "GET /calculate - amount of handled requests - ${clashCounter?.getStat()}, now handling - ${clashCounter?.getCurrent()}"
        )
        clashCounter?.decrement()
        return result
    }


    @PostMapping("/bulk/calculate")
    fun bulkCalculate(@RequestBody clashes: List<InelasticClashOfTwoPostDto>): BulkDto {
        logger.info("POST /bulk/calculate params: $clashes")
        val tmp = BulkDto()
        try {
            tmp.config(clashes)
        } catch (e: RuntimeException) {
            logger.error("POST /bulk/calculate Error:$e")
            throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, e.message, e
            )
        }
        return tmp
    }
}