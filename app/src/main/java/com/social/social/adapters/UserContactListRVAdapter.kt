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
import com.social.social.models.UserContactsModel
import java.util.*
import kotlin.collections.ArrayList

class UserContactListRVAdapter(
    private val context: Context,
    private val userContacts: ArrayList<UserContactsModel?>,
) : RecyclerView.Adapter<UserContactListRVAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(context, inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (holder.itemViewType == 0) {
            holder.bind(userContacts[position])
        }
    }

    override fun getItemCount(): Int {
        return userContacts.size
    }


    inner class MyViewHolder(
        private val context: Context,
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(inflater.inflate(R.layout.contacts_rv_layout, parent, false)) {


        private var contactNameTextView: TextView? = null
        private var contactNumberTextView: TextView? = null
        private var chatHeadImageView: ImageView? = null
        private var chatImageTextView: TextView? = null

        init {
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView)
            contactNumberTextView = itemView.findViewById(R.id.contactNumberTextView)
            chatHeadImageView = itemView.findViewById(R.id.chatHeadImageView)
            chatImageTextView = itemView.findViewById(R.id.imagePlaceHolderNameTextView)
        }

        fun bind(userContactsModel: UserContactsModel?) {
            contactNameTextView?.text =
                (userContactsModel?.contactFirstName
                    ?: "") + " " + (userContactsModel?.contactLastName
                    ?: "")
            contactNumberTextView?.text = userContactsModel?.contactNumber ?: ""
            if (userContactsModel?.contactUserImage != null) {
                chatHeadImageView?.let {
                    Glide.with(context).load(userContactsModel.contactUserImage).into(
                        it
                    )
                }
                chatImageTextView?.makeGone()
            } else {
                chatImageTextView?.text = (userContactsModel?.contactFirstName?.get(0)
                    ?: "").toString() + (userContactsModel?.contactLastName?.get(0)
                    ?: "").toString()
                chatImageTextView?.makeVisible()
            }

        }

    }


}