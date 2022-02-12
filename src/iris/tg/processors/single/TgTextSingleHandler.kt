package iris.tg.processors.single

import iris.tg.api.items.Message

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgTextSingleHandler<M: Message> {
	fun text(message: M)
}