package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Chat {
	val id: Long
	val type: String
	val title: String?
	val username: String?
	val firstName: String?
	val lastName: String?
	val photo: ChatPhoto?
	val bio: String?
	val hasPrivateForwards: Boolean
	val description: String?
	val inviteLink: String?
	val pinnedMessage: Message?
	val permissions: ChatPermissions?
	val slowModeDelay: Int
	val messageAutoDeleteTime: Int
	val hasProtectedContent: Boolean
	val stickerSetName: String?
	val canSetStickerSet: Boolean
	val linkedChatId: Long
	val location: ChatLocation?
}