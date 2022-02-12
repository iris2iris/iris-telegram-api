package iris.tg.trigger

import iris.tg.api.items.*
import iris.tg.processors.pack.TgChatMemberUpdatedSplitter
import iris.tg.processors.pack.TgEventMessagePackHandler
import iris.tg.processors.pack.TgEventPackSplitMessageHandler

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Suppress("unused")
open class TriggerHandlerPack<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>() : TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	constructor(initializer: TriggerHandlerPack<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>.() -> Unit) : this() {
		apply(initializer)
	}

	private val ownerChanged = mutableListOf<TriggerPack<CMU>>()

	fun onOwnerChanged(trigger: TriggerPack<CMU>) {
		ownerChanged += trigger
	}

	fun onOwnerChanged(action: (List<CMU>) -> Unit) {
		ownerChanged += TriggerPackLambda(action)
	}

	override fun ownersChanged(items: List<CMU>) {
		ownerChanged.forEach { it.process(items) }
	}

	private val administratorChanged = mutableListOf<TriggerPack<CMU>>()

	fun onAdministratorChanged(trigger: TriggerPack<CMU>) {
		ownerChanged += trigger
	}

	fun onAdministratorChanged(action: (List<CMU>) -> Unit) {
		ownerChanged += TriggerPackLambda(action)
	}

	override fun administratorsChanged(items: List<CMU>) {
		ownerChanged.forEach { it.process(items) }
	}

	private val administratorCanceled = mutableListOf<TriggerPack<CMU>>()

	fun onAdministratorCanceled(trigger: TriggerPack<CMU>) {
		administratorCanceled += trigger
	}

	fun onAdministratorCanceled(action: (List<CMU>) -> Unit) {
		administratorCanceled += TriggerPackLambda(action)
	}

	override fun administratorsCanceled(items: List<CMU>) {
		administratorCanceled.forEach { it.process(items) }
	}

	private val chatMemberRestricted = mutableListOf<TriggerPack<CMU>>()

	fun onChatMemberRestricted(trigger: TriggerPack<CMU>) {
		chatMemberRestricted += trigger
	}

	fun onChatMemberRestricted(action: (List<CMU>) -> Unit) {
		chatMemberRestricted += TriggerPackLambda(action)
	}

	override fun chatMembersRestricted(items: List<CMU>) {
		chatMemberRestricted.forEach { it.process(items) }
	}

	private val chatMemberLeft = mutableListOf<TriggerPack<CMU>>()

	fun onChatMemberLeft(trigger: TriggerPack<CMU>) {
		chatMemberLeft += trigger
	}

	fun onChatMemberLeft(action: (List<CMU>) -> Unit) {
		chatMemberLeft += TriggerPackLambda(action)
	}

	override fun chatMemberLeft(items: List<CMU>) {
		chatMemberLeft.forEach { it.process(items) }
	}

	private val chatMemberBanned = mutableListOf<TriggerPack<CMU>>()

	fun onChatMemberBanned(trigger: TriggerPack<CMU>) {
		chatMemberBanned += trigger
	}

	fun onChatMemberBanned(action: (List<CMU>) -> Unit) {
		chatMemberBanned += TriggerPackLambda(action)
	}

	override fun chatMemberBanned(items: List<CMU>) {
		chatMemberBanned.forEach { it.process(items) }
	}

	private val chatMemberNew = mutableListOf<TriggerPack<CMU>>()

	fun onChatMemberNew(trigger: TriggerPack<CMU>) {
		chatMemberNew += trigger
	}

	fun onChatMemberNew(action: (List<CMU>) -> Unit) {
		chatMemberNew += TriggerPackLambda(action)
	}

	override fun chatMemberNew(items: List<CMU>) {
		chatMemberNew.forEach { it.process(items) }
	}

	private val splitter = TgEventPackSplitMessageHandler(this)

	override fun messages(messages: List<M>) {
		splitter.messages(messages)
	}

	private val editMessages = mutableListOf<TriggerPack<M>>()

	fun onMessageEdit(trigger: TriggerPack<M>) {
		editMessages += trigger
	}

	fun onMessageEdit(action: (List<M>) -> Unit) {
		editMessages += TriggerPackLambda(action)
	}

	override fun editedMessages(messages: List<M>) {
		editMessages.forEach { it.process(messages) }
	}

	private val channelPosts = mutableListOf<TriggerPack<M>>()

	fun onChannelPost(trigger: TriggerPack<M>) {
		channelPosts += trigger
	}

	fun onChannelPost(action: (List<M>) -> Unit) {
		channelPosts += TriggerPackLambda(action)
	}

	override fun channelPosts(messages: List<M>) {
		channelPosts.forEach { it.process(messages) }
	}

	private val editedChannelPosts = mutableListOf<TriggerPack<M>>()

	fun onEditedChannelPost(trigger: TriggerPack<M>) {
		editedChannelPosts += trigger
	}

	fun onEditedChannelPost(action: (List<M>) -> Unit) {
		editedChannelPosts += TriggerPackLambda(action)
	}

	override fun editedChannelPosts(messages: List<M>) {
		editedChannelPosts.forEach { it.process(messages) }
	}

	private val inlineQueries = mutableListOf<TriggerPack<IQ>>()

	fun onInlineQuery(trigger: TriggerPack<IQ>) {
		inlineQueries += trigger
	}

	fun onInlineQuery(action: (List<IQ>) -> Unit) {
		inlineQueries += TriggerPackLambda(action)
	}

	override fun inlineQueries(items: List<IQ>) {
		inlineQueries.forEach { it.process(items) }
	}

	private val chosenInlineResults = mutableListOf<TriggerPack<CIR>>()

	fun onChosenInlineResult(trigger: TriggerPack<CIR>) {
		chosenInlineResults += trigger
	}

	fun onChosenInlineResult(action: (List<CIR>) -> Unit) {
		chosenInlineResults += TriggerPackLambda(action)
	}

	override fun chosenInlineResults(items: List<CIR>) {
		chosenInlineResults.forEach { it.process(items) }
	}

	private val callbackQueries = mutableListOf<TriggerPack<CQ>>()

	fun onCallbackQuery(trigger: TriggerPack<CQ>) {
		callbackQueries += trigger
	}

	fun onCallbackQuery(action: (List<CQ>) -> Unit) {
		callbackQueries += TriggerPackLambda(action)
	}

	override fun callbackQueries(items: List<CQ>) {
		callbackQueries.forEach { it.process(items) }
	}

	private val shippingQueries = mutableListOf<TriggerPack<SQ>>()

	fun onShippingQuery(trigger: TriggerPack<SQ>) {
		shippingQueries += trigger
	}

	fun onShippingQuery(action: (List<SQ>) -> Unit) {
		shippingQueries += TriggerPackLambda(action)
	}

	override fun shippingQueries(items: List<SQ>) {
		shippingQueries.forEach { it.process(items) }
	}

	private val preCheckoutQueries = mutableListOf<TriggerPack<PCQ>>()

	fun onPreCheckoutQuery(trigger: TriggerPack<PCQ>) {
		preCheckoutQueries += trigger
	}

	fun onPreCheckoutQuery(action: (List<PCQ>) -> Unit) {
		preCheckoutQueries += TriggerPackLambda(action)
	}

	override fun preCheckoutQueries(items: List<PCQ>) {
		preCheckoutQueries.forEach { it.process(items) }
	}

	private val polls = mutableListOf<TriggerPack<P>>()

	fun onPoll(trigger: TriggerPack<P>) {
		polls += trigger
	}

	fun onPoll(action: (List<P>) -> Unit) {
		polls += TriggerPackLambda(action)
	}

	override fun polls(items: List<P>) {
		polls.forEach { it.process(items) }
	}

	private val pollAnswers = mutableListOf<TriggerPack<PA>>()

	fun onPollAnswer(trigger: TriggerPack<PA>) {
		pollAnswers += trigger
	}

	fun onPollAnswer(action: (List<PA>) -> Unit) {
		pollAnswers += TriggerPackLambda(action)
	}

	override fun pollAnswers(items: List<PA>) {
		pollAnswers.forEach { it.process(items) }
	}

	private val myChatMembers = mutableListOf<TriggerPack<CMU>>()

	fun onMyChatMember(trigger: TriggerPack<CMU>) {
		myChatMembers += trigger
	}

	fun onMyChatMember(action: (List<CMU>) -> Unit) {
		myChatMembers += TriggerPackLambda(action)
	}

	override fun myChatMembers(items: List<CMU>) {
		myChatMembers.forEach { it.process(items) }
	}

	private val cmSplitter = TgChatMemberUpdatedSplitter(this)

	override fun chatMembers(items: List<CMU>) {
		cmSplitter.process(items)
	}

	private val chatJoinRequest = mutableListOf<TriggerPack<CJR>>()

	fun onChatJoinRequest(trigger: TriggerPack<CJR>) {
		chatJoinRequest += trigger
	}

	fun onChatJoinRequest(action: (List<CJR>) -> Unit) {
		chatJoinRequest += TriggerPackLambda(action)
	}

	override fun chatJoinRequests(items: List<CJR>) {
		chatJoinRequest.forEach { it.process(items) }
	}

	private val texts = mutableListOf<TriggerPack<M>>()

	fun onMessage(trigger: TriggerPack<M>) {
		texts += trigger
	}

	fun onMessage(action: (List<M>) -> Unit) {
		texts += TriggerPackLambda(action)
	}

	override fun texts(messages: List<M>) {
		texts.forEach { it.process(messages) }
	}

	private val newChatMembers = mutableListOf<TriggerPack<M>>()

	fun onNewChatMember(trigger: TriggerPack<M>) {
		newChatMembers += trigger
	}

	fun onNewChatMember(action: (List<M>) -> Unit) {
		newChatMembers += TriggerPackLambda(action)
	}

	override fun newChatMembers(messages: List<M>) {
		newChatMembers.forEach { it.process(messages) }
	}

	private val leftChatMembers = mutableListOf<TriggerPack<M>>()

	fun onLeftChatMember(trigger: TriggerPack<M>) {
		leftChatMembers += trigger
	}

	fun onLeftChatMember(action: (List<M>) -> Unit) {
		leftChatMembers += TriggerPackLambda(action)
	}

	override fun leftChatMember(messages: List<M>) {
		leftChatMembers.forEach { it.process(messages) }
	}

	private val newChatTitles = mutableListOf<TriggerPack<M>>()

	fun onNewChatTitle(trigger: TriggerPack<M>) {
		newChatTitles += trigger
	}

	fun onNewChatTitle(action: (List<M>) -> Unit) {
		newChatTitles += TriggerPackLambda(action)
	}

	override fun newChatTitle(messages: List<M>) {
		newChatTitles.forEach { it.process(messages) }
	}

	private val newChatPhotos = mutableListOf<TriggerPack<M>>()

	fun onNewChatPhoto(trigger: TriggerPack<M>) {
		newChatPhotos += trigger
	}

	fun onNewChatPhoto(action: (List<M>) -> Unit) {
		newChatPhotos += TriggerPackLambda(action)
	}

	override fun newChatPhoto(messages: List<M>) {
		newChatPhotos.forEach { it.process(messages) }
	}

	private val deleteChatPhotos = mutableListOf<TriggerPack<M>>()

	fun onDeleteChatPhoto(trigger: TriggerPack<M>) {
		deleteChatPhotos += trigger
	}

	fun onDeleteChatPhoto(action: (List<M>) -> Unit) {
		deleteChatPhotos += TriggerPackLambda(action)
	}

	override fun deleteChatPhoto(messages: List<M>) {
		deleteChatPhotos.forEach { it.process(messages) }
	}

	private val groupChatCreated = mutableListOf<TriggerPack<M>>()

	fun onGroupChatCreated(trigger: TriggerPack<M>) {
		groupChatCreated += trigger
	}

	fun onGroupChatCreated(action: (List<M>) -> Unit) {
		groupChatCreated += TriggerPackLambda(action)
	}

	override fun groupChatCreated(messages: List<M>) {
		groupChatCreated.forEach { it.process(messages) }
	}

	private val supergroupChatCreated = mutableListOf<TriggerPack<M>>()

	fun onSupergroupChatCreated(trigger: TriggerPack<M>) {
		supergroupChatCreated += trigger
	}

	fun onSupergroupChatCreated(action: (List<M>) -> Unit) {
		supergroupChatCreated += TriggerPackLambda(action)
	}

	override fun supergroupChatCreated(messages: List<M>) {
		supergroupChatCreated.forEach { it.process(messages) }
	}

	private val channelChatCreated = mutableListOf<TriggerPack<M>>()

	fun onChannelChatCreated(trigger: TriggerPack<M>) {
		channelChatCreated += trigger
	}

	fun onChannelChatCreated(action: (List<M>) -> Unit) {
		channelChatCreated += TriggerPackLambda(action)
	}

	override fun channelChatCreated(messages: List<M>) {
		channelChatCreated.forEach { it.process(messages) }
	}

	private val messageAutoDeleteTimerChanged = mutableListOf<TriggerPack<M>>()

	fun onMessageAutoDeleteTimerChanged(trigger: TriggerPack<M>) {
		messageAutoDeleteTimerChanged += trigger
	}

	fun onMessageAutoDeleteTimerChanged(action: (List<M>) -> Unit) {
		messageAutoDeleteTimerChanged += TriggerPackLambda(action)
	}

	override fun messageAutoDeleteTimerChanged(messages: List<M>) {
		messageAutoDeleteTimerChanged.forEach { it.process(messages) }
	}

	private val migrateToChatId = mutableListOf<TriggerPack<M>>()

	fun onMigrateToChatId(trigger: TriggerPack<M>) {
		migrateToChatId += trigger
	}

	fun onMigrateToChatId(action: (List<M>) -> Unit) {
		migrateToChatId += TriggerPackLambda(action)
	}

	override fun migrateToChatId(messages: List<M>) {
		migrateToChatId.forEach { it.process(messages) }
	}

	private val migrateFromChatId = mutableListOf<TriggerPack<M>>()

	fun onMigrateFromChatId(trigger: TriggerPack<M>) {
		migrateFromChatId += trigger
	}

	fun onMigrateFromChatId(action: (List<M>) -> Unit) {
		migrateFromChatId += TriggerPackLambda(action)
	}

	override fun migrateFromChatId(messages: List<M>) {
		migrateFromChatId.forEach { it.process(messages) }
	}

	private val pinnedMessage = mutableListOf<TriggerPack<M>>()

	fun onPinnedMessage(trigger: TriggerPack<M>) {
		pinnedMessage += trigger
	}

	fun onPinnedMessage(action: (List<M>) -> Unit) {
		pinnedMessage += TriggerPackLambda(action)
	}

	override fun pinnedMessage(messages: List<M>) {
		pinnedMessage.forEach { it.process(messages) }
	}

	private val invoice = mutableListOf<TriggerPack<M>>()

	fun onInvoice(trigger: TriggerPack<M>) {
		invoice += trigger
	}

	fun onInvoice(action: (List<M>) -> Unit) {
		invoice += TriggerPackLambda(action)
	}

	override fun invoice(messages: List<M>) {
		invoice.forEach { it.process(messages) }
	}

	private val successfulPayment = mutableListOf<TriggerPack<M>>()

	fun onSuccessfulPayment(trigger: TriggerPack<M>) {
		successfulPayment += trigger
	}

	fun onSuccessfulPayment(action: (List<M>) -> Unit) {
		successfulPayment += TriggerPackLambda(action)
	}

	override fun successfulPayment(messages: List<M>) {
		successfulPayment.forEach { it.process(messages) }
	}

	private val connectedWebsite = mutableListOf<TriggerPack<M>>()

	fun onConnectedWebsite(trigger: TriggerPack<M>) {
		connectedWebsite += trigger
	}

	fun onConnectedWebsite(action: (List<M>) -> Unit) {
		connectedWebsite += TriggerPackLambda(action)
	}

	override fun connectedWebsite(messages: List<M>) {
		connectedWebsite.forEach { it.process(messages) }
	}

	private val passportData = mutableListOf<TriggerPack<M>>()

	fun onPassportData(trigger: TriggerPack<M>) {
		passportData += trigger
	}

	fun onPassportData(action: (List<M>) -> Unit) {
		passportData += TriggerPackLambda(action)
	}

	override fun passportData(messages: List<M>) {
		passportData.forEach { it.process(messages) }
	}

	private val proximityAlertTriggered = mutableListOf<TriggerPack<M>>()

	fun onProximityAlertTriggered(trigger: TriggerPack<M>) {
		proximityAlertTriggered += trigger
	}

	fun onProximityAlertTriggered(action: (List<M>) -> Unit) {
		proximityAlertTriggered += TriggerPackLambda(action)
	}

	override fun proximityAlertTriggered(messages: List<M>) {
		proximityAlertTriggered.forEach { it.process(messages) }
	}

	private val voiceChatScheduled = mutableListOf<TriggerPack<M>>()

	fun onVoiceChatScheduled(trigger: TriggerPack<M>) {
		voiceChatScheduled += trigger
	}

	fun onVoiceChatScheduled(action: (List<M>) -> Unit) {
		voiceChatScheduled += TriggerPackLambda(action)
	}

	override fun voiceChatScheduled(messages: List<M>) {
		voiceChatScheduled.forEach { it.process(messages) }
	}

	private val voiceChatStarted = mutableListOf<TriggerPack<M>>()

	fun onVoiceChatStarted(trigger: TriggerPack<M>) {
		voiceChatStarted += trigger
	}

	fun onVoiceChatStarted(action: (List<M>) -> Unit) {
		voiceChatStarted += TriggerPackLambda(action)
	}

	override fun voiceChatStarted(messages: List<M>) {
		voiceChatStarted.forEach { it.process(messages) }
	}

	private val voiceChatEnded = mutableListOf<TriggerPack<M>>()

	fun onVoiceChatEnded(trigger: TriggerPack<M>) {
		voiceChatEnded += trigger
	}

	fun onVoiceChatEnded(action: (List<M>) -> Unit) {
		voiceChatEnded += TriggerPackLambda(action)
	}

	override fun voiceChatEnded(messages: List<M>) {
		voiceChatEnded.forEach { it.process(messages) }
	}

	private val voiceChatParticipantsInvited = mutableListOf<TriggerPack<M>>()

	fun onVoiceChatParticipantsInvited(trigger: TriggerPack<M>) {
		voiceChatParticipantsInvited += trigger
	}

	fun onVoiceChatParticipantsInvited(action: (List<M>) -> Unit) {
		voiceChatParticipantsInvited += TriggerPackLambda(action)
	}

	override fun voiceChatParticipantsInvited(messages: List<M>) {
		voiceChatParticipantsInvited.forEach { it.process(messages) }
	}

	private val unresolvedMessage = mutableListOf<TriggerPack<M>>()

	fun onUnresolvedMessage(trigger: TriggerPack<M>) {
		unresolvedMessage += trigger
	}

	fun onUnresolvedMessage(action: (List<M>) -> Unit) {
		unresolvedMessage += TriggerPackLambda(action)
	}

	override fun unresolvedMessages(messages: List<M>) {
		unresolvedMessage.forEach { it.process(messages) }
	}
}