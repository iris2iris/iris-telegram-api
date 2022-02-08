@file:Suppress("NAME_SHADOWING")

package iris.tg.processors.single

import iris.tg.api.items.*


/**
 * @created 20.09.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class TgEventMessageSingleFilterHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>(private val filters: Array<TgEventMessageSingleFilter<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>>, private val handler: TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) :
	TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	private val splitter by lazy(LazyThreadSafetyMode.NONE) { TgEventSplitMessageHandler(this) }

	override fun message(message: M) {
		splitter.message(message)
	}

	override fun editedMessage(message: M) {
		var message = message
		for (i in filters) {
			message = i.editedMessage(message) ?: return 
		}
		handler.editedMessage(message)
	}

	override fun channelPost(message: M) {
		var message = message
		for (i in filters) {
			message = i.channelPost(message) ?: return
		}
		handler.channelPost(message)
	}

	override fun editedChannelPost(message: M) {
		var message = message
		for (i in filters) {
			message = i.editedChannelPost(message) ?: return
		}
		handler.editedChannelPost(message)
	}

	override fun inlineQuery(item: IQ) {
		var item = item
		for (i in filters) {
			item = i.inlineQuery(item) ?: return
		}
		handler.inlineQuery(item)
	}

	override fun chosenInlineResult(item: CIR) {
		var item = item
		for (i in filters) {
			item = i.chosenInlineResult(item) ?: return
		}
		handler.chosenInlineResult(item)
	}

	override fun callbackQuery(item: CQ) {
		var item = item
		for (i in filters) {
			item = i.callbackQuery(item) ?: return
		}
		handler.callbackQuery(item)
	}

	override fun shippingQuery(item: SQ) {
		var item = item
		for (i in filters) {
			item = i.shippingQuery(item) ?: return
		}
		handler.shippingQuery(item)
	}

	override fun preCheckoutQuery(item: PCQ) {
		var item = item
		for (i in filters) {
			item = i.preCheckoutQuery(item) ?: return
		}
		handler.preCheckoutQuery(item)
	}

	override fun poll(item: P) {
		var item = item
		for (i in filters) {
			item = i.poll(item) ?: return
		}
		handler.poll(item)
	}

	override fun pollAnswer(item: PA) {
		var item = item
		for (i in filters) {
			item = i.pollAnswer(item) ?: return
		}
		handler.pollAnswer(item)
	}

	override fun myChatMember(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.myChatMember(item) ?: return
		}
		handler.myChatMember(item)
	}

	override fun chatMember(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.chatMember(item) ?: return
		}
		handler.chatMember(item)
	}

	override fun chatJoinRequest(item: CJR) {
		var item = item
		for (i in filters) {
			item = i.chatJoinRequest(item) ?: return
		}
		handler.chatJoinRequest(item)
	}

	//////////////

	override fun text(message: M) {
		var message = message
		for (i in filters) {
			message = i.text(message) ?: return
		}
		handler.text(message)
	}

	override fun newChatMember(message: M) {
		var message = message
		for (i in filters) {
			message = i.newChatMember(message) ?: return
		}
		handler.newChatMember(message)
	}

	override fun leftChatMember(message: M) {
		var message = message
		for (i in filters) {
			message = i.leftChatMember(message) ?: return
		}
		handler.leftChatMember(message)
	}

	override fun newChatTitle(message: M) {
		var message = message
		for (i in filters) {
			message = i.newChatTitle(message) ?: return
		}
		handler.newChatTitle(message)
	}

	override fun newChatPhoto(message: M) {
		var message = message
		for (i in filters) {
			message = i.newChatPhoto(message) ?: return
		}
		handler.newChatPhoto(message)
	}

	override fun deleteChatPhoto(message: M) {
		var message = message
		for (i in filters) {
			message = i.deleteChatPhoto(message) ?: return
		}
		handler.deleteChatPhoto(message)
	}

	override fun groupChatCreated(message: M) {
		var message = message
		for (i in filters) {
			message = i.groupChatCreated(message) ?: return
		}
		handler.groupChatCreated(message)
	}

	override fun supergroupChatCreated(message: M) {
		var message = message
		for (i in filters) {
			message = i.supergroupChatCreated(message) ?: return
		}
		handler.supergroupChatCreated(message)
	}

	override fun channelChatCreated(message: M) {
		var message = message
		for (i in filters) {
			message = i.channelChatCreated(message) ?: return
		}
		handler.channelChatCreated(message)
	}

	override fun messageAutoDeleteTimerChanged(message: M) {
		var message = message
		for (i in filters) {
			message = i.messageAutoDeleteTimerChanged(message) ?: return
		}
		handler.messageAutoDeleteTimerChanged(message)
	}

	override fun migrateToChatId(message: M) {
		var message = message
		for (i in filters) {
			message = i.migrateToChatId(message) ?: return
		}
		handler.migrateToChatId(message)
	}

	override fun migrateFromChatId(message: M) {
		var message = message
		for (i in filters) {
			message = i.migrateFromChatId(message) ?: return
		}
		handler.migrateFromChatId(message)
	}

	override fun pinnedMessage(message: M) {
		var message = message
		for (i in filters) {
			message = i.pinnedMessage(message) ?: return
		}
		handler.pinnedMessage(message)
	}

	override fun invoice(message: M) {
		var message = message
		for (i in filters) {
			message = i.invoice(message) ?: return
		}
		handler.invoice(message)
	}

	override fun successfulPayment(message: M) {
		var message = message
		for (i in filters) {
			message = i.successfulPayment(message) ?: return
		}
		handler.successfulPayment(message)
	}

	override fun connectedWebsite(message: M) {
		var message = message
		for (i in filters) {
			message = i.connectedWebsite(message) ?: return
		}
		handler.connectedWebsite(message)
	}

	override fun passportData(message: M) {
		var message = message
		for (i in filters) {
			message = i.passportData(message) ?: return
		}
		handler.passportData(message)
	}

	override fun proximityAlertTriggered(message: M) {
		var message = message
		for (i in filters) {
			message = i.proximityAlertTriggered(message) ?: return
		}
		handler.proximityAlertTriggered(message)
	}

	override fun voiceChatScheduled(message: M) {
		var message = message
		for (i in filters) {
			message = i.voiceChatScheduled(message) ?: return
		}
		handler.voiceChatScheduled(message)
	}

	override fun voiceChatStarted(message: M) {
		var message = message
		for (i in filters) {
			message = i.voiceChatStarted(message) ?: return
		}
		handler.voiceChatStarted(message)
	}

	override fun voiceChatEnded(message: M) {
		var message = message
		for (i in filters) {
			message = i.voiceChatEnded(message) ?: return
		}
		handler.voiceChatEnded(message)
	}

	override fun voiceChatParticipantsInvited(message: M) {
		var message = message
		for (i in filters) {
			message = i.voiceChatParticipantsInvited(message) ?: return
		}
		handler.voiceChatParticipantsInvited(message)
	}

	override fun unresolvedMessage(message: M) {
		var message = message
		for (i in filters) {
			message = i.unresolvedMessage(message) ?: return
		}
		handler.unresolvedMessage(message)
	}

	/////////////

	override fun ownerChanged(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.ownerChanged(item) ?: return
		}
		handler.ownerChanged(item)
	}

	override fun administratorChanged(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.administratorChanged(item) ?: return
		}
		handler.administratorChanged(item)
	}

	override fun administratorCanceled(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.administratorCanceled(item) ?: return
		}
		handler.administratorCanceled(item)
	}

	override fun chatMemberRestricted(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.chatMemberRestricted(item) ?: return
		}
		handler.chatMemberRestricted(item)
	}

	override fun chatMemberLeft(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.chatMemberLeft(item) ?: return
		}
		handler.chatMemberLeft(item)
	}

	override fun chatMemberBanned(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.chatMemberBanned(item) ?: return
		}
		handler.chatMemberBanned(item)
	}

	override fun chatMemberNew(item: CMU) {
		var item = item
		for (i in filters) {
			item = i.chatMemberNew(item) ?: return
		}
		handler.chatMemberNew(item)
	}
}