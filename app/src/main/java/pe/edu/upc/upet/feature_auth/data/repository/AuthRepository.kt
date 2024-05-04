package pe.edu.upc.upet.feature_auth.data.repository

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import pe.edu.upc.upet.feature_auth.data.remote.AuthService
import pe.edu.upc.upet.feature_auth.data.remote.AuthServiceFactory
import pe.edu.upc.upet.feature_auth.data.remote.SignInRequest
import pe.edu.upc.upet.feature_auth.data.remote.SignInResponse
import pe.edu.upc.upet.feature_auth.data.remote.UserRequest
import pe.edu.upc.upet.feature_auth.data.remote.UserResponse
import pe.edu.upc.upet.utils.TokenManager

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class AuthRepository(private val authService: AuthService = AuthServiceFactory.getAuthService()) {

    fun get_users(username: String, password: String) {
        val signIn = authService.get_users(username, password)
        signIn.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
            }
        })
    }

    fun signIn(context: Context, username: String, password: String) {
        val signInCall = authService.signIn(SignInRequest(username, password))
        signInCall.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                TokenManager.clearToken(context)
                if (response.isSuccessful) {
                    val userResponse = response.body() as SignInResponse
                    TokenManager.saveToken(context, userResponse.access_token)
                    val token = TokenManager.getToken(context)
                    Log.d("tag", "${token}")
                }else {
                    Log.d("tag", "${response.body()} + error")
                    TokenManager.clearToken(context)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }

            }
        })
    }



    fun signUp(userRequest: UserRequest, callback: (UserResponse) -> Unit) {
        val signUp = authService.signUp(userRequest)
        signUp.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body() as UserResponse
                    callback(userResponse)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
            }
        })
    }

}

