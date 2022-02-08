package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface MessageAutoDeleteTimerChanged {
	/** New auto-delete time for messages in the chat; in seconds */
	val message_auto_delete_time: Int
}