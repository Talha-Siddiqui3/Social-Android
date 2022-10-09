package com.social.social.adapters

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.social.social.R
import com.social.social.makeGone
import com.social.social.makeInvisible
import com.social.social.makeVisible
import com.social.social.models.ConversationModel
import java.util.*
import kotlin.collections.ArrayList

class ConversationListRVAdapter(
    private val context: Context,
    private val conversations: ArrayList<ConversationModel?>,
) : RecyclerView.Adapter<ConversationListRVAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(context, inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind((position + 1).toString())
        if (holder.itemViewType == 0) {
            (holder as MyViewHolder).bind(conversations[position])
        }
        //(holder as MyViewHolder).bind(list[position])
    }

    override fun getItemCount(): Int {
        return conversations.size
    }


    inner class MyViewHolder(
        private val context: Context,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.conversation_rv_layout, parent, false)) {


        private var conversationNameTextView: TextView? = null
        private var lastMessageTextView: TextView? = null
        private var dateTextView: TextView? = null
        private var chatHeadImageView: ImageView? = null
        private var isActiveImageView: ImageView? = null
        private var chatImageTextView: TextView? = null

        init {
            conversationNameTextView = itemView.findViewById(R.id.conversationNameTextView)
            lastMessageTextView = itemView.findViewById(R.id.lastMessageTextView)
            dateTextView = itemView.findViewById(R.id.conversationDateTextView)
            chatHeadImageView = itemView.findViewById(R.id.chatHeadImageView)
            isActiveImageView = itemView.findViewById(R.id.isActiveImageView)
            chatImageTextView = itemView.findViewById(R.id.imagePlaceHolderNameTextView)
        }

        fun bind(conversationModel: ConversationModel?) {
            if (conversationModel?.isActive == true) isActiveImageView?.makeVisible() else isActiveImageView?.makeInvisible()
            conversationNameTextView?.text = (conversationModel?.firstName ?: "") + " " + (conversationModel?.lastName ?: "")
            lastMessageTextView?.text = conversationModel?.lastSentMessage ?: ""
            dateTextView?.text = getDateString(conversationModel?.lastSentMessageDate)
            if(conversationModel?.recipientUserImage != null){
                chatHeadImageView?.let {
                    Glide.with(context).load(conversationModel.recipientUserImage).into(
                        it
                    )
                }
                chatImageTextView?.makeGone()
            }
            else{
                chatImageTextView?.text = (conversationModel?.firstName?.get(0)
                    ?: "").toString() + (conversationModel?.lastName?.get(0)
                    ?: "").toString()
                chatImageTextView?.makeVisible()
            }

        }

        private fun getDateString(date: Date?): String {
            if(date == null){
                return ""
            }
            return if (DateUtils.isToday(date.time)) "Today" else DateFormat.format(
                "dd/MM/yy",
                date
            ).toString()
        }

    }


}