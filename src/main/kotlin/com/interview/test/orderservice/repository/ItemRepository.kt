package com.interview.test.orderservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.interview.test.orderservice.entity.Item
@Repository
interface ItemRepository : JpaRepository<Item, Long> {
	
	fun findByType(type : String) : Item
}