package io.github.luteoos.quicktype.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.network.RestApi
import io.github.luteoos.quicktype.network.api.ApiAll
import io.github.luteoos.quicktype.network.api.request.scoreUploadRequest
import io.github.luteoos.quicktype.network.api.response.scoreApiResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TopScoresPresenter : BaseViewModel() {

    val GET_FAILED = "GET_FAILED"

    val scoresList : MutableLiveData<MutableList<scoreApiResponse>> = MutableLiveData()

    fun getTopScores(){
        val client = RestApi.createService(ApiAll::class.java)
        disposable.add(client.getTop10()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.code()==200){
                    if(it.body() != null)
                        scoresList.value = it.body()
                }else
                    message.value = GET_FAILED
            },{
                message.value = GET_FAILED
            }))
    }
}