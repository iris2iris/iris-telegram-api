package iris.tg.trigger

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TriggerPackFilterSingleLambda<T>(private val action: (T) -> Boolean) : TriggerPackFilter<T> {
	override fun process(events: List<T>): List<T> {
		return events.filter(action)
	}
}