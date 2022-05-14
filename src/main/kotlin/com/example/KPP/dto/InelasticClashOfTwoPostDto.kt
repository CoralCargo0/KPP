package com.example.KPP.dto

import com.example.KPP.models.InelasticClashOfTwo

data class InelasticClashOfTwoPostDto(
    val weightOfFirst: String? = null,
    val speedOfFirst: String? = null,
    val weightOfSecond: String? = null,
    val speedOfSecond: String? = null
)

fun InelasticClashOfTwoPostDto.toClash(): InelasticClashOfTwo? {
    return try {
        InelasticClashOfTwo(
            weightOfFirst = this.weightOfFirst!!.toDouble(),
            speedOfFirst = this.speedOfFirst!!.toDouble(),
            weightOfSecond = this.weightOfSecond!!.toDouble(),
            speedOfSecond = this.speedOfSecond!!.toDouble()
        )
    } catch (e: Throwable) {
        null
    }
}
