package iris.tg.irisjson.items

import iris.json.JsonItem
import iris.tg.api.items.*

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class IrisJsonMessage(sourceItem: JsonItem) : IrisJsonTgItem(sourceItem), Message {

	override val messageId: Int by lazyItem { source["message_id"].asInt() }

	override val from: User? by lazyItemOrNull("from") { IrisJsonUser(it) }

	override val senderChat: Chat? by lazyItemOrNull("sender_chat") { IrisJsonChat(it) }

	override val date: Long get() = source["date"].asLongOrNull() ?: 0L

	override val chat: Chat by lazyItem() { IrisJsonChat(source["chat"]) }

	override val forwardFrom: User? by lazyItemOrNull("forward_from") { IrisJsonUser(it) }

	override val forwardFromChat: Chat? by lazyItemOrNull("forward_from_chat") { IrisJsonChat(it) }

	override val forwardFromMessageId: Int get() = source["forward_fromMessage_id"].asIntOrNull() ?: 0

	override val forwardSignature: String? get() = source["forward_signature"].asStringOrNull()

	override val forwardSenderName: String? get() = source["forward_sender_name"].asStringOrNull()

	override val forwardDate: Int get() = source["forward_date"].asIntOrNull() ?: 0

	override val isAutomaticForward: Boolean get() = source["is_automatic_forward"].asBooleanOrNull() ?: false

	override val replyToMessage: Message? by lazyItemOrNull("reply_to_message") { IrisJsonMessage(it) }

	override val viaBot: User? by lazyItemOrNull("via_bot") { IrisJsonUser(it) }

	override val editDate: Int get() = source["edit_date"].asIntOrNull() ?: 0

	override val hasProtectedContent: Boolean get() = source["has_protected_content"].asBooleanOrNull() ?: false

	override val mediaGroupId: String?
		get() = source["media_group_id"].asStringOrNull()

	override val authorSignature: String?
		get() = source["author_signature"].asStringOrNull()

	override val text: String? by lazyItem { source["text"].asStringOrNull() }

	override val entities: List<MessageEntity>? by lazyItemOrNull("entities") {
		it.iterable().map { IrisMessageEntry(it) }
	}

	override val animation: Animation?
		get() = itemOrNull(source["animation"]) { IrisJsonAnimation(it) }

	override val audio: Audio?
		get() = itemOrNull(source["audio"]) { IrisJsonAudio(it) }

	override val document: Document?
		get() = itemOrNull(source["document"]) { IrisJsonDocument(it) }

	override val photo: List<PhotoSize>? by lazyItemOrNull("photo") { it.iterable().map { IrisJsonPhotoSize(it) } }

	override val sticker: Sticker? by lazyItemOrNull("sticker") { IrisJsonSticker(it) }

	override val video: Video? by lazyItemOrNull("video") { IrisJsonVideo(it) }

	override val videoNote: VideoNote? by lazyItemOrNull("video_note") { IrisJsonVideoNote(it) }

	override val voice: Voice? by lazyItemOrNull("voice") { IrisJsonVoice(it) }

	override val caption: String?
		get() = source["caption"].asStringOrNull()

	override val captionEntities: List<MessageEntity>?
		get() = itemOrNull(source["caption_entities"]) { it.iterable().map { IrisMessageEntry(it) } }

	override val contact: Contact?
		get() = itemOrNull(source["contact"]) { IrisJsonContact(it) }

	override val dice: Dice?
		get() = itemOrNull(source["dice"]) { IrisJsonDice(it) }

	override val game: Game?
		get() = itemOrNull(source["game"]) { IrisJsonGame(it) }

	override val poll: Poll?
		get() = itemOrNull(source["poll"]) { IrisJsonPoll(it) }

	override val venue: Venue?
		get() = itemOrNull(source["venue"]) { IrisJsonVenue(it) }

	override val location: Location?
		get() = itemOrNull(source["location"]) { IrisJsonLocation(it) }

	override val newChatMembers: List<User>?
		get() = itemOrNull(source["new_chat_members"]) { it.iterable().map { IrisJsonUser(it)} }

	override val leftChatMember: User?
		get() = itemOrNull(source["left_chat_member"]) { IrisJsonUser(it) }

	override val newChatTitle: String?
		get() = source["new_chat_title"].asStringOrNull()

	override val newChatPhoto: List<PhotoSize>?
		get() = itemOrNull(source["new_chat_photo"]) { it.iterable().map { IrisJsonPhotoSize(it) } }

	override val deleteChatPhoto: Boolean
		get() = source["delete_chat_photo"].asBooleanOrNull() ?: false

	override val groupChatCreated: Boolean
		get() = source["group_chat_created"].asBooleanOrNull() ?: false

	override val supergroupChatCreated: Boolean
		get() = source["supergroup_chat_created"].asBooleanOrNull() ?: false
	override val channelChatCreated: Boolean
		get() = source["channel_chat_created"].asBooleanOrNull() ?: false

	override val messageAutoDeleteTimerChanged: MessageAutoDeleteTimerChanged?
		get() = itemOrNull(source["message_auto_delete_timer_changed"]) { IrisJsonMessageAutoDeleteTimerChanged(it) }
	override val migrateToChatId: Long
		get() = source["migrate_to_chat_id"].asLongOrNull() ?: 0L

	override val migrateFromChatId: Long
		get() = source["migrate_from_chat_id"].asLongOrNull() ?: 0L

	override val pinnedMessage: Message? by lazyItemOrNull("pinned_message") { IrisJsonMessage(it) }

	override val invoice: Invoice?
		get() = itemOrNull(source["invoice"]) { IrisJsonInvoice(it) }

	override val successfulPayment: SuccessfulPayment?
		get() = itemOrNull(source["successful_payment"]) { IrisJsonSuccessfulPayment(it) }

	override val connectedWebsite: String?
		get() = source["connected_website"].asStringOrNull()

	override val passportData: PassportData?
		get() = itemOrNull(source["passport_data"]) { IrisJsonPassportData(it) }

	override val proximityAlertTriggered: ProximityAlertTriggered?
		get() = itemOrNull(source["proximity_alert_triggered"]) { IrisJsonProximityAlertTriggered(it) }

	override val voiceChatScheduled: VoiceChatScheduled?
		get() = itemOrNull(source["Voice_chat_scheduled"]) { IrisJsonVoiceChatScheduled(it) }

	override val voiceChatStarted: VoiceChatStarted?
		get() = itemOrNull(source["Voice_chat_started"]) { IrisJsonVoiceChatStarted(it) }

	override val voiceChatEnded: VoiceChatEnded?
		get() = itemOrNull(source["Voice_chat_ended"]) { IrisJsonVoiceChatEnded(it) }

	override val voiceChatParticipantsInvited: VoiceChatParticipantsInvited?
		get() = itemOrNull(source["voice_chat_participants_invited"]) { IrisJsonVoiceChatParticipantsInvited(it) }

	override val replyMarkup: InlineKeyboardMarkup?
		get() = itemOrNull(source["reply_markup"]) { IrisJsonInlineKeyboardMarkup(it) }
}