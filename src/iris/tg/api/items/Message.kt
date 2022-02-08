package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Message : TgItem {
	val messageId: Int
	val from: User?
	val senderChat: Chat?
	val date: Long
	val chat: Chat
	val forwardFrom: User?
	val forwardFromChat: Chat?
	val forwardFromMessageId: Int
	val forwardSignature: String?
	val forwardSenderName: String?
	val forwardDate: Int
	val isAutomaticForward: Boolean
	val replyToMessage: Message?
	val viaBot: User?
	val editDate: Int
	val hasProtectedContent: Boolean
	val mediaGroupId: String?
	val authorSignature: String?
	val text: String?
	val entities: List<MessageEntity>?
	val animation: Animation?
	val audio: Audio?
	val document: Document?
	val photo: List<PhotoSize>?
	val sticker: Sticker?
	val video: Video?
	val videoNote: VideoNote?
	val voice: Voice?
	val caption:  String?
	val captionEntities: List<MessageEntity>?
	val contact: Contact?
	val dice: Dice?
	val game: Game?
	val poll: Poll?
	val venue: Venue?
	val location: Location?
	val newChatMembers:	List<User>?
	val leftChatMember:	User?
	val newChatTitle: String?
	val newChatPhoto: List<PhotoSize>?
	val deleteChatPhoto: Boolean
	val groupChatCreated: Boolean
	val supergroupChatCreated: Boolean
	val channelChatCreated: Boolean
	val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged?
	val migrateToChatId: Long
	val migrateFromChatId: Long
	val pinnedMessage: Message?
	val invoice: Invoice?
	val successfulPayment: SuccessfulPayment?
	val connectedWebsite: String?
	val passportData: PassportData?
	val proximityAlertTriggered: ProximityAlertTriggered?
	val voiceChatScheduled: VoiceChatScheduled?
	val voiceChatStarted: VoiceChatStarted?
	val voiceChatEnded: VoiceChatEnded?
	val voiceChatParticipantsInvited: VoiceChatParticipantsInvited?
	val replyMarkup: InlineKeyboardMarkup?
}