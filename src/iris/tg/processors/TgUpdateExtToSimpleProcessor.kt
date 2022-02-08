package iris.tg.processors

import iris.tg.api.items.Update
import iris.tg.api.items.UpdateExt

/**
 * @created 05.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Deprecated("Не нужны эти переливы")
class TgUpdateExtToSimpleProcessor(private val simpleProcessor: TgUpdateProcessor<Update>) : TgUpdateProcessor<UpdateExt> {

	override fun processUpdates(updates: List<UpdateExt>) {
		simpleProcessor.processUpdates(updates.map { it.update })
	}

	override fun processUpdate(update: UpdateExt) {
		simpleProcessor.processUpdate(update.update)
	}
}