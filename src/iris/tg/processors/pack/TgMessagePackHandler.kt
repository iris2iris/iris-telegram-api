package iris.tg.processors.pack

import iris.tg.api.items.Message

interface TgMessagePackHandler<M: Message>: TgTextPackHandler<M> {
	fun newChatMembers(messages: List<M>)
	fun leftChatMember(messages: List<M>)
	fun newChatTitle(messages: List<M>)
	fun newChatPhoto(messages: List<M>)
	fun deleteChatPhoto(messages: List<M>)
	fun groupChatCreated(messages: List<M>)
	fun supergroupChatCreated(messages: List<M>)
	fun channelChatCreated(messages: List<M>)
	fun messageAutoDeleteTimerChanged(messages: List<M>)
	fun migrateToChatId(messages: List<M>)
	fun migrateFromChatId(messages: List<M>)
	fun pinnedMessage(messages: List<M>)
	fun invoice(messages: List<M>)
	fun successfulPayment(messages: List<M>)
	fun connectedWebsite(messages: List<M>)
	fun passportData(messages: List<M>)
	fun proximityAlertTriggered(messages: List<M>)
	fun voiceChatScheduled(messages: List<M>)
	fun voiceChatStarted(messages: List<M>)
	fun voiceChatEnded(messages: List<M>)
	fun voiceChatParticipantsInvited(messages: List<M>)
	fun unresolvedMessages(messages: List<M>)
}