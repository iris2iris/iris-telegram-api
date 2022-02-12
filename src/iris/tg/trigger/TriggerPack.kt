package iris.tg.trigger

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TriggerPack<T> {
	fun process(events: List<T>)
}