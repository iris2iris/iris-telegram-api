package iris.tg.processors.single

import iris.tg.api.items.*
import iris.tg.processors.TgUpdateProcessor

/**
 * @created 31.10.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class TgUpdateProcessorSingle(private val updateHandler: TgEventSingleHandlerBasicTypes) : TgUpdateProcessor<Update> {

	override fun processUpdates(updates: List<Update>) {
		updates.forEach(this::processUpdate)
	}

	override fun processUpdate(update: Update) {
		update.message?.also { updateHandler.message(it) }
		?: update.editedMessage?.also { updateHandler.editedMessage(it) }
		?: update.chatMember?.also { updateHandler.chatMember(it) }
		?: update.callbackQuery?.also { updateHandler.callbackQuery(it) }
		?: update.pollAnswer?.also { updateHandler.pollAnswer(it) }
		?: update.poll?.also { updateHandler.poll(it) }
		?: update.channelPost?.also { updateHandler.channelPost(it) }
		?: update.editedChannelPost?.also { updateHandler.editedChannelPost(it) }
		?: update.inlineQuery?.also { updateHandler.inlineQuery(it) }
		?: update.chosenInlineResult?.also { updateHandler.chosenInlineResult(it) }
		?: update.shippingQuery?.also { updateHandler.shippingQuery(it) }
		?: update.preCheckoutQuery?.also { updateHandler.preCheckoutQuery(it) }
		?: update.myChatMember?.also { updateHandler.myChatMember(it) }
		?: update.chatJoinRequest?.also { updateHandler.chatJoinRequest(it) }
	}
}