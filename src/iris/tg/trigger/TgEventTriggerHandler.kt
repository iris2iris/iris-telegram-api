package iris.tg.trigger

import iris.tg.api.items.CallbackQuery
import iris.tg.api.items.Message
import iris.tg.processors.single.TgEventMessageSingleHandlerAdapterBasicTypes

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventTriggerHandler<M: Message>() : TgEventMessageSingleHandlerAdapterBasicTypes() {

	constructor(initializer: TgEventTriggerHandler<M>.() -> Unit) : this() {
		apply(initializer)
	}

	interface TriggerMessage<M: Message> { fun process(message: M) }
	interface TriggerEditMessage<M: Message>: TriggerMessage<M>
	interface TriggerInvite<M: Message>: TriggerMessage<M>
	interface TriggerLeave<M: Message>: TriggerMessage<M>
	interface TriggerTitleUpdate<M: Message>: TriggerMessage<M>
	interface TriggerPinUpdate<M: Message>: TriggerMessage<M>
	interface TriggerCallback { fun process(item: CallbackQuery) }

	class TriggerMessageLambda<M: Message>(private val processor: (message: M) -> Unit) : TriggerMessage<M> {

		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerMessageEditLambda<M: Message>(private val processor: (message: M) -> Unit) : TriggerEditMessage<M> {
		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerInviteLambda<M: Message>(private val processor: (message: M) -> Unit) : TriggerInvite<M> {
		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerLeaveLambda<M: Message>(private val processor: (message: M) -> Unit) : TriggerLeave<M> {
		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerTitleUpdateLambda<M: Message>(private val processor: (message: M) -> Unit) : TriggerTitleUpdate<M> {
		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerPinUpdateLambda<M : Message>(private val processor: (message: M) -> Unit) : TriggerPinUpdate<M> {
		override fun process(message: M) {
			processor(message)
		}
	}

	class TriggerCallbackLambda(private val processor: (message: CallbackQuery) -> Unit) : TriggerCallback {

		override fun process(item: CallbackQuery) {
			processor(item)
		}
	}

	private val messages: MutableList<TriggerMessage<M>> = mutableListOf()
	private var editMessages: MutableList<TriggerEditMessage<M>> = mutableListOf()
	private var invites: MutableList<TriggerInvite<M>> = mutableListOf()
	private var leaves: MutableList<TriggerLeave<M>> = mutableListOf()
	private var titles: MutableList<TriggerTitleUpdate<M>> = mutableListOf()
	private var pins: MutableList<TriggerPinUpdate<M>> = mutableListOf()
	private var callbacks: MutableList<TriggerCallback> = mutableListOf()

	operator fun plusAssign(trigger: TriggerMessage<M>) {
		messages += trigger
	}

	operator fun plusAssign(trigger: TriggerEditMessage<M>) {
		editMessages += trigger
	}

	operator fun plusAssign(trigger: TriggerInvite<M>) {
		invites += trigger
	}

	operator fun plusAssign(trigger: TriggerLeave<M>) {
		leaves += trigger
	}

	operator fun plusAssign(trigger: TriggerTitleUpdate<M>) {
		titles += trigger
	}

	operator fun plusAssign(trigger: TriggerPinUpdate<M>) {
		pins += trigger
	}

	operator fun plusAssign(trigger: TriggerCallback) {
		callbacks += trigger
	}

	fun onMessage(processor: (message: M) -> Unit) = plusAssign(TriggerMessageLambda<M>(processor))
	fun onMessage(trigger: TriggerMessage<M>) = plusAssign(trigger)

	fun onMessageEdit(processor: (message: M) -> Unit) = plusAssign(TriggerMessageEditLambda(processor))

	fun onInvite(processor: (message: M) -> Unit) = plusAssign(TriggerInviteLambda(processor))
	fun onLeave(processor: (message: M) -> Unit) = plusAssign(TriggerLeaveLambda(processor))
	fun onTitleUpdate(processor: (message: M) -> Unit) = plusAssign(TriggerTitleUpdateLambda(processor))
	fun onPinUpdate(processor: (message: M) -> Unit) = plusAssign(TriggerPinUpdateLambda(processor))
	fun onCallback(processor: (item: CallbackQuery) -> Unit) = plusAssign(TriggerCallbackLambda(processor))

	override fun text(message: Message) {
		val message = message as M
		(this.messages).forEach { it.process(message) }
	}

	override fun editedMessage(message: Message) {
		val message = message as M
		(this.editMessages).forEach { it.process(message) }
	}

	override fun callbackQuery(item: CallbackQuery) {
		callbacks.forEach { it.process(item) }
	}

}