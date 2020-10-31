@file:Suppress("unused")

package iris.tg

import iris.tg.event.*

/**
 * @created 20.09.2019
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
interface TgEventFilter {
	fun filterInvites(invites: List<ChatEvent>): List<ChatEvent>
	fun filterLeaves(leaves: List<ChatEvent>): List<ChatEvent>
	fun filterMessages(messages: List<Message>): List<Message>
	fun filterTitleUpdates(updaters: List<TitleUpdate>): List<TitleUpdate>
	fun filterCallbacks(callbacks: List<CallbackEvent>): List<CallbackEvent>
}

