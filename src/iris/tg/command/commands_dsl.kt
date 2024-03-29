package iris.tg.command

import iris.tg.event.Message

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun commands(initializer: DslCommandBuilder.() -> Unit): List<Pair<CommandMatcher, CharArray?>> {
	return DslCommandBuilder().apply(initializer).build()
}

open class DslCommandBuilder {

	private val commands = mutableListOf<Pair<CommandMatcher, CharArray?>>()

	infix fun String.runs(command: (message: Message) -> Unit) {
		commands += CommandMatcherSimple(this, command).let { it to it.hashChars() }
	}

	infix fun String.runs(command: Command) {
		commands += CommandMatcherSimple(this, command).let { it to it.hashChars() }
	}

	infix fun RegexBuilder.runs(command: (message: Message, params: List<String>) -> Unit) {
		commands += CommandMatcherRegex(Regex(this.pattern), command) to this.hashChars
	}

	infix fun RegexBuilder.runs(command: CommandMatcherRegex.CommandRegex) {
		commands += CommandMatcherRegex(Regex(this.pattern), command) to this.hashChars
	}

	fun regex(template: String, hashChars: String? = null)  = regex(template, hashChars?.toCharArray())

	fun regex(template: String, hashChars: CharArray?): RegexBuilder {
		return RegexBuilder(template, hashChars)
	}

	class RegexBuilder(val pattern: String, val hashChars: CharArray?)

	fun build(): List<Pair<CommandMatcher, CharArray?>> {
		return commands
	}
}