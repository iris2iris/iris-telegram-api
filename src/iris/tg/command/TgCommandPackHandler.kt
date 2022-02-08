package iris.tg.command

import iris.tg.api.items.Message
import iris.tg.processors.pack.TgTextPackHandler
import iris.tg.trigger.TgEventTriggerHandler

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

open class TgCommandPackHandler<M: Message>(
	private val commandExtractor: CommandExtractor = CommandExtractorDefault(null),
	private val untilFirstExecuted: Boolean = true
) : TgTextPackHandler<M>, TgEventTriggerHandler.TriggerMessage<M> {

	private val map = mutableMapOf<Char, MutableList<CommandMatcher>>()

	constructor(commandExtractor: CommandExtractor = CommandExtractorDefault(null),
				untilFirstExecuted: Boolean = true, commands: Iterable<CommandMatcherWithHash>) : this(commandExtractor, untilFirstExecuted) {
		addAllWithHash(commands)
	}

	constructor(commandExtractor: CommandExtractor = CommandExtractorDefault(null),
				untilFirstExecuted: Boolean = true, initializer: TgCommandPackHandler<M>.() -> Unit) : this(commandExtractor, untilFirstExecuted) {
		commands(initializer)
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

	open fun add(command: CommandMatcher) = add(command, null)

	open fun add(command: CommandMatcherWithHash) = add(command, command.hashChars())

	open fun add(command: CommandMatcher, chars: CharArray?): TgCommandPackHandler<M> {
		if (chars == null)
			map.getOrPut(NULL_CHAR) { mutableListOf() } += command
		else
			for (char in chars)
				map.getOrPut(char) { mutableListOf() } += command
		return this
	}

	fun addAll(commands: Iterable<Pair<CommandMatcher, CharArray?>>): TgCommandPackHandler<M> {
		for (it in commands) add(it.first, it.second)
		return this
	}

	companion object {
		private const val NULL_CHAR = '\u0000'
	}

	override fun texts(messages: List<M>) {
		for (m in messages)
			text(m)
	}

	fun text(message: M) {
		val command = commandExtractor.extractCommand(message)
		if (command.isNullOrEmpty()) return
		val hash = command[0]
		var hashItems = map[hash]
		if (hashItems != null) {
			for (c in hashItems)
				if (c.testAndExecute(command, message))
					if (untilFirstExecuted)
						return
		}
		hashItems = map[NULL_CHAR]
		if (hashItems != null) {
			for (c in hashItems)
				if (c.testAndExecute(command, message))
					if (untilFirstExecuted)
						return
		}
	}

	override fun process(message: M) = text(message)

	fun addCommands(vararg commands: CommandMatcher) {
		for (it in commands)
			when (it) {
				is CommandMatcherWithHash -> add(it)
				else -> add(it)
			}
	}

	fun addCommands(vararg commands: CommandMatcherWithHash): TgCommandPackHandler<M> {
		for (it in commands)
			add(it)
		return this
	}

	fun addAllWithHash(commands: Iterable<CommandMatcherWithHash>): TgCommandPackHandler<M> {
		for (it in commands) add(it, it.hashChars())
		return this
	}

	fun text(vararg patterns: String, command: (message: Message) -> Unit) {
		for (pattern in patterns)
			add(CommandMatcherSimple(pattern, command))
	}

	fun text(patterns: List<String>, command: (message: Message) -> Unit) {
		for (pattern in patterns)
			add(CommandMatcherSimple(pattern, command))
	}

	fun text(pattern: String, command: Command) {
		add(CommandMatcherSimple(pattern, command))
	}

	fun text(patterns: List<String>, command: Command) {
		for (pattern in patterns)
			add(CommandMatcherSimple(pattern, command))
	}

	fun regex(vararg patterns: String, command: (message: Message, params: List<String>) -> Unit) {
		for (pattern in patterns)
			add(CommandMatcherRegex(Regex(pattern), command))
	}

	fun regex(patterns: List<String>, command: (message: Message, params: List<String>) -> Unit) {
		for (pattern in patterns)
			add(CommandMatcherRegex(Regex(pattern), command))
	}

	fun regex(pattern: String, command: CommandMatcherRegex.CommandRegex) {
		add(CommandMatcherRegex(Regex(pattern), command))
	}

	fun regex(patterns: List<String>, command: CommandMatcherRegex.CommandRegex) {
		for (pattern in patterns)
			add(CommandMatcherRegex(Regex(pattern), command))
	}

	fun commands(initializer: TgCommandPackHandler<M>.() -> Unit) {
		apply(initializer)
	}
}