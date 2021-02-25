package com.interview.test.orderservice.service

import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.KafkaProducer
import java.util.Properties
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import com.interview.test.orderservice.model.DiscountCode

import com.interview.test.orderservice.exception.OrderServiceException
import org.springframework.stereotype.Service
import com.interview.test.orderservice.repository.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import com.interview.test.orderservice.model.Order
import com.interview.test.orderservice.model.CartItems
import com.interview.test.orderservice.repository.ItemRepository
import com.interview.test.orderservice.entity.Item
import com.interview.test.orderservice.entity.CustomerItem
import org.springframework.dao.EmptyResultDataAccessException
import com.interview.test.orderservice.model.OrderStatus
import org.slf4j.LoggerFactory

@Service
class OrderService(
	@Autowired private val orderRepository: OrderRepository,
	@Autowired private val itemRepository: ItemRepository,
	@Autowired private val notificationService: CartProducerNotificationService
) {

	companion object {
		private val LOGGER = LoggerFactory.getLogger(OrderService::class.java)
	}

	fun getAllOrders(): List<Order> = orderRepository.findAll()

	fun createOrder(cart: CartItems): Order {

		val itemsList: MutableList<CustomerItem> = mutableListOf()
		//var itemsList = listOf<CustomerItem>()
		val order: Order = Order()
		order.customerId = cart.customerId
		order.orderStatus = OrderStatus.INPROGRESS

		LOGGER.info("Placing an order with the given cart. $cart")
		var cartPrice: Double = 0.0

		//group the cart items
		val cartItems = cart.cartItems.groupingBy { it.apply {} }.eachCount()

		for ((item, count) in cartItems) {
			//check if item is valid

			var selectedItem: Item? = null;

			try {
				LOGGER.debug("Retreiving item")
				selectedItem = itemRepository.findByType(item)
			} catch (e: EmptyResultDataAccessException) {

			}

			if (selectedItem === null) {
				LOGGER.error("Invalid cart item Found.")
				throw OrderServiceException("Invalid Item: $item")
			}

			//Add item to the order
			LOGGER.info("Adding $count $item(s) to the order")


			//cal regualar price
			val regualrPrice = count * selectedItem.price
			//apply discount if applicable
			val discount = DiscountCode.applyDiscount(selectedItem, count)

			val itemTotalPrice = (regualrPrice - discount)
			LOGGER.info("Applied a disocunt of $discount on $selectedItem")

			val custormerItem: CustomerItem = CustomerItem()
			custormerItem.totalPrice = itemTotalPrice
			custormerItem.actualPrice = regualrPrice
			custormerItem.discountAmount = discount
			custormerItem.itemCount = count
			custormerItem.orders = order
			custormerItem.item = selectedItem

			itemsList.add(custormerItem)

			cartPrice = cartPrice + itemTotalPrice
		}

		//TODO: Check the inventory and modify the status accordingly

		order.totalPrice = cartPrice/100
		order.itemsList = itemsList
		order.orderStatus = OrderStatus.PLACED

		orderRepository.save(order)

		LOGGER.info("Placed the order succesfully $order")
		//TODO: make it async
		try {
			notificationService.nofifyOrderStatus(order)
		} catch (e: Exception) {
			LOGGER.error("Failed to notify the order stutus to customer. Order detail: $order")
		}

		return order
	}

}


