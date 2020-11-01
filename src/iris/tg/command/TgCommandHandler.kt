package iris.tg.command

import iris.tg.TgEventHandlerAdapter
import iris.tg.TgTriggerEventHandler
import iris.tg.event.Message

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */

open class TgCommandHandler(
	private val commandBuilder: CommandExtractor = CommandExtractorDefault(null),
	private val searchFirst: Boolean = true
) : TgEventHandlerAdapter(), TgTriggerEventHandler.TriggerMessage {

	private val map = mutableMapOf<Char, MutableList<CommandMatcher>>()

	constructor(commandBuilder: CommandExtractor = CommandExtractorDefault(null),
				searchFirst: Boolean = true, commands: Iterable<CommandMatcherWithHash>) : this(commandBuilder, searchFirst) {
		addAllWithHash(commands)
	}

	operator fun plusAssign(command: CommandMatcher) {
		add(command, null)
	}

	operator fun plusAssign(command: CommandMatcherWithHash) {
		add(command, command.hashChars())
	}

	operator fun plusAssign(command: Pair<String, CommandMatcher>) {
		add(command.second, command.first.toCharArray())
	}

	operator fun plusAssign(commands: Iterable<Pair<CommandMatcher, CharArray?>>) {
		addAll(commands)
	}

	open fun add(command: CommandMatcher): TgCommandHandler {
		return add(command, null)
	}

	open fun add(command: CommandMatcherWithHash): TgCommandHandler {
		return add(command, command.hashChars())
	}

	open fun add(command: CommandMatcher, chars: CharArray?): TgCommandHandler {
		if (chars == null)
			map.getOrPut(NULL_CHAR) { mutableListOf() }.add(command)
		else
			for (char in chars)
				map.getOrPut(char) { mutableListOf() }.add(command)
		return this
	}

	fun addAll(commands: Iterable<Pair<CommandMatcher, CharArray?>>): TgCommandHandler {
		for (it in commands) add(it.first, it.second)
		return this
	}

	companion object {
		private const val NULL_CHAR = '\u0000'
	}

	override fun processMessage(message: Message) {
		val command = commandBuilder.extractCommand(message)?: return
		if (command.isEmpty()) return
		val hash = command[0]
		var hashItems = map[hash]
		if (hashItems != null) {
			for (c in hashItems)
				if (c.testAndExecute(command, message))
					if (searchFirst)
						return
		}
		hashItems = map[NULL_CHAR]
		if (hashItems != null) {
			for (c in hashItems)
				if (c.testAndExecute(command, message))
					if (searchFirst)
						return
		}
	}

	override fun process(messages: List<Message>) = processMessages(messages)

	fun addCommands(vararg commands: CommandMatcher) {
		for (it in commands)
			when (it) {
				is CommandMatcherWithHash -> add(it)
				else -> add(it)
			}
	}

	fun addCommands(vararg commands: CommandMatcherWithHash): TgCommandHandler {
		for (it in commands)
			add(it)
		return this
	}

	fun addAllWithHash(commands: Iterable<CommandMatcherWithHash>): TgCommandHandler {
		for (it in commands) add(it, it.hashChars())
		return this
	}
}