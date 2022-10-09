package com.social.social.models.responseModels

import com.social.social.models.ConversationModel
import java.util.*

class ConversationResponseModel(
    val conversations: List<ConversationModel?>?,
    override val message: String?,
    override val success: Boolean,
    override val error: String?
) : BaseServerResponseModel() {
}