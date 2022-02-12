package iris.tg.test.api

import iris.tg.connection.Connection
import iris.tg.examples.TestUtil
import iris.tg.pojo.items.InputMediaDocument_Pojo
import iris.tg.pojo.items.InputMediaPhoto_Pojo
import iris.tg.tgApi
import java.io.File

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
fun main() {

	// Инициализируем тестовые данные. В рабочих проектах этого делать не нужно
	TestUtil.init()
	val properties = TestUtil.properties
	val token = properties.getProperty("bot.token")
	val toId = properties.getProperty("userTo.id").toLong()


	// Создаём объект TgApi с подготовленными по умолчанию настройками
	val api = tgApi(token)

	// Собираем медиа-группу через список
	val res = api.sendMediaGroup(toId,
		listOf(
			InputMediaPhoto_Pojo(data = Connection.BinaryDataFile(File("D:/test.png")), caption = "Это фото 1"),
			InputMediaPhoto_Pojo(data = Connection.BinaryDataFile(File("D:/test.png")), caption = "Это фото 2")
		)
	)
	println("Ответ: $res")

	// Собираем медиа-группу через лямбду-билдер
	val res2 = api.sendMediaGroup(toId) {
		photo(data = binary("D:/Downloads/photo_2022-02-04_18-19-03.jpg"), caption = "Да. Оно работает!")
		photo(data = binary("D:/Downloads/6EFyti31Ix4.jpg"), caption = "БН-Лайв")
		video(data = binary("D:/Downloads/IMG_6625.MOV"), caption = "Какой-то Леонид Волков")
	}

	println(res2)

}