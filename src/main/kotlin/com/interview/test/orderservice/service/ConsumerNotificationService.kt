package com.interview.test.orderservice.service

import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import com.interview.test.orderservice.config.KafkaProducerConfiguration


class ConsumerNotificationService {


	@KafkaListener(topics = [KafkaProducerConfiguration.ORDER_STATUS_TOPIC])
	fun receive(payload: String) {
		LOGGER.info("Received payload='$payload'")
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(ConsumerNotificationService::class.java)
	}
}