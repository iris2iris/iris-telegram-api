package iris.tg.processors.single

import iris.tg.api.items.Message

interface TgMessageSingleFilter<M: Message> {
	fun text(message: M): M?
	fun newChatMember(message: M): M?
	fun leftChatMember(message: M): M?
	fun newChatTitle(message: M): M?
	fun newChatPhoto(message: M): M?
	fun deleteChatPhoto(message: M): M?
	fun groupChatCreated(message: M): M?
	fun supergroupChatCreated(message: M): M?
	fun channelChatCreated(message: M): M?
	fun messageAutoDeleteTimerChanged(message: M): M?
	fun migrateToChatId(message: M): M?
	fun migrateFromChatId(message: M): M?
	fun pinnedMessage(message: M): M?
	fun invoice(message: M): M?
	fun successfulPayment(message: M): M?
	fun connectedWebsite(message: M): M?
	fun passportData(message: M): M?
	fun proximityAlertTriggered(message: M): M?
	fun voiceChatScheduled(message: M): M?
	fun voiceChatStarted(message: M): M?
	fun voiceChatEnded(message: M): M?
	fun voiceChatParticipantsInvited(message: M): M?
	fun unresolvedMessage(message: M): M?
}

