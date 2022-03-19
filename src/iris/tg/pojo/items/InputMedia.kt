package iris.tg.pojo.items

import iris.tg.api.items.*
import iris.connection.Connection

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

open class InputMedia_Pojo(
	override val type: String,
	override var media: String?,
	override val caption: String?,
	override val parse_mode: String?,
	override val caption_entities: List<MessageEntity>?,
	override val data: Connection.BinaryData?
): InputMedia

open class InputMediaThumbable_Pojo(type: String, media: String? = null, caption: String? = null, parse_mode: String? = null
	, caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
	
	, override var thumb: String?, override val thumbData: Connection.BinaryData? = null
) : InputMedia_Pojo(type, media, caption, parse_mode, caption_entities, data)
	, InputMediaThumbable

open class InputMediaAudio_Pojo(type: String, media: String? = null, caption: String? = null, parse_mode: String? = null
	, caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
	, thumb: String?, thumbData: Connection.BinaryData? = null
								
	, override val duration: Int = 0
	, override val performer: String? = null
	, override val title: String? = null
) : InputMediaThumbable_Pojo(type, media, caption, parse_mode, caption_entities, data, thumb, thumbData)
	, InputMediaAudio

class InputMediaDocument_Pojo(media: String? = null, caption: String? = null, parse_mode: String? = null
   , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
   , thumb: String? = null, thumbData: Connection.BinaryData? = null

   , override val disable_content_type_detection: Boolean = false
) : InputMediaThumbable_Pojo("document", media, caption, parse_mode, caption_entities, data, thumb, thumbData)
	, InputMediaDocument

class InputMediaPhoto_Pojo(media: String? = null, caption: String? = null, parse_mode: String? = null
						  , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
) : InputMedia_Pojo("photo", media, caption, parse_mode, caption_entities, data)
	, InputMediaPhoto


class InputMediaAnimation_Pojo(media: String? = null, caption: String? = null, parse_mode: String? = null
	, caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
	, thumb: String? = null, thumbData: Connection.BinaryData? = null
	, width: Int = 0, height: Int = 0, duration: Int = 0
) : InputMediaAnimatable_Pojo("animation", media, caption, parse_mode, caption_entities, data, thumb, thumbData, width, height, duration)
	, InputMediaAnimation

open class InputMediaAnimatable_Pojo(type: String, media: String? = null, caption: String? = null, parse_mode: String? = null
	 , caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
									 , thumb: String? = null, thumbData: Connection.BinaryData? = null
									 
	 , override val width: Int = 0
	 , override val height: Int = 0
	 , override val duration: Int = 0
) : InputMediaThumbable_Pojo(type, media, caption, parse_mode, caption_entities, data, thumb, thumbData)
	, InputMediaAnimation

open class InputMediaVideo_Pojo(media: String? = null, caption: String? = null, parse_mode: String? = null
	, caption_entities: List<MessageEntity>? = null, data: Connection.BinaryData? = null
	, thumb: String? = null, thumbData: Connection.BinaryData? = null
	, width: Int = 0, height: Int = 0, duration: Int = 0

	, override val supports_streaming: Boolean? = null
) : InputMediaAnimatable_Pojo("video", media, caption, parse_mode, caption_entities, data, thumb, thumbData, width, height, duration)
	, InputMediaVideo
