package com.example.KPP.models;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface ClashJPARepository : CrudRepository<DBentity, Int> {
}