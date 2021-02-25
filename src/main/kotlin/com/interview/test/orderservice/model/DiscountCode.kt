package com.interview.test.orderservice.model

import com.interview.test.orderservice.entity.Item

enum class DiscountCode(val discount: Int) {

	BOGO(2),
	B2GO(3);

	/**
	 * Returns an enum entry with the specified name or `null` if no such entry was found.
	 */

	fun isValid(code: String): DiscountCode? {
		var codes = values()
		var discountCode: DiscountCode? = null
		for (discountCode in codes) {
			if (discountCode.equals(code)) {
				return discountCode
			}
		}
		return discountCode
	}

	companion object {
		fun applyDiscount(item: Item, totalItemCount: Int): Int {
			var codes = values()
			var selectedDiscountCode: DiscountCode? = null
			for (discountCode in codes) {
				if (discountCode.name.equals(item.discountCode)) {
					selectedDiscountCode = discountCode
					break
				}
			}
			var itemDiscount = 0

			if (selectedDiscountCode != null && item.enableDiscount) {
				val discountNumber = selectedDiscountCode.discount
				itemDiscount = (totalItemCount / discountNumber) * item.price
			}
			return itemDiscount
		}
	}
}