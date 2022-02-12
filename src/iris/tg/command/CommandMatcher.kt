package iris.tg.command

import iris.tg.api.items.Message


/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface CommandMatcher<M> {
	fun testAndExecute(command: String, message: M): Boolean
}