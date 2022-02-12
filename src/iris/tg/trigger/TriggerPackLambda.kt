package iris.tg.trigger

class TriggerPackLambda<T>(private val action: (List<T>) -> Unit) : TriggerPack<T> {
	override fun process(events: List<T>) {
		action(events)
	}
}