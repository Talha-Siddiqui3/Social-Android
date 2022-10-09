package com.social.social.models

import com.social.social.models.responseModels.ConversationResponseModel
import java.util.*

class ConversationModel(
    val firstName: String?,
    val lastName:String?,
    val recipientUserID: String?,
    val recipientUserImage: String?,
    val lastSentMessage: String?,
    val lastSentMessageUserID: String?,
    val lastSentMessageDate: Date?,
    val isActive: Boolean = false
) {

//    var name: String? = null
//    var recipientUserID: String? = null
//    var recipientUserImage: String? = null
//    var lastSentMessage: String? = null
//    var lastSentMessageUserID: String? = null
//    var lastSentMessageDate: Date? = null
//    var isActive: Boolean = false


//    constructor(conversationResponseModel: ConversationResponseModel) : this() {
//        name = conversationResponseModel.name
//        recipientUserID = conversationResponseModel.recipientUserID
//        recipientUserImage = conversationResponseModel.recipientUserImage
//        lastSentMessage = conversationResponseModel.lastSentMessage
//        lastSentMessageUserID = conversationResponseModel.lastSentMessageUserID
//        lastSentMessageDate = conversationResponseModel.lastSentMessageDate
//    }


}