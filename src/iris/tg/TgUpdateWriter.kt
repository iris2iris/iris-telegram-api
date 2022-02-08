package iris.tg


interface TgUpdateWriter<T> {
	fun write(event: T)
	fun write(events: List<T>)
	fun write(events: Array<T>)
}