package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface VoiceChatParticipantsInvited {
	/** Optional. New members that were invited to the voice chat */
	val users: List<User>
}