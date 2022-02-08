package iris.tg

/**
 * @created 02.12.2019
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface TgReadable<T> {
	fun readAll(wait: Boolean = true): List<T>
	fun read(limit: Int = 0, wait: Boolean = true): List<T>
	fun read(dest: MutableList<T>, limit: Int = 0, wait: Boolean = true): Int
}