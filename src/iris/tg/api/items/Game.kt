package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface Game {
	/** Title of the game */
	val title: String

	/** Description of the game */
	val description: String

	/** Photo that will be displayed in the game message in chats. */
	val photo: List<PhotoSize>

	/** Optional. Brief description of the game or high scores included in the game message. Can be automatically edited to include current high scores for the game when the botData calls setGameScore, or manually edited using editMessageText. 0-4096 characters. */
	val text: String?

	/** Optional. Special entities that appear in text, such as usernames, URLs, botData commands, etc. */
	val text_entities: List<MessageEntity>?

	/** Optional. Animation that will be displayed in the game message in chats. Upload via BotFather */
	val animation: Animation?
}