package iris.tg.api.items

/**
 * @created 01.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface PassportData {

	/** Array with information about documents and other Telegram Passport elements that was shared with the botData */
	val data:	List<EncryptedPassportElement>

	/** Encrypted credentials required to decrypt the data */
	val credentials: EncryptedCredentials
}