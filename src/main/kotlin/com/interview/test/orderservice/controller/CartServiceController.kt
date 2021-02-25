package com.interview.test.orderservice.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import com.interview.test.orderservice.service.OrderService
import com.interview.test.orderservice.model.Order
import com.interview.test.orderservice.model.CartItems
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import javax.validation.Valid

@RestController
@RequestMapping("/customer-service/order")
class CartServiceController(@Autowired private val orderService : OrderService) {
	
	
	@GetMapping
    fun getAllJournals() : List<Order> = orderService.getAllOrders()
	
	@PostMapping
    fun createOrder(@Valid @RequestBody cart : CartItems) : ResponseEntity<Any>  {
		if(cart.cartItems.isEmpty()) {
			return ResponseEntity("cart items missing", HttpStatus.BAD_REQUEST)
		}
		var order: Order = orderService.createOrder(cart)
		return ResponseEntity(order, HttpStatus.CREATED)
	}

	
}