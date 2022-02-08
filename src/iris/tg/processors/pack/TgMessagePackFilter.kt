package iris.tg.processors.pack

import iris.tg.api.items.Message

interface TgMessagePackFilter<M: Message> {
	fun texts(messages: List<M>): List<M>
	fun newChatMembers(messages: List<M>): List<M>
	fun leftChatMember(messages: List<M>): List<M>
	fun newChatTitle(messages: List<M>): List<M>
	fun newChatPhoto(messages: List<M>): List<M>
	fun deleteChatPhoto(messages: List<M>): List<M>
	fun groupChatCreated(messages: List<M>): List<M>
	fun supergroupChatCreated(messages: List<M>): List<M>
	fun channelChatCreated(messages: List<M>): List<M>
	fun messageAutoDeleteTimerChanged(messages: List<M>): List<M>
	fun migrateToChatId(messages: List<M>): List<M>
	fun migrateFromChatId(messages: List<M>): List<M>
	fun pinnedMessage(messages: List<M>): List<M>
	fun invoice(messages: List<M>): List<M>
	fun successfulPayment(messages: List<M>): List<M>
	fun connectedWebsite(messages: List<M>): List<M>
	fun passportData(messages: List<M>): List<M>
	fun proximityAlertTriggered(messages: List<M>): List<M>
	fun voiceChatScheduled(messages: List<M>): List<M>
	fun voiceChatStarted(messages: List<M>): List<M>
	fun voiceChatEnded(messages: List<M>): List<M>
	fun voiceChatParticipantsInvited(messages: List<M>): List<M>
	fun unresolvedMessages(messages: List<M>): List<M>
}

