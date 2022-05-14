package com.example.KPP.models

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class InelasticClashOfTwo(

    val weightOfFirst: Double = 0.0,
    val speedOfFirst: Double = 0.0,
    val weightOfSecond: Double = 0.0,
    val speedOfSecond: Double = 0.0
)

fun InelasticClashOfTwo.getWeight1(): Double {
    return weightOfFirst
}

fun InelasticClashOfTwo.getWeight2(): Double {
    return weightOfSecond
}

fun InelasticClashOfTwo.getSpeed1(): Double {
    return speedOfFirst
}

fun InelasticClashOfTwo.getSpeed2(): Double {
    return speedOfSecond
}

fun InelasticClashOfTwo.calculateSpeedAfterClash(): Double {
    this.apply {
        val speedAfterClash: Double = if (this.isHeadOnClash()) {
            sqrt(
                abs(
                    weightOfFirst * speedOfFirst.pow(2.0) - weightOfSecond * speedOfSecond.pow(2.0)
                ) / (weightOfFirst + weightOfSecond)
            )
        } else {
            sqrt(
                abs(
                    weightOfFirst * speedOfFirst.pow(2.0) + weightOfSecond * speedOfSecond.pow(2.0)
                ) / (weightOfFirst + weightOfSecond)
            )
        }
        return if (speedAfterClash.isNaN()) 0.0 else speedAfterClash
    }
}

fun InelasticClashOfTwo.isHeadOnClash(): Boolean = !(speedOfFirst > 0 && speedOfSecond > 0)