package com.interview.test.orderservice.model

import javax.validation.constraints.NotNull


class CartItems {
	@get: NotNull
	var customerId: String = ""
	
	@get: NotNull
	var cartItems: List<String> = emptyList()

}