package iris.tg.test

import iris.tg.api.JsonifierSimple
import iris.connection.Connection
import iris.tg.pojo.items.InputMediaDocument_Pojo
import iris.tg.pojo.items.InputMediaPhoto_Pojo
import java.io.File

/**
 * @created 07.02.2022
 * @author [Ivan Ivanov](https://vk.com/irisism)
 */
fun main() {
	val simpleJsonifier = JsonifierSimple()

	val input = listOf(
		InputMediaDocument_Pojo(data = Connection.BinaryDataFile(File("D:/test.png")), caption = "Это документик"),
		InputMediaPhoto_Pojo(data = Connection.BinaryDataFile(File("D:/test.png")), caption = "Это фото")
	)

	val res = simpleJsonifier.inputMedia(input)
	println(res)

}



