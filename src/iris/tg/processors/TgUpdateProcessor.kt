package iris.tg.processors

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgUpdateProcessor<T> {
	fun processUpdates(updates: List<T>)
	fun processUpdate(update: T)
}