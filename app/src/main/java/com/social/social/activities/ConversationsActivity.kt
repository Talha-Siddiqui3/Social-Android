package com.social.social.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.adapters.ConversationListRVAdapter
import com.social.social.helper.Resource
import com.social.social.misc.MyBaseClass
import com.social.social.models.ConversationModel
import com.social.social.models.UserModel
import com.social.social.models.UserObject
import com.social.social.viewModels.ConversationsViewModel
import kotlinx.android.synthetic.main.activity_conversations.*
import okhttp3.internal.notify

class ConversationsActivity : MyBaseClass() {

    private val viewModel: ConversationsViewModel by viewModels {
        DependencyInjectorUtility.getConversationsViewModelFactory()
    }

    private var conversationList: ArrayList<ConversationModel?> = arrayListOf()
    private var conversationRvAdapter: ConversationListRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversations)
        addCommonViews(root, this)
        initRvAdaptor()
        addOnServerResponseObserver()
        getConversations()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        newChatImageView.setOnClickListener{
            startActivity(Intent(this, UserContactsActivity::class.java))
        }
    }

    private fun getConversations() {
        viewModel.getConversations(UserObject.id)
    }

    private fun initRvAdaptor() {
        conversationRvAdapter = ConversationListRVAdapter(this, conversationList)
        conversationRv?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        conversationRv?.adapter = conversationRvAdapter
    }

    private fun addOnServerResponseObserver() {
        viewModel.getOnServerResponseLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    conversationList.clear()
                    conversationList.addAll(it.data!!)
                    conversationRvAdapter?.notifyDataSetChanged()
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Error -> {
                    hideLoading()
                    it.message?.let { showCustomError(it) } ?: run { showUnknownError() }
                    Log.e("error", it.message ?: "")
                }
            }
        }
    }
}