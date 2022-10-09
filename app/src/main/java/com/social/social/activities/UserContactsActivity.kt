package com.social.social.activities

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.adapters.UserContactListRVAdapter
import com.social.social.helper.Resource
import com.social.social.misc.ErrorsAndMessages
import com.social.social.misc.MyBaseClass
import com.social.social.models.UserContactsModel
import com.social.social.models.UserObject
import com.social.social.printLog
import com.social.social.viewModels.UserContactsViewModel
import kotlinx.android.synthetic.main.activity_conversations.*
import kotlinx.android.synthetic.main.activity_conversations.root
import kotlinx.android.synthetic.main.activity_user_contacts.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


const val CONTACT_PERMISSION = 2

class UserContactsActivity : MyBaseClass() {

    private var userContactsList: ArrayList<UserContactsModel?> = arrayListOf()
    private var userContactsRvAdapter: UserContactListRVAdapter? = null
    private var contactsList = arrayListOf<String>()

    private val viewModel: UserContactsViewModel by viewModels {
        DependencyInjectorUtility.getUserContactsViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_contacts)
        addCommonViews(root, this)
        initRvAdaptor()
        checkForPermissions()
        addOnServerResponseObserver()

    }

    private fun addOnServerResponseObserver() {
        viewModel.getOnServerResponseLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    userContactsList.clear()
                    userContactsList.addAll(it.data!!)
                    userContactsRvAdapter?.notifyDataSetChanged()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(CONTACT_PERMISSION)
    private fun checkForPermissions() {
        val perms = arrayOf(Manifest.permission.READ_CONTACTS)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            getUserContacts()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, ErrorsAndMessages.contactPermissionRequiredMessage,
                CONTACT_PERMISSION, *perms
            )
        }
    }

    @SuppressLint("Range")
    private fun getUserContacts() {
        val cr = contentResolver
        val cur = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        if (cur?.count ?: 0 > 0) {
            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(id),
                        null
                    )
                    while (pCur!!.moveToNext()) {
                        val phoneNo = pCur!!.getString(
                            pCur!!.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                        )
                        contactsList.add(phoneNo)
                        "phonenumeber".printLog(phoneNo)
                    }
                    pCur!!.close()
                }
            }
        }
        cur?.close()
        viewModel.getUserContacts(UserObject.id, contactsList)
    }

    private fun initRvAdaptor() {
        userContactsRvAdapter = UserContactListRVAdapter(this, userContactsList)
        userContactsRv?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        conversationRv?.adapter = userContactsRvAdapter
    }
}