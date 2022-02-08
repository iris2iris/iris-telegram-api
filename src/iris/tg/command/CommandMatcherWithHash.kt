package iris.tg.command

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface CommandMatcherWithHash : CommandMatcher {
	fun hashChars(): CharArray?
}