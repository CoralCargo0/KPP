package com.example.KPP.models;

import org.springframework.data.mongodb.repository.MongoRepository

interface ClashJPARepository : MongoRepository<DBEntity, Int> {
    fun findById(id: Long): DBEntity
}