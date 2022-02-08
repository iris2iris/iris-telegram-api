package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface VoiceChatScheduled {

	/** Point in time (Unix timestamp) when the voice chat is supposed to be started by a chat administrator */
	val start_date: Int
}