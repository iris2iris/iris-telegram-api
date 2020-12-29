package iris.tg.webhook

class GroupSourceSimple(private val gb: GroupbotSource.Groupbot) : GroupbotSource {
	//override fun isGetByRequest() = false
	override fun getGroupbot(request: TgWebhookRequestHandler.Request) = gb
	//override fun getGroupbot(groupId: Int) = gb
}