package iris.tg.command

import iris.tg.event.Message

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
open class SimpleCommand(private val commandTemplate: String, private val runCommand: (message: Message) -> Unit) : CommandWithHash {
	override fun testAndExecute(command: String, message: Message): Boolean {
		if (commandTemplate != command) return false
		runCommand(message)
		return true
	}

	override fun hashChars() = commandTemplate.firstOrNull()?.let { charArrayOf(it) }
}