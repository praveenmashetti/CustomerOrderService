package com.interview.test.orderservice.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.GetMapping

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.http.HttpStatus
import com.interview.test.orderservice.repository.ItemRepository
import com.interview.test.orderservice.entity.Item
import javax.validation.Valid

@RestController
@RequestMapping("/customer-service/item")
class ItemController(@Autowired private val itemRepository: ItemRepository) {
	//gets all journals
	@GetMapping
	fun getAllItems(): List<Item> = itemRepository.findAll()

	//creates a item
	@PostMapping
	fun createItem(@Valid @RequestBody item: Item): Item = itemRepository.save(item)

	//gets a single item
	@GetMapping("/{itemId}")
	fun getItemById(@PathVariable itemId: Long): ResponseEntity<Item> =
		itemRepository.findById(itemId).map {
			ResponseEntity.ok(it)
		}.orElse(ResponseEntity.notFound().build())


/*	//updates a item
	@PutMapping("/{itemId}")
	fun updateItem(@PathVariable itemId: Long, @Valid @RequestBody updatedItem: Item)
			: ResponseEntity<Item> =
		itemRepository.findById(itemId).map {
			val newItem = it.copy(
				price = updatedItem.price,
				type = updatedItem.type,
				enableDiscount = updatedItem.enableDiscount,
				discountCode = updatedItem.discountCode
			)
			ResponseEntity.ok().body(itemRepository.save(newItem))
		}.orElse(ResponseEntity.notFound().build())
*/
	// deletes a item
	@DeleteMapping("/{itemId}")
	fun deleteItem(@PathVariable itemId: Long): ResponseEntity<Void> =
		itemRepository.findById(itemId).map {
			itemRepository.delete(it)
			ResponseEntity<Void>(HttpStatus.OK)
		}.orElse(ResponseEntity.notFound().build())
}