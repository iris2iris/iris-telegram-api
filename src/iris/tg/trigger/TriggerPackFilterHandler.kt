package iris.tg.trigger

import iris.tg.api.items.*
import iris.tg.processors.pack.TgChatMemberUpdatedSplitter
import iris.tg.processors.pack.TgEventMessagePackFilter
import iris.tg.processors.pack.TgEventMessagePackHandler
import iris.tg.processors.pack.TgEventPackSplitMessageHandler

/**
 * @created 01.11.2020
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Suppress("unused")
open class TriggerPackFilterHandler<M : Message
		, IQ : InlineQuery
		, CIR : ChosenInlineResult
		, CQ : CallbackQuery
		, SQ : ShippingQuery
		, PCQ: PreCheckoutQuery
		, P: Poll
		, PA: PollAnswer
		, CMU: ChatMemberUpdated
		, CJR: ChatJoinRequest
>(private val handler: TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>) : TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR> {

	constructor(handler: TgEventMessagePackHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>, initializer: TriggerPackFilterHandler<M, IQ, CIR, CQ, SQ, PCQ, P, PA, CMU, CJR>.() -> Unit) : this(handler) {
		apply(initializer)
	}

	private val ownerChanged = mutableListOf<TriggerPackFilter<CMU>>()

	fun onOwnerChanged(trigger: TriggerPackFilter<CMU>) {
		ownerChanged += trigger
	}

	fun onOwnersChanged(action: (List<CMU>) -> List<CMU>) {
		ownerChanged += TriggerPackFilterLambda(action)
	}

	fun onOwnerChanged(action: (CMU) -> Boolean) {
		ownerChanged += TriggerPackFilterSingleLambda(action)
	}

	override fun ownersChanged(items: List<CMU>) {
		var items = items
		ownerChanged.forEach { 
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.ownersChanged(items)
	}

	private val administratorChanged = mutableListOf<TriggerPackFilter<CMU>>()

	fun onAdministratorChanged(trigger: TriggerPackFilter<CMU>) {
		administratorChanged += trigger
	}

	fun onAdministratorsChanged(action: (List<CMU>) -> List<CMU>) {
		administratorChanged += TriggerPackFilterLambda(action)
	}

	fun onAdministratorChanged(action: (CMU) -> Boolean) {
		administratorChanged += TriggerPackFilterSingleLambda(action)
	}

	override fun administratorsChanged(items: List<CMU>) {
		var items = items
		administratorChanged.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.administratorsChanged(items)
	}

	private val administratorCanceled = mutableListOf<TriggerPackFilter<CMU>>()

	fun onAdministratorCanceled(trigger: TriggerPackFilter<CMU>) {
		administratorCanceled += trigger
	}

	fun onAdministratorsCanceled(action: (List<CMU>) -> List<CMU>) {
		administratorCanceled += TriggerPackFilterLambda(action)
	}

	fun onAdministratorCanceled(action: (CMU) -> Boolean) {
		administratorCanceled += TriggerPackFilterSingleLambda(action)
	}

	override fun administratorsCanceled(items: List<CMU>) {
		var items = items
		administratorCanceled.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.administratorsCanceled(items)
	}

	private val chatMemberRestricted = mutableListOf<TriggerPackFilter<CMU>>()

	fun onChatMemberRestricted(trigger: TriggerPackFilter<CMU>) {
		chatMemberRestricted += trigger
	}

	fun onChatMembersRestricted(action: (List<CMU>) -> List<CMU>) {
		chatMemberRestricted += TriggerPackFilterLambda(action)
	}

	fun onChatMemberRestricted(action: (CMU) -> Boolean) {
		chatMemberRestricted += TriggerPackFilterSingleLambda(action)
	}

	override fun chatMembersRestricted(items: List<CMU>) {
		var items = items
		chatMemberRestricted.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chatMembersRestricted(items)
	}

	private val chatMemberLeft = mutableListOf<TriggerPackFilter<CMU>>()

	fun onChatMemberLeft(trigger: TriggerPackFilter<CMU>) {
		chatMemberLeft += trigger
	}

	fun onChatMembersLeft(action: (List<CMU>) -> List<CMU>) {
		chatMemberLeft += TriggerPackFilterLambda(action)
	}

	fun onChatMemberLeft(action: (CMU) -> Boolean) {
		chatMemberLeft += TriggerPackFilterSingleLambda(action)
	}

	override fun chatMemberLeft(items: List<CMU>) {
		var items = items
		chatMemberLeft.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chatMemberLeft(items)
	}

	private val chatMemberBanned = mutableListOf<TriggerPackFilter<CMU>>()

	fun onChatMemberBanned(trigger: TriggerPackFilter<CMU>) {
		chatMemberBanned += trigger
	}

	fun onChatMembersBanned(action: (List<CMU>) -> List<CMU>) {
		chatMemberBanned += TriggerPackFilterLambda(action)
	}

	fun onChatMemberBanned(action: (CMU) -> Boolean) {
		chatMemberBanned += TriggerPackFilterSingleLambda(action)
	}

	override fun chatMemberBanned(items: List<CMU>) {
		var items = items
		chatMemberBanned.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chatMemberBanned(items)
	}

	private val chatMemberNew = mutableListOf<TriggerPackFilter<CMU>>()

	fun onChatMemberNew(trigger: TriggerPackFilter<CMU>) {
		chatMemberNew += trigger
	}

	fun onChatMembersNew(action: (List<CMU>) -> List<CMU>) {
		chatMemberNew += TriggerPackFilterLambda(action)
	}

	fun onChatMemberNew(action: (CMU) -> Boolean) {
		chatMemberNew += TriggerPackFilterSingleLambda(action)
	}

	override fun chatMemberNew(items: List<CMU>) {
		var items = items
		chatMemberNew.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chatMemberNew(items)
	}

	private val splitter = TgEventPackSplitMessageHandler(this)

	override fun messages(messages: List<M>) {
		splitter.messages(messages)
	}

	private val editMessages = mutableListOf<TriggerPackFilter<M>>()

	fun onMessageEdit(trigger: TriggerPackFilter<M>) {
		editMessages += trigger
	}

	fun onMessagesEdit(action: (List<M>) -> List<M>) {
		editMessages += TriggerPackFilterLambda(action)
	}

	fun onMessageEdit(action: (M) -> Boolean) {
		editMessages += TriggerPackFilterSingleLambda(action)
	}

	override fun editedMessages(messages: List<M>) {
		var items = messages
		editMessages.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.editedMessages(items)
	}

	private val channelPosts = mutableListOf<TriggerPackFilter<M>>()

	fun onChannelPost(trigger: TriggerPackFilter<M>) {
		channelPosts += trigger
	}

	fun onChannelPosts(action: (List<M>) -> List<M>) {
		channelPosts += TriggerPackFilterLambda(action)
	}

	fun onChannelPost(action: (M) -> Boolean) {
		channelPosts += TriggerPackFilterSingleLambda(action)
	}

	override fun channelPosts(messages: List<M>) {
		var items = messages
		channelPosts.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.channelPosts(items)
	}

	private val editedChannelPosts = mutableListOf<TriggerPackFilter<M>>()

	fun onEditedChannelPost(trigger: TriggerPackFilter<M>) {
		editedChannelPosts += trigger
	}

	fun onEditedChannelPosts(action: (List<M>) -> List<M>) {
		editedChannelPosts += TriggerPackFilterLambda(action)
	}

	fun onEditedChannelPost(action: (M) -> Boolean) {
		editedChannelPosts += TriggerPackFilterSingleLambda(action)
	}

	override fun editedChannelPosts(messages: List<M>) {
		var items = messages
		editedChannelPosts.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.editedChannelPosts(items)
	}

	private val inlineQueries = mutableListOf<TriggerPackFilter<IQ>>()

	fun onInlineQuery(trigger: TriggerPackFilter<IQ>) {
		inlineQueries += trigger
	}

	fun onInlineQueries(action: (List<IQ>) -> List<IQ>) {
		inlineQueries += TriggerPackFilterLambda(action)
	}

	fun onInlineQuery(action: (IQ) -> Boolean) {
		inlineQueries += TriggerPackFilterSingleLambda(action)
	}

	override fun inlineQueries(items: List<IQ>) {
		var items = items
		inlineQueries.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.inlineQueries(items)
	}

	private val chosenInlineResults = mutableListOf<TriggerPackFilter<CIR>>()

	fun onChosenInlineResult(trigger: TriggerPackFilter<CIR>) {
		chosenInlineResults += trigger
	}

	fun onChosenInlineResults(action: (List<CIR>) -> List<CIR>) {
		chosenInlineResults += TriggerPackFilterLambda(action)
	}

	fun onChosenInlineResult(action: (CIR) -> Boolean) {
		chosenInlineResults += TriggerPackFilterSingleLambda(action)
	}

	override fun chosenInlineResults(items: List<CIR>) {
		var items = items
		chosenInlineResults.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chosenInlineResults(items)
	}

	private val callbackQueries = mutableListOf<TriggerPackFilter<CQ>>()

	fun onCallbackQuery(trigger: TriggerPackFilter<CQ>) {
		callbackQueries += trigger
	}

	fun onCallbackQueries(action: (List<CQ>) -> List<CQ>) {
		callbackQueries += TriggerPackFilterLambda(action)
	}

	fun onCallbackQuery(action: (CQ) -> Boolean) {
		callbackQueries += TriggerPackFilterSingleLambda(action)
	}

	override fun callbackQueries(items: List<CQ>) {
		var items = items
		callbackQueries.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.callbackQueries(items)
	}

	private val shippingQueries = mutableListOf<TriggerPackFilter<SQ>>()

	fun onShippingQuery(trigger: TriggerPackFilter<SQ>) {
		shippingQueries += trigger
	}

	fun onShippingQueries(action: (List<SQ>) -> List<SQ>) {
		shippingQueries += TriggerPackFilterLambda(action)
	}

	fun onShippingQuery(action: (SQ) -> Boolean) {
		shippingQueries += TriggerPackFilterSingleLambda(action)
	}

	override fun shippingQueries(items: List<SQ>) {
		var items = items
		shippingQueries.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.shippingQueries(items)
	}

	private val preCheckoutQueries = mutableListOf<TriggerPackFilter<PCQ>>()

	fun onPreCheckoutQuery(trigger: TriggerPackFilter<PCQ>) {
		preCheckoutQueries += trigger
	}

	fun onPreCheckoutQueries(action: (List<PCQ>) -> List<PCQ>) {
		preCheckoutQueries += TriggerPackFilterLambda(action)
	}

	fun onPreCheckoutQuery(action: (PCQ) -> Boolean) {
		preCheckoutQueries += TriggerPackFilterSingleLambda(action)
	}

	override fun preCheckoutQueries(items: List<PCQ>) {
		var items = items
		preCheckoutQueries.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.preCheckoutQueries(items)
	}

	private val polls = mutableListOf<TriggerPackFilter<P>>()

	fun onPoll(trigger: TriggerPackFilter<P>) {
		polls += trigger
	}

	fun onPolls(action: (List<P>) -> List<P>) {
		polls += TriggerPackFilterLambda(action)
	}

	fun onPoll(action: (P) -> Boolean) {
		polls += TriggerPackFilterSingleLambda(action)
	}

	override fun polls(items: List<P>) {
		var items = items
		polls.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.polls(items)
	}

	private val pollAnswers = mutableListOf<TriggerPackFilter<PA>>()

	fun onPollAnswer(trigger: TriggerPackFilter<PA>) {
		pollAnswers += trigger
	}

	fun onPollAnswers(action: (List<PA>) -> List<PA>) {
		pollAnswers += TriggerPackFilterLambda(action)
	}

	fun onPollAnswer(action: (PA) -> Boolean) {
		pollAnswers += TriggerPackFilterSingleLambda(action)
	}

	override fun pollAnswers(items: List<PA>) {
		var items = items
		pollAnswers.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.pollAnswers(items)
	}

	private val myChatMembers = mutableListOf<TriggerPackFilter<CMU>>()

	fun onMyChatMember(trigger: TriggerPackFilter<CMU>) {
		myChatMembers += trigger
	}

	fun onMyChatMembers(action: (List<CMU>) -> List<CMU>) {
		myChatMembers += TriggerPackFilterLambda(action)
	}

	fun onMyChatMember(action: (CMU) -> Boolean) {
		myChatMembers += TriggerPackFilterSingleLambda(action)
	}

	override fun myChatMembers(items: List<CMU>) {
		var items = items
		myChatMembers.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.myChatMembers(items)
	}

	private val cmSplitter = TgChatMemberUpdatedSplitter(this)

	override fun chatMembers(items: List<CMU>) {
		cmSplitter.process(items)
	}

	private val chatJoinRequest = mutableListOf<TriggerPackFilter<CJR>>()

	fun onChatJoinRequest(trigger: TriggerPackFilter<CJR>) {
		chatJoinRequest += trigger
	}

	fun onChatJoinRequests(action: (List<CJR>) -> List<CJR>) {
		chatJoinRequest += TriggerPackFilterLambda(action)
	}

	fun onChatJoinRequest(action: (CJR) -> Boolean) {
		chatJoinRequest += TriggerPackFilterSingleLambda(action)
	}

	override fun chatJoinRequests(items: List<CJR>) {
		var items = items
		chatJoinRequest.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.chatJoinRequests(items)
	}

	private val texts = mutableListOf<TriggerPackFilter<M>>()

	fun onMessages(trigger: TriggerPackFilter<M>) {
		texts += trigger
	}

	fun onMessages(action: (List<M>) -> List<M>) {
		texts += TriggerPackFilterLambda(action)
	}

	fun onMessage(action: (M) -> Boolean) {
		texts += TriggerPackFilterSingleLambda(action)
	}

	override fun texts(messages: List<M>) {
		var items = messages
		texts.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.texts(items)
	}

	private val newChatMembers = mutableListOf<TriggerPackFilter<M>>()

	fun onNewChatMembers(trigger: TriggerPackFilter<M>) {
		newChatMembers += trigger
	}

	fun onNewChatMembers(action: (List<M>) -> List<M>) {
		newChatMembers += TriggerPackFilterLambda(action)
	}

	fun onNewChatMember(action: (M) -> Boolean) {
		newChatMembers += TriggerPackFilterSingleLambda(action)
	}

	override fun newChatMembers(messages: List<M>) {
		var items = messages
		newChatMembers.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.newChatMembers(items)
	}

	private val leftChatMembers = mutableListOf<TriggerPackFilter<M>>()

	fun onLeftChatMembers(trigger: TriggerPackFilter<M>) {
		leftChatMembers += trigger
	}

	fun onLeftChatMembers(action: (List<M>) -> List<M>) {
		leftChatMembers += TriggerPackFilterLambda(action)
	}

	fun onLeftChatMember(action: (M) -> Boolean) {
		leftChatMembers += TriggerPackFilterSingleLambda(action)
	}

	override fun leftChatMember(messages: List<M>) {
		var items = messages
		leftChatMembers.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.leftChatMember(items)
	}

	private val newChatTitles = mutableListOf<TriggerPackFilter<M>>()

	fun onNewChatTitles(trigger: TriggerPackFilter<M>) {
		newChatTitles += trigger
	}

	fun onNewChatTitles(action: (List<M>) -> List<M>) {
		newChatTitles += TriggerPackFilterLambda(action)
	}

	fun onNewChatTitle(action: (M) -> Boolean) {
		newChatTitles += TriggerPackFilterSingleLambda(action)
	}

	override fun newChatTitle(messages: List<M>) {
		var items = messages
		newChatTitles.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.newChatTitle(items)
	}

	private val newChatPhotos = mutableListOf<TriggerPackFilter<M>>()

	fun onNewChatPhoto(trigger: TriggerPackFilter<M>) {
		newChatPhotos += trigger
	}

	fun onNewChatPhotos(action: (List<M>) -> List<M>) {
		newChatPhotos += TriggerPackFilterLambda(action)
	}

	fun onNewChatPhoto(action: (M) -> Boolean) {
		newChatPhotos += TriggerPackFilterSingleLambda(action)
	}

	override fun newChatPhoto(messages: List<M>) {
		var items = messages
		newChatPhotos.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.newChatPhoto(items)
	}

	private val deleteChatPhotos = mutableListOf<TriggerPackFilter<M>>()

	fun onDeleteChatPhoto(trigger: TriggerPackFilter<M>) {
		deleteChatPhotos += trigger
	}

	fun onDeleteChatPhotos(action: (List<M>) -> List<M>) {
		deleteChatPhotos += TriggerPackFilterLambda(action)
	}

	fun onDeleteChatPhoto(action: (M) -> Boolean) {
		deleteChatPhotos += TriggerPackFilterSingleLambda(action)
	}

	override fun deleteChatPhoto(messages: List<M>) {
		var items = messages
		deleteChatPhotos.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.deleteChatPhoto(items)
	}

	private val groupChatCreated = mutableListOf<TriggerPackFilter<M>>()

	fun onGroupChatCreated(trigger: TriggerPackFilter<M>) {
		groupChatCreated += trigger
	}

	fun onGroupChatsCreated(action: (List<M>) -> List<M>) {
		groupChatCreated += TriggerPackFilterLambda(action)
	}

	fun onGroupChatCreated(action: (M) -> Boolean) {
		groupChatCreated += TriggerPackFilterSingleLambda(action)
	}

	override fun groupChatCreated(messages: List<M>) {
		var items = messages
		groupChatCreated.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.groupChatCreated(items)
	}

	private val supergroupChatCreated = mutableListOf<TriggerPackFilter<M>>()

	fun onSupergroupChatCreated(trigger: TriggerPackFilter<M>) {
		supergroupChatCreated += trigger
	}

	fun onSupergroupChatsCreated(action: (List<M>) -> List<M>) {
		supergroupChatCreated += TriggerPackFilterLambda(action)
	}

	fun onSupergroupChatCreated(action: (M) -> Boolean) {
		supergroupChatCreated += TriggerPackFilterSingleLambda(action)
	}

	override fun supergroupChatCreated(messages: List<M>) {
		var items = messages
		supergroupChatCreated.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.supergroupChatCreated(items)
	}

	private val channelChatCreated = mutableListOf<TriggerPackFilter<M>>()

	fun onChannelChatCreated(trigger: TriggerPackFilter<M>) {
		channelChatCreated += trigger
	}

	fun onChannelChatsCreated(action: (List<M>) -> List<M>) {
		channelChatCreated += TriggerPackFilterLambda(action)
	}

	fun onChannelChatCreated(action: (M) -> Boolean) {
		channelChatCreated += TriggerPackFilterSingleLambda(action)
	}

	override fun channelChatCreated(messages: List<M>) {
		var items = messages
		channelChatCreated.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.channelChatCreated(items)
	}

	private val messageAutoDeleteTimerChanged = mutableListOf<TriggerPackFilter<M>>()

	fun onMessageAutoDeleteTimerChanged(trigger: TriggerPackFilter<M>) {
		messageAutoDeleteTimerChanged += trigger
	}

	fun onMessageAutoDeleteTimersChanged(action: (List<M>) -> List<M>) {
		messageAutoDeleteTimerChanged += TriggerPackFilterLambda(action)
	}

	fun onMessageAutoDeleteTimerChanged(action: (M) -> Boolean) {
		messageAutoDeleteTimerChanged += TriggerPackFilterSingleLambda(action)
	}

	override fun messageAutoDeleteTimerChanged(messages: List<M>) {
		var items = messages
		messageAutoDeleteTimerChanged.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.messageAutoDeleteTimerChanged(items)
	}

	private val migrateToChatId = mutableListOf<TriggerPackFilter<M>>()

	fun onMigrateToChatId(trigger: TriggerPackFilter<M>) {
		migrateToChatId += trigger
	}

	fun onMigrateToChatsId(action: (List<M>) -> List<M>) {
		migrateToChatId += TriggerPackFilterLambda(action)
	}

	fun onMigrateToChatId(action: (M) -> Boolean) {
		migrateToChatId += TriggerPackFilterSingleLambda(action)
	}

	override fun migrateToChatId(messages: List<M>) {
		var items = messages
		migrateToChatId.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.migrateToChatId(items)
	}

	private val migrateFromChatId = mutableListOf<TriggerPackFilter<M>>()

	fun onMigrateFromChatId(trigger: TriggerPackFilter<M>) {
		migrateFromChatId += trigger
	}

	fun onMigrateFromChatsId(action: (List<M>) -> List<M>) {
		migrateFromChatId += TriggerPackFilterLambda(action)
	}

	fun onMigrateFromChatId(action: (M) -> Boolean) {
		migrateFromChatId += TriggerPackFilterSingleLambda(action)
	}

	override fun migrateFromChatId(messages: List<M>) {
		var items = messages
		migrateFromChatId.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.migrateFromChatId(items)
	}

	private val pinnedMessage = mutableListOf<TriggerPackFilter<M>>()

	fun onPinnedMessage(trigger: TriggerPackFilter<M>) {
		pinnedMessage += trigger
	}

	fun onPinnedMessages(action: (List<M>) -> List<M>) {
		pinnedMessage += TriggerPackFilterLambda(action)
	}

	fun onPinnedMessage(action: (M) -> Boolean) {
		pinnedMessage += TriggerPackFilterSingleLambda(action)
	}

	override fun pinnedMessage(messages: List<M>) {
		var items = messages
		pinnedMessage.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.pinnedMessage(items)
	}

	private val invoice = mutableListOf<TriggerPackFilter<M>>()

	fun onInvoice(trigger: TriggerPackFilter<M>) {
		invoice += trigger
	}

	fun onInvoices(action: (List<M>) -> List<M>) {
		invoice += TriggerPackFilterLambda(action)
	}

	fun onInvoice(action: (M) -> Boolean) {
		invoice += TriggerPackFilterSingleLambda(action)
	}

	override fun invoice(messages: List<M>) {
		var items = messages
		invoice.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.invoice(items)
	}

	private val successfulPayment = mutableListOf<TriggerPackFilter<M>>()

	fun onSuccessfulPayment(trigger: TriggerPackFilter<M>) {
		successfulPayment += trigger
	}

	fun onSuccessfulPayments(action: (List<M>) -> List<M>) {
		successfulPayment += TriggerPackFilterLambda(action)
	}

	fun onSuccessfulPayment(action: (M) -> Boolean) {
		successfulPayment += TriggerPackFilterSingleLambda(action)
	}

	override fun successfulPayment(messages: List<M>) {
		var items = messages
		successfulPayment.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.successfulPayment(items)
	}

	private val connectedWebsite = mutableListOf<TriggerPackFilter<M>>()

	fun onConnectedWebsite(trigger: TriggerPackFilter<M>) {
		connectedWebsite += trigger
	}

	fun onConnectedWebsites(action: (List<M>) -> List<M>) {
		connectedWebsite += TriggerPackFilterLambda(action)
	}

	fun onConnectedWebsite(action: (M) -> Boolean) {
		connectedWebsite += TriggerPackFilterSingleLambda(action)
	}

	override fun connectedWebsite(messages: List<M>) {
		var items = messages
		connectedWebsite.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.connectedWebsite(items)
	}

	private val passportData = mutableListOf<TriggerPackFilter<M>>()

	fun onPassportData(trigger: TriggerPackFilter<M>) {
		passportData += trigger
	}

	fun onPassportsData(action: (List<M>) -> List<M>) {
		passportData += TriggerPackFilterLambda(action)
	}

	fun onPassportData(action: (M) -> Boolean) {
		passportData += TriggerPackFilterSingleLambda(action)
	}

	override fun passportData(messages: List<M>) {
		var items = messages
		passportData.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.passportData(items)
	}

	private val proximityAlertTriggered = mutableListOf<TriggerPackFilter<M>>()

	fun onProximityAlertTriggered(trigger: TriggerPackFilter<M>) {
		proximityAlertTriggered += trigger
	}

	fun onProximityAlertsTriggered(action: (List<M>) -> List<M>) {
		proximityAlertTriggered += TriggerPackFilterLambda(action)
	}

	fun onProximityAlertTriggered(action: (M) -> Boolean) {
		proximityAlertTriggered += TriggerPackFilterSingleLambda(action)
	}

	override fun proximityAlertTriggered(messages: List<M>) {
		var items = messages
		proximityAlertTriggered.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.proximityAlertTriggered(items)
	}

	private val voiceChatScheduled = mutableListOf<TriggerPackFilter<M>>()

	fun onVoiceChatScheduled(trigger: TriggerPackFilter<M>) {
		voiceChatScheduled += trigger
	}

	fun onVoiceChatsScheduled(action: (List<M>) -> List<M>) {
		voiceChatScheduled += TriggerPackFilterLambda(action)
	}

	fun onVoiceChatScheduled(action: (M) -> Boolean) {
		voiceChatScheduled += TriggerPackFilterSingleLambda(action)
	}

	override fun voiceChatScheduled(messages: List<M>) {
		var items = messages
		voiceChatScheduled.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.voiceChatScheduled(items)
	}

	private val voiceChatStarted = mutableListOf<TriggerPackFilter<M>>()

	fun onVoiceChatStarted(trigger: TriggerPackFilter<M>) {
		voiceChatStarted += trigger
	}

	fun onVoiceChatsStarted(action: (List<M>) -> List<M>) {
		voiceChatStarted += TriggerPackFilterLambda(action)
	}

	fun onVoiceChatStarted(action: (M) -> Boolean) {
		voiceChatStarted += TriggerPackFilterSingleLambda(action)
	}

	override fun voiceChatStarted(messages: List<M>) {
		var items = messages
		voiceChatStarted.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.voiceChatStarted(items)
	}

	private val voiceChatEnded = mutableListOf<TriggerPackFilter<M>>()

	fun onVoiceChatEnded(trigger: TriggerPackFilter<M>) {
		voiceChatEnded += trigger
	}

	fun onVoiceChatsEnded(action: (List<M>) -> List<M>) {
		voiceChatEnded += TriggerPackFilterLambda(action)
	}

	fun onVoiceChatEnded(action: (M) -> Boolean) {
		voiceChatEnded += TriggerPackFilterSingleLambda(action)
	}

	override fun voiceChatEnded(messages: List<M>) {
		var items = messages
		voiceChatEnded.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.voiceChatEnded(items)
	}

	private val voiceChatParticipantsInvited = mutableListOf<TriggerPackFilter<M>>()

	fun onVoiceChatParticipantsInvited(trigger: TriggerPackFilter<M>) {
		voiceChatParticipantsInvited += trigger
	}

	fun onVoiceChatParticipantsInvited(action: (List<M>) -> List<M>) {
		voiceChatParticipantsInvited += TriggerPackFilterLambda(action)
	}

	fun onVoiceChatParticipantInvited(action: (M) -> Boolean) {
		voiceChatParticipantsInvited += TriggerPackFilterSingleLambda(action)
	}

	override fun voiceChatParticipantsInvited(messages: List<M>) {
		var items = messages
		voiceChatParticipantsInvited.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.voiceChatParticipantsInvited(items)
	}

	private val unresolvedMessage = mutableListOf<TriggerPackFilter<M>>()

	fun onUnresolvedMessage(trigger: TriggerPackFilter<M>) {
		unresolvedMessage += trigger
	}

	fun onUnresolvedMessages(action: (List<M>) -> List<M>) {
		unresolvedMessage += TriggerPackFilterLambda(action)
	}

	fun onUnresolvedMessage(action: (M) -> Boolean) {
		unresolvedMessage += TriggerPackFilterSingleLambda(action)
	}

	override fun unresolvedMessages(messages: List<M>) {
		var items = messages
		unresolvedMessage.forEach {
			items = it.process(items)
			if (items.isEmpty())
				return
		}
		handler.unresolvedMessages(items)
	}
}