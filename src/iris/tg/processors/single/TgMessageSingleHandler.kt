package iris.tg.processors.single

import iris.tg.api.items.Message

interface TgMessageSingleHandler<M: Message> {
	fun text(message: M)
	fun newChatMember(message: M)
	fun leftChatMember(message: M)
	fun newChatTitle(message: M)
	fun newChatPhoto(message: M)
	fun deleteChatPhoto(message: M)
	fun groupChatCreated(message: M)
	fun supergroupChatCreated(message: M)
	fun channelChatCreated(message: M)
	fun messageAutoDeleteTimerChanged(message: M)
	fun migrateToChatId(message: M)
	fun migrateFromChatId(message: M)
	fun pinnedMessage(message: M)
	fun invoice(message: M)
	fun successfulPayment(message: M)
	fun connectedWebsite(message: M)
	fun passportData(message: M)
	fun proximityAlertTriggered(message: M)
	fun voiceChatScheduled(message: M)
	fun voiceChatStarted(message: M)
	fun voiceChatEnded(message: M)
	fun voiceChatParticipantsInvited(message: M)
	fun unresolvedMessage(message: M)
}