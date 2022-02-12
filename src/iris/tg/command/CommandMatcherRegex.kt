package iris.tg.command

import iris.tg.api.items.Message

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class CommandMatcherRegex<M>(private val commandPattern: Regex, private val runCommand: CommandRegex<M>): CommandMatcherWithHash<M> {

	constructor(commandPattern: String, runCommand: (message: M, params: List<String>) -> Unit) : this(Regex(commandPattern), runCommand)

	constructor(commandPattern: Regex, runCommand: (message: M, params: List<String>) -> Unit) : this(commandPattern, object : CommandRegex<M> {
		override fun run(message: M, groupValues: List<String>) {
			runCommand(message, groupValues)
		}
	})

	override fun testAndExecute(command: String, message: M): Boolean {
		val matcher = commandPattern.matchEntire(command)?: return false
		runCommand.run(message, matcher.groupValues)
		return false
	}

	override fun hashChars(): CharArray? {
		return commandPattern.pattern.firstOrNull()?.let {
			if (it.isLetterOrDigit())
				charArrayOf(it)
			else
				null
		}
	}

	interface CommandRegex<M> {
		fun run(message: M, groupValues: List<String>)
	}
}