package iris.tg.processors.single

import iris.tg.api.items.*

class TgEventSplitMessageHandler<M : Message>(private val handler: TgMessageSingleHandler<M>) {

	fun message(message: M) {
		try {
			message.text?.also { handler.text(message) }
			?: message.sticker?.also { handler.text(message) }
			?: message.photo?.also { handler.text(message) }
			?: message.animation?.also { handler.text(message) }
			?: message.video?.also { handler.text(message) }
			?: message.videoNote?.also { handler.text(message) }
			?: message.audio?.also { handler.text(message) }
			?: message.caption?.also { handler.text(message) }
			?: message.newChatMembers?.also { handler.newChatMember(message) }
			?: message.leftChatMember?.also { handler.leftChatMember(message) }
			?: message.newChatTitle?.also { handler.newChatTitle(message) }
			?: message.newChatPhoto?.also { handler.newChatPhoto(message) }
			?: if (message.deleteChatPhoto) { handler.deleteChatPhoto(message) }
			else if (message.groupChatCreated) { handler.groupChatCreated(message) }
			else if (message.supergroupChatCreated) { handler.supergroupChatCreated(message) }
			else if (message.channelChatCreated) { handler.channelChatCreated(message) }
			else message.messageAutoDeleteTimerChanged?.apply { handler.messageAutoDeleteTimerChanged(message) }
			?: if (message.migrateToChatId != 0L) { handler.migrateToChatId(message) }
			else if (message.migrateFromChatId != 0L) { handler.migrateFromChatId(message) }
			else message.pinnedMessage?.also { handler.pinnedMessage(message) }

			?: message.invoice?.also { handler.invoice(message) }
			?: message.successfulPayment?.also { handler.successfulPayment(message) }
			?: message.connectedWebsite?.also { handler.connectedWebsite(message) }
			?: message.passportData?.also { handler.passportData(message) }
			?: message.proximityAlertTriggered?.also { handler.proximityAlertTriggered(message) }
			?: message.voiceChatScheduled?.also { handler.voiceChatScheduled(message) }
			?: message.voiceChatStarted?.also { handler.voiceChatStarted(message) }
			?: message.voiceChatEnded?.also { handler.voiceChatEnded(message) }
			?: message.voiceChatParticipantsInvited?.also { handler.voiceChatParticipantsInvited(message) }
			?: run { handler.text(message) }
		} catch (e: Throwable) {
			System.err.println(e.message!!)
			println(message)
		}
	}
}