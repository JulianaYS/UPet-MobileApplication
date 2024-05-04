package pe.edu.upc.upet.feature_auth.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @GET("users")
    fun get_users(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<List<UserResponse>>

    @POST("auth/sign-in")
    fun signIn(
        @Body token: SignInRequest
    ): Call<SignInResponse>

    @POST("auth/sign-up")
    fun signUp(
        @Body user: UserRequest
    ): Call<UserResponse>
}