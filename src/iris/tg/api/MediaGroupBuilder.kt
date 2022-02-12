package iris.tg.api

import iris.tg.api.items.InputMedia
import iris.tg.api.items.MessageEntity
import iris.tg.connection.Connection
import iris.tg.pojo.items.InputMediaAnimation_Pojo
import iris.tg.pojo.items.InputMediaDocument_Pojo
import iris.tg.pojo.items.InputMediaPhoto_Pojo
import iris.tg.pojo.items.InputMediaVideo_Pojo
import java.io.File

/**
 * @created 11.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */
class MediaGroupBuilder {
	private val items = mutableListOf<InputMedia>()

	fun animation(media: String? = null, caption: String? = null, parse_mode: String? = null
				  , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
				  , thumb: String? = null, thumbData: Connection.BinaryData? = null
				  , width: Int, height: Int, duration: Int) {
		items += InputMediaAnimation_Pojo(media, caption, parse_mode, caption_entities, data, thumb, thumbData, width, height, duration)
	}
	fun video(media: String? = null, caption: String? = null, parse_mode: String? = null
		  , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
		  , thumb: String? = null, thumbData: Connection.BinaryData? = null
		  , width: Int = 0, height: Int = 0, duration: Int = 0
		  , supports_streaming: Boolean? = null
	) {
		items += InputMediaVideo_Pojo(media, caption, parse_mode, caption_entities, data, thumb, thumbData, width, height, duration, supports_streaming)
	}
	fun document(media: String? = null, caption: String? = null, parse_mode: String? = null
		 , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
		 , thumb: String? = null, thumbData: Connection.BinaryData? = null
		 , disable_content_type_detection: Boolean = false
	) {
		items += InputMediaDocument_Pojo(media, caption, parse_mode, caption_entities, data, thumb, thumbData, disable_content_type_detection)
	}
	fun photo(media: String? = null, caption: String? = null, parse_mode: String? = null
		, caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
	) {
		items += InputMediaPhoto_Pojo(media, caption, parse_mode, caption_entities, data)
	}

	fun other(item: InputMedia) {
		items += item
	}

	fun binary(bytes: ByteArray) : Connection.BinaryData {
		return Connection.BinaryDataByteArray(bytes)
	}

	fun binary(file: String) : Connection.BinaryData {
		return binary(File(file))
	}

	fun binary(file: File) : Connection.BinaryData {
		return Connection.BinaryDataFile(file)
	}

	fun build(initializer: MediaGroupBuilder.() -> Unit): List<InputMedia> {
		return MediaGroupBuilder().apply(initializer).items
	}
}