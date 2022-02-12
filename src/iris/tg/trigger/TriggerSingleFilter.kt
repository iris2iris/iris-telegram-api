package iris.tg.trigger

interface TriggerSingleFilter<T> {
	fun process(event: T): Boolean
}