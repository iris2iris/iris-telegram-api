package iris.tg.processors.pack

import iris.tg.api.items.Message

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgTextPackHandler<M: Message> {
	fun texts(messages: List<M>)
}