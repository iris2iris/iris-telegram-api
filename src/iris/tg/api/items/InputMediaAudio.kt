package iris.tg.api.items

import iris.tg.connection.Connection

/**
 * @created 10.02.2022
 * @author [Ivan Ivanov](https://t.me/irisism)
 */

interface InputMedia {
	/** Type of the result, must be photo */
	val type: String

	/** File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended), pass an HTTP URL for Telegram to get a file from the Internet, or pass “attach://<file_attach_name>” to upload a new one using multipart/form-data under <file_attach_name> name. More info on Sending Files » */
	var media: String?

	/** Optional. Caption of the photo to be sent, 0-1024 characters after entities parsing */
	val caption: String?

	/** Optional. Mode for parsing entities in the photo caption. See formatting options for more details. */
	val parse_mode: String?

	/** Optional. List of special entities that appear in the caption, which can be specified instead of parse_mode */
	val caption_entities: List<MessageEntity>?


	/** Iris-TG-Api specific field */
	val data: Connection.BinaryData?
}

interface InputMediaThumbable : InputMedia {
	/** Optional. Thumbnail of the file sent; can be ignored if thumbnail generation for the file is supported server-side. The thumbnail should be in JPEG format and less than 200 kB in size. A thumbnail's width and height should not exceed 320. Ignored if the file is not uploaded using multipart/form-data. Thumbnails can't be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>” if the thumbnail was uploaded using multipart/form-data under <file_attach_name>. More info on Sending Files » */
	var thumb: String?

	/** Iris-TG-Api specific field */
	val thumbData: Connection.BinaryData?
}

interface InputMediaAudio : InputMediaThumbable {

	/** Optional. Duration of the audio in seconds */
	val duration: Int

	/** Optional. Performer of the audio */
	val performer: String?

	/** Optional. Title of the audio */
	val title: String?
}

interface InputMediaDocument : InputMediaThumbable {
	/** Optional. Disables automatic server-side content type detection for files uploaded using multipart/form-data. Always True, if the document is sent as part of an album. */
	val disable_content_type_detection: Boolean
}

interface InputMediaPhoto : InputMedia {



}

interface InputMediaAnimation : InputMediaThumbable {
	/** Optional. Video width */
	val width: Int

	/** Optional. Video height */
	val height: Int

	/** Optional. Video duration in seconds */
	val duration: Int
}

interface InputMediaVideo : InputMediaAnimation {

	/** Optional. Pass True, if the uploaded video is suitable for streaming */
	val supports_streaming: Boolean?
}