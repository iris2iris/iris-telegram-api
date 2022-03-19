package iris.tg.processors.pack

import iris.tg.api.items.Message
import java.util.*

class TgEventPackSplitMessageHandler<M: Message>(private val handler: TgMessagePackHandler<M>) {

	fun messages(messages: List<M>) {

		var msgs: MutableList<M>? = null
		var newChatMembers: MutableList<M>? = null
		var leftChatMember: MutableList<M>? = null
		var newChatTitle: MutableList<M>? = null
		var newChatPhoto: MutableList<M>? = null
		var deleteChatPhoto: MutableList<M>? = null
		var groupChatCreated: MutableList<M>? = null
		var supergroupChatCreated: MutableList<M>? = null
		var channelChatCreated: MutableList<M>? = null
		var messageAutoDeleteTimerChanged: MutableList<M>? = null
		var migrateToChatId: MutableList<M>? = null
		var migrateFromChatId: MutableList<M>? = null
		var pinnedMessage: MutableList<M>? = null
		var invoice: MutableList<M>? = null
		var successfulPayment: MutableList<M>? = null
		var connectedWebsite: MutableList<M>? = null
		var passportData: MutableList<M>? = null
		var proximityAlertTriggered: MutableList<M>? = null
		var voiceChatScheduled: MutableList<M>? = null
		var voiceChatStarted: MutableList<M>? = null
		var voiceChatEnded: MutableList<M>? = null
		var voiceChatParticipantsInvited: MutableList<M>? = null
		
		for (m in messages) {
			try {
				m.text?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.sticker?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.photo?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.animation?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.video?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.videoNote?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.audio?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.caption?.also { (msgs ?: list().also { msgs = it }) += m }
				?: m.newChatMembers?.also { (newChatMembers ?: list().also { newChatMembers = it }) += m }
				?: m.leftChatMember?.also { (leftChatMember ?: list().also { leftChatMember = it }) += m }
				?: m.newChatTitle?.also { (newChatTitle ?: list().also { newChatTitle = it }) += m }
				?: m.newChatPhoto?.also { (newChatPhoto ?: list().also { newChatPhoto = it }) += m }
				?: if (m.deleteChatPhoto) { (deleteChatPhoto ?: list().also { deleteChatPhoto = it }) += m }
				else if (m.groupChatCreated) { (groupChatCreated ?: list().also { groupChatCreated = it }) += m }
				else if (m.supergroupChatCreated) { (supergroupChatCreated ?: list().also { supergroupChatCreated = it }) += m }
				else if (m.channelChatCreated) { (channelChatCreated ?: list().also { channelChatCreated = it }) += m }
				else m.messageAutoDeleteTimerChanged?.apply { (messageAutoDeleteTimerChanged ?: list().also { messageAutoDeleteTimerChanged = it }) += m }
				?: if (m.migrateToChatId != 0L) (migrateToChatId ?: list().also { migrateToChatId = it }) += m
				else if (m.migrateFromChatId != 0L) (migrateFromChatId ?: list().also { migrateFromChatId = it }) += m
				else m.pinnedMessage?.also { (pinnedMessage ?: list().also { pinnedMessage = it }) += m }
				?: m.invoice?.also { (invoice ?: list().also { invoice = it }) += m }
				?: m.successfulPayment?.also { (successfulPayment ?: list().also { successfulPayment = it }) += m }
				?: m.connectedWebsite?.also { (connectedWebsite ?: list().also { connectedWebsite = it }) += m }
				?: m.passportData?.also { (passportData ?: list().also { passportData = it }) += m }
				?: m.proximityAlertTriggered?.also { (proximityAlertTriggered ?: list().also { proximityAlertTriggered = it }) += m }
				?: m.voiceChatScheduled?.also { (voiceChatScheduled ?: list().also { voiceChatScheduled = it }) += m }
				?: m.voiceChatStarted?.also { (voiceChatStarted ?: list().also { voiceChatStarted = it }) += m }
				?: m.voiceChatEnded?.also { (voiceChatEnded ?: list().also { voiceChatEnded = it }) += m }
				?: m.voiceChatParticipantsInvited?.also { (voiceChatParticipantsInvited ?: list().also { voiceChatParticipantsInvited = it }) += m }
				?: run { (msgs ?: list().also { msgs = it }) += m }
			} catch (e: Throwable) {
				System.err.println(e.message!!)
				println(m)
			}
		}

		msgs?.run { handler.texts(this) }

		newChatMembers?.run { handler.newChatMembers(this) }

		leftChatMember?.run { handler.leftChatMember(this) }

		newChatTitle?.run { handler.newChatTitle(this) }

		newChatPhoto?.run { handler.newChatPhoto(this) }

		deleteChatPhoto?.run { handler.deleteChatPhoto(this) }

		groupChatCreated?.run { handler.groupChatCreated(this) }

		supergroupChatCreated?.run { handler.supergroupChatCreated(this) }

		channelChatCreated?.run { handler.channelChatCreated(this) }

		messageAutoDeleteTimerChanged?.run { handler.messageAutoDeleteTimerChanged(this) }

		migrateToChatId?.run { handler.migrateToChatId(this) }

		migrateFromChatId?.run { handler.migrateFromChatId(this) }

		pinnedMessage?.run { handler.pinnedMessage(this) }

		invoice?.run { handler.invoice(this) }

		successfulPayment?.run { handler.successfulPayment(this) }

		connectedWebsite?.run { handler.connectedWebsite(this) }

		passportData?.run { handler.passportData(this) }

		proximityAlertTriggered?.run { handler.proximityAlertTriggered(this) }

		voiceChatScheduled?.run { handler.voiceChatScheduled(this) }

		voiceChatStarted?.run { handler.voiceChatStarted(this) }

		voiceChatEnded?.run { handler.voiceChatEnded(this) }

		voiceChatParticipantsInvited?.run { handler.voiceChatParticipantsInvited(this) }
	}
	
	private fun list() = LinkedList<M>()
}