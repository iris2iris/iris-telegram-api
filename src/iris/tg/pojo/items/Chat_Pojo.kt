package iris.tg.pojo.items

import iris.tg.api.items.*

/**
 * @created 09.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
open class Chat_Pojo(
	override val id: Long,
	override val type: String,
	override val title: String? = null,
	override val username: String? = null,
	override val firstName: String? = null,
	override val lastName: String? = null,
	override val photo: ChatPhoto? = null,
	override val bio: String? = null,
	override val hasPrivateForwards: Boolean = false,
	override val description: String? = null,
	override val inviteLink: String? = null,
	override val pinnedMessage: Message? = null,
	override val permissions: ChatPermissions? = null,
	override val slowModeDelay: Int = 0,
	override val messageAutoDeleteTime: Int = 0,
	override val hasProtectedContent: Boolean = false,
	override val stickerSetName: String? = null,
	override val canSetStickerSet: Boolean = false,
	override val linkedChatId: Long = 0L,
	override val location: ChatLocation? = null,
): Chat