package iris.tg.trigger

import iris.tg.api.items.CallbackQuery
import iris.tg.api.items.Message
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapter
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventTriggerSingleHandler() : TgEventMessageSingleHandlerAdapterBasicTypes() {

	constructor(initializer: TgEventTriggerSingleHandler.() -> Unit) : this() {
		apply(initializer)
	}

	interface TriggerMessage { fun process(message: Message) }
	interface TriggerEditMessage { fun process(message: Message) }
	interface TriggerInvite { fun process(message: Message) }
	interface TriggerLeave { fun process(message: Message) }
	interface TriggerTitleUpdate { fun process(message: Message) }
	interface TriggerPinUpdate { fun process(message: Message) }
	interface TriggerCallback { fun process(message: CallbackQuery) }

	class TriggerMessageLambda(private val processor: (message: Message) -> Unit) : TriggerMessage {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerMessageEditLambda(private val processor: (message: Message) -> Unit) : TriggerEditMessage {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerInviteLambda(private val processor: (message: Message) -> Unit) : TriggerInvite {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerLeaveLambda(private val processor: (message: Message) -> Unit) : TriggerLeave {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerTitleUpdateLambda(private val processor: (message: Message) -> Unit) : TriggerTitleUpdate {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerPinUpdateLambda(private val processor: (message: Message) -> Unit) : TriggerPinUpdate {
		override fun process(message: Message) {
			processor(message)
		}
	}

	class TriggerCallbackLambda(private val processor: (message: CallbackQuery) -> Unit) : TriggerCallback {
		override fun process(message: CallbackQuery) {
			processor(message)
		}
	}

	private val messages: MutableList<TriggerMessage> = mutableListOf()
	private var editMessages: MutableList<TriggerEditMessage> = mutableListOf()
	private var invites: MutableList<TriggerInvite> = mutableListOf()
	private var leaves: MutableList<TriggerLeave> = mutableListOf()
	private var titles: MutableList<TriggerTitleUpdate> = mutableListOf()
	private var pins: MutableList<TriggerPinUpdate> = mutableListOf()
	private var callbacks: MutableList<TriggerCallback> = mutableListOf()

	operator fun plusAssign(trigger: TriggerMessage) {
		messages += trigger
	}

	operator fun plusAssign(trigger: TriggerEditMessage) {
		editMessages += trigger
	}

	operator fun plusAssign(trigger: TriggerInvite) {
		invites += trigger
	}

	operator fun plusAssign(trigger: TriggerLeave) {
		leaves += trigger
	}

	operator fun plusAssign(trigger: TriggerTitleUpdate) {
		titles += trigger
	}

	operator fun plusAssign(trigger: TriggerPinUpdate) {
		pins += trigger
	}

	operator fun plusAssign(trigger: TriggerCallback) {
		callbacks += trigger
	}

	fun onMessage(processor: (message: Message) -> Unit) = plusAssign(TriggerMessageLambda(processor))
	fun onMessage(trigger: TriggerMessage) = plusAssign(trigger)

	fun onMessageEdit(processor: (message: Message) -> Unit) = plusAssign(TriggerMessageEditLambda(processor))

	fun onInvite(processor: (message: Message) -> Unit) = plusAssign(TriggerInviteLambda(processor))
	fun onLeave(processor: (message: Message) -> Unit) = plusAssign(TriggerLeaveLambda(processor))
	fun onTitleUpdate(processor: (message: Message) -> Unit) = plusAssign(TriggerTitleUpdateLambda(processor))
	fun onPinUpdate(processor: (message: Message) -> Unit) = plusAssign(TriggerPinUpdateLambda(processor))
	fun onCallback(processor: (messages: CallbackQuery) -> Unit) = plusAssign(TriggerCallbackLambda(processor))

	override fun text(message: Message) {
		this.messages.forEach { it.process(message) }
	}

	override fun editedMessage(message: Message) {
		this.editMessages.forEach { it.process(message) }
	}

	override fun callbackQuery(item: CallbackQuery) {
		callbacks.forEach { it.process(item) }
	}

	override fun newChatTitle(message: Message) {
		titles.forEach { it.process(message) }
	}
}