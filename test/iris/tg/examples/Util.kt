package iris.tg.examples

import iris.tg.api.TgApiObjFuture
import iris.tg.api.TgApiObject
import iris.tg.tgApi
import iris.tg.tgApiFuture
import java.io.File
import java.util.*
import java.util.logging.LogManager

/**
 * @created 28.09.2020
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */





object TestUtil {

	const val confPath = "excl/cfg.properties"
	lateinit var api: TgApiObject
	lateinit var apiFuture: TgApiObjFuture

	val properties = run {
		val props = Properties()
		File(confPath).reader().use { props.load(it) }
		props
	}

	fun init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"))

		val token = properties.getProperty("bot.token")
		api = tgApi(token)
		apiFuture = tgApiFuture(token)
		//initLogger()
	}

	private fun initLogger() {
		val ist = this.javaClass.getResourceAsStream("logger.properties").use {
			LogManager.getLogManager().readConfiguration(it)
		}
	}

	fun tgVsLocalTimeDiff(): Long {
		val toId = properties.getProperty("checkTimeDiffToId")?.toLong() ?: throw IllegalArgumentException("\"checkTimeDiffToId\" property is not defined")
		val startTime = System.currentTimeMillis()
		val firstText = "<b>Current local server time</b>: " + Date(startTime)
		val response = api.sendMessage(toId, firstText, parse_mode = "HTML")
		val responseTime =  System.currentTimeMillis()
		val result = response.result ?: throw IllegalArgumentException("Error: $response")
		val tgVsLocal = (result.date*1000 - responseTime)
		api.editMessageText(toId, result.messageId, firstText
			+ "\n<b>Response time</b>: " + (responseTime - startTime) + " ms"
			+ "\n<b>TG vs Local server time diff</b>: " + tgVsLocal + " ms"
			, parse_mode = "HTML"
		)
		return tgVsLocal
	}


}