package pe.edu.upc.upet.utils

import android.content.Context

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts

object TokenManager {
    private const val PREF_NAME = "my_app_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"

    fun saveToken(context: Context, token: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ACCESS_TOKEN, token)
        return editor.commit()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.apply()
    }
    fun isUserAuthenticated(context: Context): Boolean {
        val token = getToken(context)
        return !token.isNullOrEmpty()
    }

    fun getUserIdFromToken(context: Context): String? {
        val token = getToken(context)
        val parts = token?.split(" ")
        return if (parts?.size == 2 && parts[0] == "Bearer") {
            parts[1]
        } else {
            null
        }
    }



}