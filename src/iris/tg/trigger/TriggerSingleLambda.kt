package iris.tg.trigger

class TriggerSingleLambda<T>(private val action: (T) -> Unit) : TriggerSingle<T> {
	override fun process(m: T) {
		action(m)
	}
}