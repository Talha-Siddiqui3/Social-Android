package com.social.social.repositories


import com.social.social.helper.Resource
import com.social.social.repositoriesInterfaces.AuthenticationRepositoryInterface
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.TimeUnit

class AuthenticationRepository : AuthenticationRepositoryInterface {

    override suspend fun loginUsingPhone(phoneNumber: String): Resource<String> {
        return signInUsingAmplify(phoneNumber)
    }

    private suspend fun signInUsingAmplify(phoneNumber: String): Resource<String> =
        suspendCancellableCoroutine { coroutine ->
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber, // Phone number to verify
//                60, // Timeout duration
//                TimeUnit.SECONDS, // Unit of timeout
//                this, // Activity (for callback binding)
//                mCallbacks
//            )
        }
}