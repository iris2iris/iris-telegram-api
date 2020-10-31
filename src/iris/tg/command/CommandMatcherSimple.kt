package iris.tg.command

import iris.tg.event.Message

open class CommandMatcherSimple(private val commandTemplate: String, private val runCommand: Command) : CommandMatcherWithHash {

	constructor(commandPattern: String, runCommand: (message: Message) -> Unit) : this(commandPattern, object : Command {
		override fun run(message: Message) {
			runCommand(message)
		}
	} )

	override fun testAndExecute(command: String, message: Message): Boolean {
		if (commandTemplate != command) return false
		runCommand.run(message)
		return true
	}

	override fun hashChars() = commandTemplate.firstOrNull()?.let { charArrayOf(it) }


}