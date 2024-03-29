package iris.tg

import iris.tg.event.CallbackEvent
import iris.tg.event.*

/**
 * @created 08.09.2019
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface TgEventHandler {
	fun processMessages(messages: List<Message>)
	fun processEditedMessages(messages: List<Message>)
	fun processInvites(invites:List<ChatEvent>)
	fun processLeaves(leaves:List<ChatEvent>)
	fun processTitleUpdates(updaters:List<TitleUpdate>)
	fun processPinUpdates(updaters:List<PinUpdate>)
	fun processCallbacks(callbacks: List<CallbackEvent>)
}