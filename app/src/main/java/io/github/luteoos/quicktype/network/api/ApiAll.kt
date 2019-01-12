package io.github.luteoos.quicktype.network.api

import io.github.luteoos.quicktype.network.api.request.scoreUploadRequest
import io.github.luteoos.quicktype.network.api.response.scoreApiResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiAll {

    @GET("getwords")
    fun getWordsArray() : Single<Response<Array<String>>>

    @GET("getword")
    fun getWordSingle() : Single<Response<String>>

    @GET("top10")
    fun getTop10() : Single<Response<MutableList<scoreApiResponse>>>

    @POST("result")
    fun uploadScore(@Body score: scoreUploadRequest) : Single<Response<Unit>>
}