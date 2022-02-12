package iris.tg.trigger

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TriggerPack2Lambda<M>(private val lambda: (M) -> Unit) : TriggerPack<M> {
	override fun process(events: List<M>) {
		events.forEach(lambda)
	}
}