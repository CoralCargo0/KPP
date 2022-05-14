package com.example.KPP.dto

import com.example.KPP.models.InelasticClashOfTwo
import com.example.KPP.models.calculateSpeedAfterClash
import java.util.function.Function
import java.util.stream.Collectors


data class BulkDto(
    var totalAmount: Int = 0,
    var totalAmountIncorrect: Int = 0,
    var minResult: Double = 0.0,
    var maxResult: Double = 0.0,
    var popularResult: Double = 0.0
)

fun BulkDto.config(clashList: List<InelasticClashOfTwoPostDto>) {
    this.apply {
        var tmp: InelasticClashOfTwo?
        val resultsList = clashList.map {
            tmp = it.toClash()
            if (tmp == null) {
                totalAmount++
                totalAmountIncorrect++
            } else {
                totalAmount++
            }
            tmp?.calculateSpeedAfterClash()
        } as MutableList
        resultsList.removeIf { it == null }
        maxResult = resultsList.stream().mapToDouble(::mmmmm).max().asDouble
        minResult = resultsList.stream().mapToDouble(::mmmmm).min().asDouble
        var tmpLong: Long = -1
        resultsList.stream().collect(
            Collectors.toMap(
                Function.identity(),
                { v: Double? -> 1L },
                { a: Long, b: Long -> a + b })
        ).forEach {
            if (it.value > tmpLong) {
                tmpLong = it.value
                popularResult = it.key!!
            }
        }
    }
}

fun mmmmm(mm: Double?) = mm!!
