package iris.tg.longpoll

/**
 * @created 03.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
interface GetUpdateExceptionHandler<T> {
	fun handle(e: Throwable): Boolean
	fun nullUpdates(): Boolean
	fun notOk(errorItem: T): Boolean
}