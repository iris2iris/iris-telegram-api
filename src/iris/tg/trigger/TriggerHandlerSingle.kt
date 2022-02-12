package iris.tg.trigger

import iris.tg.api.items.*
import iris.tg.processors.single.TgChatMemberUpdatedSplitter
import iris.tg.processors.single.TgEventMessageSingleHandler
import iris.tg.processors.single.TgEventSplitMessageHandler

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Suppress("unused")
open class TriggerHandlerSingle<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>() : TgEventMessageSingleHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	constructor(initializer: TriggerHandlerSingle<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>.() -> Unit) : this() {
		apply(initializer)
	}

	private val ownerChanged = mutableListOf<TriggerSingle<CMU>>()

	fun onOwnerChanged(trigger: TriggerSingle<CMU>) {
		ownerChanged += trigger
	}

	fun onOwnerChanged(action: (CMU) -> Unit) {
		ownerChanged += TriggerSingleLambda(action)
	}

	override fun ownerChanged(item: CMU) {
		ownerChanged.forEach { it.process(item) }
	}

	private val administratorChanged = mutableListOf<TriggerSingle<CMU>>()

	fun onAdministratorChanged(trigger: TriggerSingle<CMU>) {
		ownerChanged += trigger
	}

	fun onAdministratorChanged(action: (CMU) -> Unit) {
		ownerChanged += TriggerSingleLambda(action)
	}

	override fun administratorChanged(item: CMU) {
		ownerChanged.forEach { it.process(item) }
	}

	private val administratorCanceled = mutableListOf<TriggerSingle<CMU>>()

	fun onAdministratorCanceled(trigger: TriggerSingle<CMU>) {
		administratorCanceled += trigger
	}

	fun onAdministratorCanceled(action: (CMU) -> Unit) {
		administratorCanceled += TriggerSingleLambda(action)
	}

	override fun administratorCanceled(item: CMU) {
		administratorCanceled.forEach { it.process(item) }
	}

	private val chatMemberRestricted = mutableListOf<TriggerSingle<CMU>>()

	fun onChatMemberRestricted(trigger: TriggerSingle<CMU>) {
		chatMemberRestricted += trigger
	}

	fun onChatMemberRestricted(action: (CMU) -> Unit) {
		chatMemberRestricted += TriggerSingleLambda(action)
	}

	override fun chatMemberRestricted(item: CMU) {
		chatMemberRestricted.forEach { it.process(item) }
	}

	private val chatMemberLeft = mutableListOf<TriggerSingle<CMU>>()

	fun onChatMemberLeft(trigger: TriggerSingle<CMU>) {
		chatMemberLeft += trigger
	}

	fun onChatMemberLeft(action: (CMU) -> Unit) {
		chatMemberLeft += TriggerSingleLambda(action)
	}

	override fun chatMemberLeft(item: CMU) {
		chatMemberLeft.forEach { it.process(item) }
	}

	private val chatMemberBanned = mutableListOf<TriggerSingle<CMU>>()

	fun onChatMemberBanned(trigger: TriggerSingle<CMU>) {
		chatMemberBanned += trigger
	}

	fun onChatMemberBanned(action: (CMU) -> Unit) {
		chatMemberBanned += TriggerSingleLambda(action)
	}

	override fun chatMemberBanned(item: CMU) {
		chatMemberBanned.forEach { it.process(item) }
	}

	private val chatMemberNew = mutableListOf<TriggerSingle<CMU>>()

	fun onChatMemberNew(trigger: TriggerSingle<CMU>) {
		chatMemberNew += trigger
	}

	fun onChatMemberNew(action: (CMU) -> Unit) {
		chatMemberNew += TriggerSingleLambda(action)
	}

	override fun chatMemberNew(item: CMU) {
		chatMemberNew.forEach { it.process(item) }
	}

	private val splitter = TgEventSplitMessageHandler(this)

	override fun message(message: M) {
		splitter.message(message)
	}

	private val editMessages = mutableListOf<TriggerSingle<M>>()

	fun onMessageEdit(trigger: TriggerSingle<M>) {
		editMessages += trigger
	}

	fun onMessageEdit(action: (M) -> Unit) {
		editMessages += TriggerSingleLambda(action)
	}

	override fun editedMessage(message: M) {
		editMessages.forEach { it.process(message) }
	}

	private val channelPosts = mutableListOf<TriggerSingle<M>>()

	fun onChannelPost(trigger: TriggerSingle<M>) {
		channelPosts += trigger
	}

	fun onChannelPost(action: (M) -> Unit) {
		channelPosts += TriggerSingleLambda(action)
	}

	override fun channelPost(message: M) {
		channelPosts.forEach { it.process(message) }
	}

	private val editedChannelPosts = mutableListOf<TriggerSingle<M>>()

	fun onEditedChannelPost(trigger: TriggerSingle<M>) {
		editedChannelPosts += trigger
	}

	fun onEditedChannelPost(action: (M) -> Unit) {
		editedChannelPosts += TriggerSingleLambda(action)
	}

	override fun editedChannelPost(message: M) {
		editedChannelPosts.forEach { it.process(message) }
	}

	private val inlineQueries = mutableListOf<TriggerSingle<IQ>>()

	fun onInlineQuery(trigger: TriggerSingle<IQ>) {
		inlineQueries += trigger
	}

	fun onInlineQuery(action: (IQ) -> Unit) {
		inlineQueries += TriggerSingleLambda(action)
	}

	override fun inlineQuery(item: IQ) {
		inlineQueries.forEach { it.process(item) }
	}

	private val chosenInlineResults = mutableListOf<TriggerSingle<CIR>>()

	fun onChosenInlineResult(trigger: TriggerSingle<CIR>) {
		chosenInlineResults += trigger
	}

	fun onChosenInlineResult(action: (CIR) -> Unit) {
		chosenInlineResults += TriggerSingleLambda(action)
	}

	override fun chosenInlineResult(item: CIR) {
		chosenInlineResults.forEach { it.process(item) }
	}

	private val callbackQueries = mutableListOf<TriggerSingle<CQ>>()

	fun onCallbackQuery(trigger: TriggerSingle<CQ>) {
		callbackQueries += trigger
	}

	fun onCallbackQuery(action: (CQ) -> Unit) {
		callbackQueries += TriggerSingleLambda(action)
	}

	override fun callbackQuery(item: CQ) {
		callbackQueries.forEach { it.process(item) }
	}

	private val shippingQueries = mutableListOf<TriggerSingle<SQ>>()

	fun onShippingQuery(trigger: TriggerSingle<SQ>) {
		shippingQueries += trigger
	}

	fun onShippingQuery(action: (SQ) -> Unit) {
		shippingQueries += TriggerSingleLambda(action)
	}

	override fun shippingQuery(item: SQ) {
		shippingQueries.forEach { it.process(item) }
	}

	private val preCheckoutQueries = mutableListOf<TriggerSingle<PCQ>>()

	fun onPreCheckoutQuery(trigger: TriggerSingle<PCQ>) {
		preCheckoutQueries += trigger
	}

	fun onPreCheckoutQuery(action: (PCQ) -> Unit) {
		preCheckoutQueries += TriggerSingleLambda(action)
	}

	override fun preCheckoutQuery(item: PCQ) {
		preCheckoutQueries.forEach { it.process(item) }
	}

	private val polls = mutableListOf<TriggerSingle<P>>()

	fun onPoll(trigger: TriggerSingle<P>) {
		polls += trigger
	}

	fun onPoll(action: (P) -> Unit) {
		polls += TriggerSingleLambda(action)
	}

	override fun poll(item: P) {
		polls.forEach { it.process(item) }
	}

	private val pollAnswers = mutableListOf<TriggerSingle<PA>>()

	fun onPollAnswer(trigger: TriggerSingle<PA>) {
		pollAnswers += trigger
	}

	fun onPollAnswer(action: (PA) -> Unit) {
		pollAnswers += TriggerSingleLambda(action)
	}

	override fun pollAnswer(item: PA) {
		pollAnswers.forEach { it.process(item) }
	}

	private val myChatMembers = mutableListOf<TriggerSingle<CMU>>()

	fun onMyChatMember(trigger: TriggerSingle<CMU>) {
		myChatMembers += trigger
	}

	fun onMyChatMember(action: (CMU) -> Unit) {
		myChatMembers += TriggerSingleLambda(action)
	}

	override fun myChatMember(item: CMU) {
		myChatMembers.forEach { it.process(item) }
	}

	private val cmSplitter = TgChatMemberUpdatedSplitter(this)

	override fun chatMember(item: CMU) {
		cmSplitter.process(item)
	}

	private val chatJoinRequest = mutableListOf<TriggerSingle<CJR>>()

	fun onChatJoinRequest(trigger: TriggerSingle<CJR>) {
		chatJoinRequest += trigger
	}

	fun onChatJoinRequest(action: (CJR) -> Unit) {
		chatJoinRequest += TriggerSingleLambda(action)
	}

	override fun chatJoinRequest(item: CJR) {
		chatJoinRequest.forEach { it.process(item) }
	}

	private val texts = mutableListOf<TriggerSingle<M>>()

	fun onMessage(trigger: TriggerSingle<M>) {
		texts += trigger
	}

	fun onMessage(action: (M) -> Unit) {
		texts += TriggerSingleLambda(action)
	}

	override fun text(message: M) {
		texts.forEach { it.process(message) }
	}

	private val newChatMembers = mutableListOf<TriggerSingle<M>>()

	fun onNewChatMember(trigger: TriggerSingle<M>) {
		newChatMembers += trigger
	}

	fun onNewChatMember(action: (M) -> Unit) {
		newChatMembers += TriggerSingleLambda(action)
	}

	override fun newChatMember(message: M) {
		newChatMembers.forEach { it.process(message) }
	}

	private val leftChatMembers = mutableListOf<TriggerSingle<M>>()

	fun onLeftChatMember(trigger: TriggerSingle<M>) {
		leftChatMembers += trigger
	}

	fun onLeftChatMember(action: (M) -> Unit) {
		leftChatMembers += TriggerSingleLambda(action)
	}

	override fun leftChatMember(message: M) {
		leftChatMembers.forEach { it.process(message) }
	}

	private val newChatTitles = mutableListOf<TriggerSingle<M>>()

	fun onNewChatTitle(trigger: TriggerSingle<M>) {
		newChatTitles += trigger
	}

	fun onNewChatTitle(action: (M) -> Unit) {
		newChatTitles += TriggerSingleLambda(action)
	}

	override fun newChatTitle(message: M) {
		newChatTitles.forEach { it.process(message) }
	}

	private val newChatPhotos = mutableListOf<TriggerSingle<M>>()

	fun onNewChatPhoto(trigger: TriggerSingle<M>) {
		newChatPhotos += trigger
	}

	fun onNewChatPhoto(action: (M) -> Unit) {
		newChatPhotos += TriggerSingleLambda(action)
	}

	override fun newChatPhoto(message: M) {
		newChatPhotos.forEach { it.process(message) }
	}

	private val deleteChatPhotos = mutableListOf<TriggerSingle<M>>()

	fun onDeleteChatPhoto(trigger: TriggerSingle<M>) {
		deleteChatPhotos += trigger
	}

	fun onDeleteChatPhoto(action: (M) -> Unit) {
		deleteChatPhotos += TriggerSingleLambda(action)
	}

	override fun deleteChatPhoto(message: M) {
		deleteChatPhotos.forEach { it.process(message) }
	}

	private val groupChatCreated = mutableListOf<TriggerSingle<M>>()

	fun onGroupChatCreated(trigger: TriggerSingle<M>) {
		groupChatCreated += trigger
	}

	fun onGroupChatCreated(action: (M) -> Unit) {
		groupChatCreated += TriggerSingleLambda(action)
	}

	override fun groupChatCreated(message: M) {
		groupChatCreated.forEach { it.process(message) }
	}

	private val supergroupChatCreated = mutableListOf<TriggerSingle<M>>()

	fun onSupergroupChatCreated(trigger: TriggerSingle<M>) {
		supergroupChatCreated += trigger
	}

	fun onSupergroupChatCreated(action: (M) -> Unit) {
		supergroupChatCreated += TriggerSingleLambda(action)
	}

	override fun supergroupChatCreated(message: M) {
		supergroupChatCreated.forEach { it.process(message) }
	}

	private val channelChatCreated = mutableListOf<TriggerSingle<M>>()

	fun onChannelChatCreated(trigger: TriggerSingle<M>) {
		channelChatCreated += trigger
	}

	fun onChannelChatCreated(action: (M) -> Unit) {
		channelChatCreated += TriggerSingleLambda(action)
	}

	override fun channelChatCreated(message: M) {
		channelChatCreated.forEach { it.process(message) }
	}

	private val messageAutoDeleteTimerChanged = mutableListOf<TriggerSingle<M>>()

	fun onMessageAutoDeleteTimerChanged(trigger: TriggerSingle<M>) {
		messageAutoDeleteTimerChanged += trigger
	}

	fun onMessageAutoDeleteTimerChanged(action: (M) -> Unit) {
		messageAutoDeleteTimerChanged += TriggerSingleLambda(action)
	}

	override fun messageAutoDeleteTimerChanged(message: M) {
		messageAutoDeleteTimerChanged.forEach { it.process(message) }
	}

	private val migrateToChatId = mutableListOf<TriggerSingle<M>>()

	fun onMigrateToChatId(trigger: TriggerSingle<M>) {
		migrateToChatId += trigger
	}

	fun onMigrateToChatId(action: (M) -> Unit) {
		migrateToChatId += TriggerSingleLambda(action)
	}

	override fun migrateToChatId(message: M) {
		migrateToChatId.forEach { it.process(message) }
	}

	private val migrateFromChatId = mutableListOf<TriggerSingle<M>>()

	fun onMigrateFromChatId(trigger: TriggerSingle<M>) {
		migrateFromChatId += trigger
	}

	fun onMigrateFromChatId(action: (M) -> Unit) {
		migrateFromChatId += TriggerSingleLambda(action)
	}

	override fun migrateFromChatId(message: M) {
		migrateFromChatId.forEach { it.process(message) }
	}

	private val pinnedMessage = mutableListOf<TriggerSingle<M>>()

	fun onPinnedMessage(trigger: TriggerSingle<M>) {
		pinnedMessage += trigger
	}

	fun onPinnedMessage(action: (M) -> Unit) {
		pinnedMessage += TriggerSingleLambda(action)
	}

	override fun pinnedMessage(message: M) {
		pinnedMessage.forEach { it.process(message) }
	}

	private val invoice = mutableListOf<TriggerSingle<M>>()

	fun onInvoice(trigger: TriggerSingle<M>) {
		invoice += trigger
	}

	fun onInvoice(action: (M) -> Unit) {
		invoice += TriggerSingleLambda(action)
	}

	override fun invoice(message: M) {
		invoice.forEach { it.process(message) }
	}

	private val successfulPayment = mutableListOf<TriggerSingle<M>>()

	fun onSuccessfulPayment(trigger: TriggerSingle<M>) {
		successfulPayment += trigger
	}

	fun onSuccessfulPayment(action: (M) -> Unit) {
		successfulPayment += TriggerSingleLambda(action)
	}

	override fun successfulPayment(message: M) {
		successfulPayment.forEach { it.process(message) }
	}

	private val connectedWebsite = mutableListOf<TriggerSingle<M>>()

	fun onConnectedWebsite(trigger: TriggerSingle<M>) {
		connectedWebsite += trigger
	}

	fun onConnectedWebsite(action: (M) -> Unit) {
		connectedWebsite += TriggerSingleLambda(action)
	}

	override fun connectedWebsite(message: M) {
		connectedWebsite.forEach { it.process(message) }
	}

	private val passportData = mutableListOf<TriggerSingle<M>>()

	fun onPassportData(trigger: TriggerSingle<M>) {
		passportData += trigger
	}

	fun onPassportData(action: (M) -> Unit) {
		passportData += TriggerSingleLambda(action)
	}

	override fun passportData(message: M) {
		passportData.forEach { it.process(message) }
	}

	private val proximityAlertTriggered = mutableListOf<TriggerSingle<M>>()

	fun onProximityAlertTriggered(trigger: TriggerSingle<M>) {
		proximityAlertTriggered += trigger
	}

	fun onProximityAlertTriggered(action: (M) -> Unit) {
		proximityAlertTriggered += TriggerSingleLambda(action)
	}

	override fun proximityAlertTriggered(message: M) {
		proximityAlertTriggered.forEach { it.process(message) }
	}

	private val voiceChatScheduled = mutableListOf<TriggerSingle<M>>()

	fun onVoiceChatScheduled(trigger: TriggerSingle<M>) {
		voiceChatScheduled += trigger
	}

	fun onVoiceChatScheduled(action: (M) -> Unit) {
		voiceChatScheduled += TriggerSingleLambda(action)
	}

	override fun voiceChatScheduled(message: M) {
		voiceChatScheduled.forEach { it.process(message) }
	}

	private val voiceChatStarted = mutableListOf<TriggerSingle<M>>()

	fun onVoiceChatStarted(trigger: TriggerSingle<M>) {
		voiceChatStarted += trigger
	}

	fun onVoiceChatStarted(action: (M) -> Unit) {
		voiceChatStarted += TriggerSingleLambda(action)
	}

	override fun voiceChatStarted(message: M) {
		voiceChatStarted.forEach { it.process(message) }
	}

	private val voiceChatEnded = mutableListOf<TriggerSingle<M>>()

	fun onVoiceChatEnded(trigger: TriggerSingle<M>) {
		voiceChatEnded += trigger
	}

	fun onVoiceChatEnded(action: (M) -> Unit) {
		voiceChatEnded += TriggerSingleLambda(action)
	}

	override fun voiceChatEnded(message: M) {
		voiceChatEnded.forEach { it.process(message) }
	}

	private val voiceChatParticipantsInvited = mutableListOf<TriggerSingle<M>>()

	fun onVoiceChatParticipantsInvited(trigger: TriggerSingle<M>) {
		voiceChatParticipantsInvited += trigger
	}

	fun onVoiceChatParticipantsInvited(action: (M) -> Unit) {
		voiceChatParticipantsInvited += TriggerSingleLambda(action)
	}

	override fun voiceChatParticipantsInvited(message: M) {
		voiceChatParticipantsInvited.forEach { it.process(message) }
	}

	private val unresolvedMessage = mutableListOf<TriggerSingle<M>>()

	fun onUnresolvedMessage(trigger: TriggerSingle<M>) {
		unresolvedMessage += trigger
	}

	fun onUnresolvedMessage(action: (M) -> Unit) {
		unresolvedMessage += TriggerSingleLambda(action)
	}

	override fun unresolvedMessage(message: M) {
		unresolvedMessage.forEach { it.process(message) }
	}
}