//package com.interview.test.orderservice.model
//
//enum class Item(val type: String, val price: Int, val enableDiscount: Boolean, val discountCode: DiscountCode) {
//
//	Apple("Apple", 60, true, DiscountCode.BOGO),
//	Orange("Orange", 25, true, DiscountCode.B2GO);
//
//
//	/**
//	 * Returns an enum entry with the specified name or `null` if no such entry was found.
//	 */
//	companion object {
//		fun getItem(type: String): Item? {
//			var items = values()
//			var item: Item? = null
//
//			for (item in items) {
//				if (item.type.equals(type)) {
//					return item
//				}
//			}
//			return item
//		}
//	}
//}