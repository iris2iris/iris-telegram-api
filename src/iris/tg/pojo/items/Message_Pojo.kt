package iris.tg.pojo.items

import iris.tg.api.items.*

/**
 * @created 09.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class Message_Pojo(
	override val messageId: Int,
	override val chat: Chat,
	override val from: User? = null,
	override val senderChat: Chat? = null,
	override val date: Long = 0,
	override val forwardFrom: User? = null,
	override val forwardFromChat: Chat? = null,
	override val forwardFromMessageId: Int = 0,
	override val forwardSignature: String? = null,
	override val forwardSenderName: String? = null,
	override val forwardDate: Int = 0,
	override val isAutomaticForward: Boolean = false,
	override val replyToMessage: Message? = null,
	override val viaBot: User? = null,
	override val editDate: Int = 0,
	override val hasProtectedContent: Boolean = false,
	override val mediaGroupId: String? = null,
	override val authorSignature: String? = null,
	override val text: String? = null,
	override val entities: List<MessageEntity>? = null,
	override val animation: Animation? = null,
	override val audio: Audio? = null,
	override val document: Document? = null,
	override val photo: List<PhotoSize>? = null,
	override val sticker: Sticker? = null,
	override val video: Video? = null,
	override val videoNote: VideoNote? = null,
	override val voice: Voice? = null,
	override val caption: String? = null,
	override val captionEntities: List<MessageEntity>? = null,
	override val contact: Contact? = null,
	override val dice: Dice? = null,
	override val game: Game? = null,
	override val poll: Poll? = null,
	override val venue: Venue? = null,
	override val location: Location? = null,
	override val newChatMembers: List<User>? = null,
	override val leftChatMember: User? = null,
	override val newChatTitle: String? = null,
	override val newChatPhoto: List<PhotoSize>? = null,
	override val deleteChatPhoto: Boolean = false,
	override val groupChatCreated: Boolean = false,
	override val supergroupChatCreated: Boolean = false,
	override val channelChatCreated: Boolean = false,
	override val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged? = null,
	override val migrateToChatId: Long = 0L,
	override val migrateFromChatId: Long = 0L,
	override val pinnedMessage: Message? = null,
	override val invoice: Invoice? = null,
	override val successfulPayment: SuccessfulPayment? = null,
	override val connectedWebsite: String? = null,
	override val passportData: PassportData? = null,
	override val proximityAlertTriggered: ProximityAlertTriggered? = null,
	override val voiceChatScheduled: VoiceChatScheduled? = null,
	override val voiceChatStarted: VoiceChatStarted? = null,
	override val voiceChatEnded: VoiceChatEnded? = null,
	override val voiceChatParticipantsInvited: VoiceChatParticipantsInvited? = null,
	override val replyMarkup: InlineKeyboardMarkup? = null,
): Message {



	override fun toString(): String {
		return "{msg=${chat.id}:$messageId}"
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as Message_Pojo

		if (messageId != other.messageId) return false
		if (chat.id != other.chat.id) return false

		return true
	}

	override fun hashCode(): Int {
		return messageId*31 + chat.id.hashCode()
	}
}