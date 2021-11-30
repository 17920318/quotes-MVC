package dev.cardoso.quotesmvvm.data.remote

import dev.cardoso.quotesmvvm.data.model.LoginRequest
import dev.cardoso.quotesmvvm.data.model.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class UserApiImpl @Inject constructor(retrofit: Retrofit):UserApi{
    private val apiService: UserApi = retrofit.create(UserApi::class.java)
    override suspend fun loginUser(requestLogin: LoginRequest): Response<LoginResponse> {
      return  apiService.loginUser(requestLogin)
    }
}