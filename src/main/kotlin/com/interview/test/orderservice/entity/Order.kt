package com.interview.test.orderservice.model

import com.interview.test.orderservice.entity.CustomerItem
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.NotBlank
import com.fasterxml.jackson.annotation.JsonManagedReference


@Entity
@Table(name = "orders")
class Order() {

	@Id @GeneratedValue(
		strategy = GenerationType.IDENTITY
	) var id: Long = 0

	@get: NotBlank var customerId: String = ""
	@get: NotNull var totalPrice: Double=0.0
	@get: NotNull var orderStatus: OrderStatus=OrderStatus.NA
	
	@JsonManagedReference
	@OneToMany(mappedBy = "orders", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
    @get: NotNull var itemsList : List<CustomerItem> = emptyList()
}