package com.interview.test.orderservice.service

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.web.server.LocalServerPort
import org.junit.BeforeClass
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import com.interview.test.orderservice.model.CartItems
import com.interview.test.orderservice.model.Order
import org.springframework.http.ResponseEntity
import org.assertj.core.api.Assertions
import com.interview.test.orderservice.entity.Item

import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderServiceTest {

	
	@LocalServerPort
	val port: Int = 8080
	
	val testRestTemplate: TestRestTemplate = TestRestTemplate()

	var url: String = "http://localhost:$port//customer-service"


	@Before
	fun configureSystemUnderTest() {

		var item1: Item = Item()
		item1.type = "Apple"
		item1.price = 25
		item1.enableDiscount = true
		item1.discountCode = "BOGO"

		var item2: Item = Item()
		item2.type = "Orange"
		item2.price = 75
		item2.enableDiscount = true
		item2.discountCode = "B2GO"

		var entity: HttpEntity<Item> = HttpEntity<Item>(item1)
		var response: ResponseEntity<Item> ?=
			testRestTemplate.postForEntity(url + "/item", entity, Item().javaClass, null)

		Assertions.assertThat(response != null)
		Assertions.assertThat(response?.getStatusCodeValue() == 200)

		var entity2: HttpEntity<Item> = HttpEntity<Item>(item2)
		var response2: ResponseEntity<Item> ?=
			testRestTemplate.postForEntity(url + "/item", entity2, Item().javaClass, null)

		Assertions.assertThat(response2 != null)
		Assertions.assertThat(response2?.getStatusCodeValue() == 200)


	}

	@Test
	fun placeOrderWithCartItems() {
		var cart: CartItems = CartItems()
		cart.customerId = "TestUser"
		cart.cartItems = listOf("Apple", "Orange")

		var entity: HttpEntity<CartItems> = HttpEntity<CartItems>(cart)
		var response: ResponseEntity<Order> ?=
			testRestTemplate.postForEntity(url + "/order", entity, Order().javaClass, null)

		Assertions.assertThat(response != null)
		Assertions.assertThat(response?.getStatusCodeValue() == 201)
		Assertions.assertThat(response?.getBody()?.totalPrice == 1.0)
	}


}