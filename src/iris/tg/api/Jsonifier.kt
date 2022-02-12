package iris.tg.api

import iris.tg.api.items.ChatPermissions
import iris.tg.api.items.InputMedia
import iris.tg.api.items.MessageEntity
import iris.tg.api.items.ReplyMarkup

interface Jsonifier {
	fun entities(entities: List<MessageEntity>): String
	fun replyMarkup(replyMarkup: ReplyMarkup): String
	fun chatPermissions(permissions: ChatPermissions): String
	fun array2JsonString(array: Array<String>): String
	fun array2JsonString(array: List<String>): String
	fun inputMedia(inputMedia: List<InputMedia>): String
}