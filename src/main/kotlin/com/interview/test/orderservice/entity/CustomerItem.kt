package com.interview.test.orderservice.entity

import com.interview.test.orderservice.model.Order
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.NotBlank
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonBackReference

@Entity
@Table(name = "customeritem")
class CustomerItem {
	
	
	@Id @GeneratedValue(
		strategy = GenerationType.IDENTITY
	) var id: Long = 0

	@get: NotNull var totalPrice: Int = 0
	@get: NotNull var actualPrice: Int = 0
	@get: NotNull var discountAmount: Int = 0
	@get: NotNull var itemCount: Int = 0

	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
	var orders: Order ? = null
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
	@get: NotNull var item: Item ? = null
}