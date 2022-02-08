package iris.tg.processors.pack

import iris.tg.api.items.Message
import java.util.*

class TgEventPackSplitMessageHandler<M: Message>(private val handler: TgMessagePackHandler<M>) {

	private val messages = LinkedList<M>()
	private val newChatMembers = LinkedList<M>()
	private val leftChatMember = LinkedList<M>()
	private val newChatTitle = LinkedList<M>()
	private val newChatPhoto = LinkedList<M>()
	private val deleteChatPhoto = LinkedList<M>()
	private val groupChatCreated = LinkedList<M>()
	private val supergroupChatCreated = LinkedList<M>()
	private val channelChatCreated = LinkedList<M>()
	private val messageAutoDeleteTimerChanged = LinkedList<M>()
	private val migrateToChatId = LinkedList<M>()
	private val migrateFromChatId = LinkedList<M>()
	private val pinnedMessage = LinkedList<M>()
	private val invoice = LinkedList<M>()
	private val successfulPayment = LinkedList<M>()
	private val connectedWebsite = LinkedList<M>()
	private val passportData = LinkedList<M>()
	private val proximityAlertTriggered = LinkedList<M>()
	private val voiceChatScheduled = LinkedList<M>()
	private val voiceChatStarted = LinkedList<M>()
	private val voiceChatEnded = LinkedList<M>()
	private val voiceChatParticipantsInvited = LinkedList<M>()

	fun messages(messages: List<M>) {

		for (m in messages) {
			try {
				m.text?.also { this.messages += m }
				?: m.sticker?.also { this.messages += m }
				?: m.photo?.also { this.messages += m }
				?: m.animation?.also { this.messages += m }
				?: m.video?.also { this.messages += m }
				?: m.videoNote?.also { this.messages += m }
				?: m.audio?.also { this.messages += m }
				?: m.caption?.also { this.messages += m }
				?: m.newChatMembers?.also { newChatMembers += m }
				?: m.leftChatMember?.also { leftChatMember += m }
				?: m.newChatTitle?.also { newChatTitle += m }
				?: m.newChatPhoto?.also { newChatPhoto += m }
				?: if (m.deleteChatPhoto) { deleteChatPhoto += m }
				else if (m.groupChatCreated) { groupChatCreated += m }
				else if (m.supergroupChatCreated) { supergroupChatCreated += m }
				else if (m.channelChatCreated) { channelChatCreated += m }
				else m.messageAutoDeleteTimerChanged?.apply { messageAutoDeleteTimerChanged += m }
				?: if (m.migrateToChatId != 0L) migrateToChatId += m
				else if (m.migrateFromChatId != 0L) migrateFromChatId += m
				else m.pinnedMessage?.also { pinnedMessage += m }
				?: m.invoice?.also { invoice += m }
				?: m.successfulPayment?.also { successfulPayment += m }
				?: m.connectedWebsite?.also { connectedWebsite += m }
				?: m.passportData?.also { passportData += m }
				?: m.proximityAlertTriggered?.also { proximityAlertTriggered += m }
				?: m.voiceChatScheduled?.also { voiceChatScheduled += m }
				?: m.voiceChatStarted?.also { voiceChatStarted += m }
				?: m.voiceChatEnded?.also { voiceChatEnded += m }
				?: m.voiceChatParticipantsInvited?.also { voiceChatParticipantsInvited += m }
				?: run { this.messages += m }
			} catch (e: Throwable) {
				System.err.println(e.message!!)
				println(m)
			}
		}

		if (this.messages.isNotEmpty()) {
			handler.texts(this.messages)
			this.messages.clear()
		}

		if (newChatMembers.isNotEmpty()) {
			handler.newChatMembers(newChatMembers)
			newChatMembers.clear()
		}

		if (leftChatMember.isNotEmpty()) {
			handler.leftChatMember(leftChatMember)
			leftChatMember.clear()
		}

		if (newChatTitle.isNotEmpty()) {
			handler.newChatTitle(newChatTitle)
			newChatTitle.clear()
		}

		if (newChatPhoto.isNotEmpty()) {
			handler.newChatPhoto(newChatPhoto)
			newChatPhoto.clear()
		}

		if (deleteChatPhoto.isNotEmpty()) {
			handler.deleteChatPhoto(deleteChatPhoto)
			deleteChatPhoto.clear()
		}

		if (groupChatCreated.isNotEmpty()) {
			handler.groupChatCreated(groupChatCreated)
			groupChatCreated.clear()
		}

		if (supergroupChatCreated.isNotEmpty()) {
			handler.supergroupChatCreated(supergroupChatCreated)
			supergroupChatCreated.clear()
		}

		if (channelChatCreated.isNotEmpty()) {
			handler.channelChatCreated(channelChatCreated)
			channelChatCreated.clear()
		}

		if (messageAutoDeleteTimerChanged.isNotEmpty()) {
			handler.messageAutoDeleteTimerChanged(messageAutoDeleteTimerChanged)
			messageAutoDeleteTimerChanged.clear()
		}

		if (migrateToChatId.isNotEmpty()) {
			handler.migrateToChatId(migrateToChatId)
			migrateToChatId.clear()
		}

		if (migrateFromChatId.isNotEmpty()) {
			handler.migrateFromChatId(migrateFromChatId)
			migrateFromChatId.clear()
		}

		if (pinnedMessage.isNotEmpty()) {
			handler.pinnedMessage(pinnedMessage)
			pinnedMessage.clear()
		}


		if (invoice.isNotEmpty()) {
			handler.invoice(invoice)
			invoice.clear()
		}

		if (successfulPayment.isNotEmpty()) {
			handler.successfulPayment(successfulPayment)
			successfulPayment.clear()
		}

		if (connectedWebsite.isNotEmpty()) {
			handler.connectedWebsite(connectedWebsite)
			connectedWebsite.clear()
		}

		if (passportData.isNotEmpty()) {
			handler.passportData(passportData)
			passportData.clear()
		}

		if (proximityAlertTriggered.isNotEmpty()) {
			handler.proximityAlertTriggered(proximityAlertTriggered)
			proximityAlertTriggered.clear()
		}

		if (voiceChatScheduled.isNotEmpty()) {
			handler.voiceChatScheduled(voiceChatScheduled)
			voiceChatScheduled.clear()
		}

		if (voiceChatStarted.isNotEmpty()) {
			handler.voiceChatStarted(voiceChatStarted)
			voiceChatStarted.clear()
		}

		if (voiceChatEnded.isNotEmpty()) {
			handler.voiceChatEnded(voiceChatEnded)
			voiceChatEnded.clear()
		}

		if (voiceChatParticipantsInvited.isNotEmpty()) {
			handler.voiceChatParticipantsInvited(voiceChatParticipantsInvited)
			voiceChatParticipantsInvited.clear()
		}
	}
}