package com.interview.test.orderservice

import  com.interview.test.orderservice.service.OrderService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class CartServiceApplication

fun main(args: Array<String>) {
	runApplication<CartServiceApplication>(*args)
	
}