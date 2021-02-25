package com.interview.test.orderservice.entity

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "item")
class Item {
	
	@Id @GeneratedValue(
		strategy = GenerationType.IDENTITY
	) var id: Long = 0
	@get: NotBlank var type: String = ""
	@get: NotNull var price: Int = 0
	@get: NotNull var enableDiscount: Boolean = false
	@get: NotBlank var discountCode: String = ""
	
	//@OneToMany(mappedBy = "item", cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
    //var itemsList : List<CustomerItem> = emptyList()

}