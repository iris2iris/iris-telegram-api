package iris.tg.command

import iris.tg.api.items.Message


open class CommandMatcherSimple<M: Message>(private val commandTemplate: String, private val runCommand: Command<M>) : CommandMatcherWithHash<M> {

	constructor(commandPattern: String, runCommand: (message: M) -> Unit) : this(commandPattern, object : Command<M> {
		override fun run(message: M) {
			runCommand(message)
		}
	} )

	override fun testAndExecute(command: String, message: M): Boolean {
		if (commandTemplate != command) return false
		runCommand.run(message)
		return true
	}

	override fun hashChars() = commandTemplate.firstOrNull()?.let { charArrayOf(it) }


}