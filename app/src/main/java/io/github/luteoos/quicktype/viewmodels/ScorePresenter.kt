package io.github.luteoos.quicktype.viewmodels

import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.network.RestApi
import io.github.luteoos.quicktype.network.api.ApiAll
import io.github.luteoos.quicktype.network.api.request.scoreUploadRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ScorePresenter : BaseViewModel() {

    val UPLOAD_SUCCESS = -1
    val UPLOAD_FAILED =  14

    fun postScore(nickname: String, score: Int){
        //TODO API isnt working so whatev
        val client = RestApi.createService(ApiAll::class.java)
        CompositeDisposable().add(client.uploadScore(scoreUploadRequest(nickname,score))
            .subscribeOn(Schedulers.io())
            .timeout(1500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.code()==200)
                  send( UPLOAD_SUCCESS)
            },{
                send(UPLOAD_FAILED)
            }))
    }
}