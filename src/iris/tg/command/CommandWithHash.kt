package iris.tg.command

/**
 * @created 27.10.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface CommandWithHash : Command {
	fun hashChars(): CharArray?
}