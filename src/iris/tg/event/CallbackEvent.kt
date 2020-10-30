package iris.tg.event

/**
 * @created 28.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface CallbackEvent : Event {
	val id: String
	val fromId: Long
	val peerId: Long
	val data: String
	val conversationMessageId: Int
}