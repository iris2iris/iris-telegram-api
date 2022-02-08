package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface ProximityAlertTriggered {

	/** User that triggered the alert */
	val traveler: User

	/** User that set the alert */
	val watcher: User

	/** The distance between the users */
	val distance: Int

}