package iris.tg.py

import iris.tg.QueuedService
import iris.tg.TgService
import iris.tg.api.AllowedUpdates
import iris.tg.api.SendDefaults
import iris.tg.api.TgApiObjFuture
import iris.tg.api.TgApiObject
import iris.tg.api.items.*
import iris.tg.command.TgCommandHandler
import iris.tg.longpoll.longPollQueued
import iris.tg.py.items.PyMessage
import iris.tg.trigger.TriggerPack2Lambda
import iris.tg.trigger.TriggerPack2Single
import iris.tg.trigger.TriggerPackFilterHandler

/**
 * @created 08.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
@Suppress("unused")
class Bot(token: String
		  , allowedUpdates: AllowedUpdates? = null
		  , dropPending: Boolean = false
): TgService {

	private val triggers = TriggerHandlerPackPy()
	val filter = TriggerFilterHandlerPackPy(triggers)
	val commands = TgCommandHandler<PyMessage>()

	val api: TgApiObjFuture
	val defaultSendSettings: SendDefaults

	private val listener: QueuedService<Update>

	init {
		val handler = PyResponseHandler(this)
		api = TgApiObjFuture(token, handler)

		if (dropPending || allowedUpdates != null) {
			api.getUpdates(if (dropPending) Long.MAX_VALUE else 0, 0, 0, allowedUpdates).get()?.apply {
				if (!ok)
					throw IllegalStateException(with(error!!) { "$description ($errorCode)" })
			}
		}

		defaultSendSettings = api.defaultSendSettings

		listener = longPollQueued {
			api = TgApiObject(token, handler)
			updateProcessor = processor(filter)
		}

		triggers.onMessage(commands)
	}

	fun commands(initializer: TgCommandHandler<PyMessage>.() -> Unit) {
		commands.apply(initializer)
	}

	override fun start() {
		listener.start()
	}

	override fun stop() {
		listener.stop()
	}

	override fun join() {
		listener.join()
	}

	override fun run() {
		listener.run()
	}

	@Suppress("FunctionName") // Комфортный переезд для питонистов
	fun run_forever() = run()

	val on = Triggers()

	inner class Triggers {

		fun message(action: (PyMessage) -> Unit) {
			triggers.onMessage(TriggerPack2Lambda(action))
		}

		fun messages(action: (List<PyMessage>) -> Unit) {
			triggers.onMessage(action)
		}

		fun messageEdit(action: (PyMessage) -> Unit) {
			triggers.onMessageEdit(TriggerPack2Lambda(action))
		}

		fun messagesEdit(action: (List<PyMessage>) -> Unit) {
			triggers.onMessageEdit(action)
		}
		
		fun ownerChanged(action: (ChatMemberUpdated) -> Unit) {
			triggers.onOwnerChanged(TriggerPack2Lambda(action))
		}

		fun ownersChanged(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onOwnerChanged(action)
		}
		
		fun administratorChanged(action: (ChatMemberUpdated) -> Unit) {
			triggers.onAdministratorChanged(TriggerPack2Lambda(action))
		}

		fun administratorsChanged(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onAdministratorChanged(action)
		}

		fun administratorCanceled(action: (ChatMemberUpdated) -> Unit) {
			triggers.onAdministratorCanceled(TriggerPack2Lambda(action))
		}

		fun administratorsCanceled(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onAdministratorCanceled(action)
		}

		fun chatMemberRestricted(action: (ChatMemberUpdated) -> Unit) {
			triggers.onChatMemberRestricted(TriggerPack2Lambda(action))
		}

		fun chatMembersRestricted(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onChatMemberRestricted(action)
		}

		fun chatMemberLeft(action: (ChatMemberUpdated) -> Unit) {
			triggers.onChatMemberLeft(TriggerPack2Lambda(action))
		}

		fun chatMembersLeft(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onChatMemberLeft(action)
		}

		fun chatMemberBanned(action: (ChatMemberUpdated) -> Unit) {
			triggers.onChatMemberBanned(TriggerPack2Lambda(action))
		}

		fun chatMembersBanned(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onChatMemberBanned(action)
		}

		fun chatMemberNew(action: (ChatMemberUpdated) -> Unit) {
			triggers.onChatMemberNew(TriggerPack2Lambda(action))
		}

		fun chatMembersNew(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onChatMemberNew(action)
		}

		fun channelPost(action: (PyMessage) -> Unit) {
			triggers.onChannelPost(TriggerPack2Lambda(action))
		}

		fun channelPosts(action: (List<PyMessage>) -> Unit) {
			triggers.onChannelPost(action)
		}

		fun editedChannelPost(action: (PyMessage) -> Unit) {
			triggers.onEditedChannelPost(TriggerPack2Lambda(action))
		}

		fun editedChannelPosts(action: (List<PyMessage>) -> Unit) {
			triggers.onEditedChannelPost(action)
		}

		fun inlineQuery(action: (InlineQuery) -> Unit) {
			triggers.onInlineQuery(TriggerPack2Lambda(action))
		}

		fun inlineQueries(action: (List<InlineQuery>) -> Unit) {
			triggers.onInlineQuery(action)
		}

		fun chosenInlineResult(action: (ChosenInlineResult) -> Unit) {
			triggers.onChosenInlineResult(TriggerPack2Lambda(action))
		}

		fun chosenInlineResults(action: (List<ChosenInlineResult>) -> Unit) {
			triggers.onChosenInlineResult(action)
		}

		fun callbackQuery(action: (CallbackQuery) -> Unit) {
			triggers.onCallbackQuery(TriggerPack2Lambda(action))
		}

		fun callbackQueries(action: (List<CallbackQuery>) -> Unit) {
			triggers.onCallbackQuery(action)
		}

		fun shippingQuery(action: (ShippingQuery) -> Unit) {
			triggers.onShippingQuery(TriggerPack2Lambda(action))
		}

		fun shippingQueries(action: (List<ShippingQuery>) -> Unit) {
			triggers.onShippingQuery(action)
		}

		fun preCheckoutQuery(action: (PreCheckoutQuery) -> Unit) {
			triggers.onPreCheckoutQuery(TriggerPack2Lambda(action))
		}

		fun preCheckoutQueries(action: (List<PreCheckoutQuery>) -> Unit) {
			triggers.onPreCheckoutQuery(action)
		}

		fun poll(action: (Poll) -> Unit) {
			triggers.onPoll(TriggerPack2Lambda(action))
		}

		fun polls(action: (List<Poll>) -> Unit) {
			triggers.onPoll(action)
		}

		fun pollAnswer(action: (PollAnswer) -> Unit) {
			triggers.onPollAnswer(TriggerPack2Lambda(action))
		}

		fun pollAnswers(action: (List<PollAnswer>) -> Unit) {
			triggers.onPollAnswer(action)
		}

		fun myChatMember(action: (ChatMemberUpdated) -> Unit) {
			triggers.onMyChatMember(TriggerPack2Lambda(action))
		}

		fun myChatMembers(action: (List<ChatMemberUpdated>) -> Unit) {
			triggers.onMyChatMember(action)
		}

		fun chatJoinRequest(action: (ChatJoinRequest) -> Unit) {
			triggers.onChatJoinRequest(TriggerPack2Lambda(action))
		}

		fun chatJoinRequests(action: (List<ChatJoinRequest>) -> Unit) {
			triggers.onChatJoinRequest(action)
		}

		fun newChatMember(action: (PyMessage) -> Unit) {
			triggers.onNewChatMember(TriggerPack2Lambda(action))
		}

		fun newChatMembers(action: (List<PyMessage>) -> Unit) {
			triggers.onNewChatMember(action)
		}

		fun leftChatMember(action: (PyMessage) -> Unit) {
			triggers.onLeftChatMember(TriggerPack2Lambda(action))
		}

		fun leftChatMembers(action: (List<PyMessage>) -> Unit) {
			triggers.onLeftChatMember(action)
		}

		fun newChatTitle(action: (PyMessage) -> Unit) {
			triggers.onNewChatTitle(TriggerPack2Lambda(action))
		}

		fun newChatTitles(action: (List<PyMessage>) -> Unit) {
			triggers.onNewChatTitle(action)
		}

		fun newChatPhoto(action: (PyMessage) -> Unit) {
			triggers.onNewChatPhoto(TriggerPack2Lambda(action))
		}

		fun newChatPhotos(action: (List<PyMessage>) -> Unit) {
			triggers.onNewChatPhoto(action)
		}

		fun deleteChatPhoto(action: (PyMessage) -> Unit) {
			triggers.onDeleteChatPhoto(TriggerPack2Lambda(action))
		}

		fun deleteChatPhotos(action: (List<PyMessage>) -> Unit) {
			triggers.onDeleteChatPhoto(action)
		}

		fun groupChatCreated(action: (PyMessage) -> Unit) {
			triggers.onGroupChatCreated(TriggerPack2Lambda(action))
		}

		fun groupChatsCreated(action: (List<PyMessage>) -> Unit) {
			triggers.onGroupChatCreated(action)
		}

		fun supergroupChatCreated(action: (PyMessage) -> Unit) {
			triggers.onSupergroupChatCreated(TriggerPack2Lambda(action))
		}

		fun supergroupChatsCreated(action: (List<PyMessage>) -> Unit) {
			triggers.onSupergroupChatCreated(action)
		}

		fun channelChatCreated(action: (PyMessage) -> Unit) {
			triggers.onChannelChatCreated(TriggerPack2Lambda(action))
		}

		fun channelChatsCreated(action: (List<PyMessage>) -> Unit) {
			triggers.onChannelChatCreated(action)
		}

		fun messageAutoDeleteTimerChanged(action: (PyMessage) -> Unit) {
			triggers.onMessageAutoDeleteTimerChanged(TriggerPack2Lambda(action))
		}

		fun messageAutoDeleteTimersChanged(action: (List<PyMessage>) -> Unit) {
			triggers.onMessageAutoDeleteTimerChanged(action)
		}

		fun migrateToChatId(action: (PyMessage) -> Unit) {
			triggers.onMigrateToChatId(TriggerPack2Lambda(action))
		}

		fun migrateToChatsId(action: (List<PyMessage>) -> Unit) {
			triggers.onMigrateToChatId(action)
		}

		fun migrateFromChatId(action: (PyMessage) -> Unit) {
			triggers.onMigrateFromChatId(TriggerPack2Lambda(action))
		}

		fun migrateFromChatsId(action: (List<PyMessage>) -> Unit) {
			triggers.onMigrateFromChatId(action)
		}

		fun pinnedMessage(action: (PyMessage) -> Unit) {
			triggers.onPinnedMessage(TriggerPack2Lambda(action))
		}

		fun pinnedMessages(action: (List<PyMessage>) -> Unit) {
			triggers.onPinnedMessage(action)
		}

		fun invoice(action: (PyMessage) -> Unit) {
			triggers.onInvoice(TriggerPack2Lambda(action))
		}

		fun invoices(action: (List<PyMessage>) -> Unit) {
			triggers.onInvoice(action)
		}

		fun successfulPayment(action: (PyMessage) -> Unit) {
			triggers.onSuccessfulPayment(TriggerPack2Lambda(action))
		}

		fun successfulPayments(action: (List<PyMessage>) -> Unit) {
			triggers.onSuccessfulPayment(action)
		}

		fun connectedWebsite(action: (PyMessage) -> Unit) {
			triggers.onConnectedWebsite(TriggerPack2Lambda(action))
		}

		fun connectedWebsites(action: (List<PyMessage>) -> Unit) {
			triggers.onConnectedWebsite(action)
		}

		fun passportData(action: (PyMessage) -> Unit) {
			triggers.onPassportData(TriggerPack2Lambda(action))
		}

		fun passportsData(action: (List<PyMessage>) -> Unit) {
			triggers.onPassportData(action)
		}

		fun proximityAlertTriggered(action: (PyMessage) -> Unit) {
			triggers.onProximityAlertTriggered(TriggerPack2Lambda(action))
		}

		fun proximityAlertsTriggered(action: (List<PyMessage>) -> Unit) {
			triggers.onProximityAlertTriggered(action)
		}

		fun voiceChatScheduled(action: (PyMessage) -> Unit) {
			triggers.onVoiceChatScheduled(TriggerPack2Lambda(action))
		}

		fun voiceChatsScheduled(action: (List<PyMessage>) -> Unit) {
			triggers.onVoiceChatScheduled(action)
		}

		fun voiceChatStarted(action: (PyMessage) -> Unit) {
			triggers.onVoiceChatStarted(TriggerPack2Lambda(action))
		}

		fun voiceChatsStarted(action: (List<PyMessage>) -> Unit) {
			triggers.onVoiceChatStarted(action)
		}

		fun voiceChatEnded(action: (PyMessage) -> Unit) {
			triggers.onVoiceChatEnded(TriggerPack2Lambda(action))
		}

		fun voiceChatsEnded(action: (List<PyMessage>) -> Unit) {
			triggers.onVoiceChatEnded(action)
		}

		fun voiceChatParticipantsInvited(action: (PyMessage) -> Unit) {
			triggers.onVoiceChatParticipantsInvited(TriggerPack2Lambda(action))
		}

		fun voiceChatsParticipantsInvited(action: (List<PyMessage>) -> Unit) {
			triggers.onVoiceChatParticipantsInvited(action)
		}

		fun unresolvedMessage(action: (PyMessage) -> Unit) {
			triggers.onUnresolvedMessage(TriggerPack2Lambda(action))
		}

		fun unresolvedMessages(action: (List<PyMessage>) -> Unit) {
			triggers.onUnresolvedMessage(action)
		}
	}
}