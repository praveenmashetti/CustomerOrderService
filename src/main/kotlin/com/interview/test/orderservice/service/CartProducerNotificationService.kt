package com.interview.test.orderservice.service

import org.springframework.beans.factory.annotation.Autowired
import com.interview.test.orderservice.config.KafkaProducerConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import com.interview.test.orderservice.model.Order
import org.slf4j.LoggerFactory

@Service
class CartProducerNotificationService(@Autowired private val kafkaTemplate: KafkaTemplate<String, String>) {

	companion object {
		private val LOGGER = LoggerFactory.getLogger(CartProducerNotificationService::class.java)
	}

	fun nofifyOrderStatus(order: Order): Boolean {
		val mapper: ObjectMapper = ObjectMapper()
		try {
			kafkaTemplate.send(KafkaProducerConfiguration.ORDER_STATUS_TOPIC, mapper.writeValueAsString(order))
			LOGGER.info("Successfully posted the order status $order")
		} catch (e: Exception) {
			return false
		}

		return true
	}

	//TODO: scheduler to check failed orders or check for inventory


}