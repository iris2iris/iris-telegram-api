package iris.tg.trigger

class TriggerPackFilterLambda<T>(private val action: (List<T>) -> List<T>) : TriggerPackFilter<T> {
	override fun process(events: List<T>): List<T> {
		return action(events)
	}
}