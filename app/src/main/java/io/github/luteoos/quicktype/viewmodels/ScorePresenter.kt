package io.github.luteoos.quicktype.viewmodels

import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.network.RestApi
import io.github.luteoos.quicktype.network.api.ApiAll
import io.github.luteoos.quicktype.network.api.request.scoreUploadRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ScorePresenter : BaseViewModel() {

    val UPLOAD_SUCCESS = "UPLOAD_SUCCESS"
    val UPLOAD_FAILED = "UPLOAD_FAILED"

    fun postScore(nickname: String, score: Int){
        val client = RestApi.createService(ApiAll::class.java)
        disposable.add(client.uploadScore(scoreUploadRequest(nickname,score))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.code()==200)
                    message.value = UPLOAD_SUCCESS
            },{
                message.value = UPLOAD_FAILED
            }))
    }
}