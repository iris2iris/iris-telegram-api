package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Contact {
	/** Contact's phone number */
	val phone_number: String

	/** Contact's first name */
	val first_name: String

	/** Optional. Contact's last name */
	val last_name: String?

	/** Optional. Contact's user identifier in Telegram. This number may have more than 32 significant bits and some programming languages may have difficulty/silent defects in interpreting it. But it has at most 52 significant bits, so a 64-bit integer or double-precision float type are safe for storing this identifier. */
	val user_id: Long

	/** Optional. Additional data about the contact in the form of a vCard */
	val vcard: String?
}