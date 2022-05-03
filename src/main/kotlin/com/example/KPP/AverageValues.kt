package com.example.KPP

import java.util.*


class AverageValues {
    var greetingAnswerList: List<InelasticClashOfTwo> = LinkedList<InelasticClashOfTwo>()
    var numAverage = 0.0
    var numsAverage = arrayOf(0.0, 0.0, 0.0, 0.0)
    var numsMin = arrayOf(0.0, 0.0, 0.0, 0.0)
    var numsMax = arrayOf(0.0, 0.0, 0.0, 0.0)


    fun calcAverage(clashList: List<InelasticClashOfTwo>) {
        numsAverage[0] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight1).average().asDouble //average of stream
        numsAverage[1] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed1).average().asDouble //average of stream
        numsAverage[2] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight2).average().asDouble //average of stream
        numsAverage[3] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed2).average().asDouble //average of stream
    }

    fun calcMin(clashList: List<InelasticClashOfTwo>) {
        numsMin[0] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight1).min().asDouble //average of stream
        numsMin[1] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed1).min().asDouble //average of stream
        numsMin[2] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight2).min().asDouble //average of stream
        numsMin[3] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed2).min().asDouble //average of stream
    }

    fun calcMax(clashList: List<InelasticClashOfTwo>) {
        numsMax[0] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight1).max().asDouble //average of stream
        numsMax[1] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed1).max().asDouble //average of stream
        numsMax[2] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getWeight2).max().asDouble //average of stream
        numsMax[3] =
            clashList.stream().mapToDouble(InelasticClashOfTwo::getSpeed2).max().asDouble //average of stream
    }
}