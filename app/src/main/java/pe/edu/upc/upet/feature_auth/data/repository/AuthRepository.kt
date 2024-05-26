package pe.edu.upc.upet.feature_auth.data.repository

import android.util.Log
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

    fun getUsers(username: String, password: String) {
        val signIn = authService.getUsers()
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

    fun signIn( username: String, password: String, callback: (Boolean) -> Unit) {
        val signInCall = authService.signIn(SignInRequest(username, password))
        signInCall.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>) {
                TokenManager.clearToken()
                if (response.isSuccessful) {
                    val userResponse = response.body() as SignInResponse
                    TokenManager.saveToken( userResponse.access_token)

                    callback(true)
                } else {
                    callback(false)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
                callback(false)
            }
        })
    }



    fun signUp(userRequest: UserRequest, callback: (Boolean) -> Unit) {
        val signUp = authService.signUp(userRequest)
        signUp.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(call: Call<SignInResponse>, response: Response<SignInResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.access_token
                    if (token != null) {
                        TokenManager.clearToken()
                        TokenManager.saveToken(token)
                    }
                    callback(true)
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                t.message?.let {
                    Log.d("AuthRepository", it)
                }
            }
        })
    }

    fun getUserById(userId: Int, callback: (UserResponse) -> Unit) {
        val getUserById = authService.getUserById(userId)
        getUserById.enqueue(object : Callback<UserResponse> {
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

