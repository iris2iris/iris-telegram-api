package iris.tg.trigger

/**
 * @created 12.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TriggerPack2Single<M>(private val singleHandler: TriggerSingle<M>) : TriggerPack<M> {
	override fun process(events: List<M>) {
		events.forEach(singleHandler::process)
	}
}