package com.social.social.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.social.social.DependencyInjectorUtility
import com.social.social.R
import com.social.social.dataObjects.FieldObject
import com.social.social.helper.Resource
import com.social.social.helper.ResourceValidation
import com.social.social.misc.ErrorsAndMessages
import com.social.social.misc.MyBaseClass
import com.social.social.models.UserInformationDataModel
import com.social.social.models.UserObject
import com.social.social.scrollToBottom
import com.social.social.showToastLong
import com.social.social.viewModels.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_user_info.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


enum class UserInformationFields {
    FirstName
}

class UserInfoActivity : MyBaseClass(), View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private val viewModel: UserInfoViewModel by viewModels {
        DependencyInjectorUtility.getUserInfoViewModelFactory()
    }

    private val userInfoFieldsToViewsMap = HashMap<UserInformationFields, EditText>()
    private lateinit var userInformationDataModel: UserInformationDataModel
    private var profilePictureFile: MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        addCommonViews(root, this)
        userInformationDataModel = UserInformationDataModel()
        setKeyboardListener()
        setOnClickListeners()
        initUserInfoFieldsMap()
        addOnValidationCompleteObserver()
        addOnServerResponseObserver()

    }


    private fun addOnServerResponseObserver() {
        viewModel.getOnServerResponseLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    hideLoading()
                    startActivity(Intent(this, ConversationsActivity::class.java))
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

    private fun initUserInfoFieldsMap() {
        userInfoFieldsToViewsMap[UserInformationFields.FirstName] = firstNameEditText
    }

    private fun setOnClickListeners() {
        saveButton.setOnClickListener(this)
        addImageButton.setOnClickListener(this)
    }

    private fun setKeyboardListener() {
        KeyboardVisibilityEvent.setEventListener(
            this,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    if (isOpen) {
                        rootScrollView.scrollToBottom()
                    }
                }
            })
    }

    private fun addOnValidationCompleteObserver() {
        viewModel.getValidationLiveData().observe(this) {
            if (it is ResourceValidation.Success) {
                saveUserInformation()
            } else {
                //Validation was not successful due to at least one field having incorrect data
                //We will now display errors on all the fields having incorrect data
                for (fieldObject in it.data) {
                    if (!fieldObject.valid) {
                        userInfoFieldsToViewsMap[fieldObject.fieldEnum]?.error =
                            fieldObject.error //using our hashmap to get our view from the enum
                    }
                }
            }
        }
    }

    private fun getAllFieldObjects(): ArrayList<FieldObject<UserInformationFields>> {
        val allFieldObjects = ArrayList<FieldObject<UserInformationFields>>()
        allFieldObjects.add(
            FieldObject(
                UserInformationFields.FirstName,
                firstNameEditText.text.toString()
            )
        )
        return allFieldObjects
    }

    private fun showPickImageDialog() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        galleryIntent.type = "image/*"
        val chooser = Intent.createChooser(galleryIntent, "Select Profile Picture")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        resultLauncher.launch(chooser)
    }

    private fun saveBitmapToFile(bitmap: Bitmap): File? {
        //create a file to write bitmap data
        var file: File? = null
        return try {
            file = File(
                this.filesDir.toString() + File.separator + UUID.randomUUID().toString()
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImage = result.data?.data

                try {
                    val bitmap = if (selectedImage == null) {
                        result.data?.extras?.get("data") as Bitmap?
                    } else {
                        MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                    }
                    avatar.setImageBitmap(bitmap)
                    bitmap?.let {
                        val avatarImageFile = saveBitmapToFile(it)
                        avatarImageFile?.let {
                            val reqFile: RequestBody =
                                avatarImageFile.asRequestBody("image/*".toMediaTypeOrNull())
                            profilePictureFile =
                                MultipartBody.Part.createFormData(
                                    "profilePictureFile",
                                    avatarImageFile.name,
                                    reqFile
                                )
                        }

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        showPickImageDialog()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        ErrorsAndMessages.imagePickPermissionsRequiredError.showToastLong(this)
    }

    private fun checkForPermissions() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            showPickImageDialog()
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                this, ErrorsAndMessages.imagePickPermissionsRequiredMessage,
                1, *perms
            );
        }
    }


    private fun saveUserInformation() {
        userInformationDataModel.id = UserObject.id
        userInformationDataModel.firstName = firstNameEditText.text.toString()
        userInformationDataModel.lastName = lastNameEditText.text.toString()
        viewModel.saveUserInformation(userInformationDataModel, profilePictureFile)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.addImageButton -> {
                checkForPermissions()
            }
            R.id.saveButton -> {
                viewModel.validateAllFields(getAllFieldObjects())
            }
        }
    }


}