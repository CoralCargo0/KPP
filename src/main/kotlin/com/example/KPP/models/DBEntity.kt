package com.example.KPP.models

import org.springframework.data.annotation.Id

data class DBEntity(
    @Id
    val id: Long = 0L,
    val weightOfFirst: Double = 0.0,
    val speedOfFirst: Double = 0.0,
    val weightOfSecond: Double = 0.0,
    val speedOfSecond: Double = 0.0,
    val result: Double = 0.0
)