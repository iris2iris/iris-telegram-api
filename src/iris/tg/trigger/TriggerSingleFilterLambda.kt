package iris.tg.trigger

class TriggerSingleFilterLambda<T>(private val action: (T) -> Boolean) : TriggerSingleFilter<T> {

	override fun process(event: T): Boolean
		= action(event)
}