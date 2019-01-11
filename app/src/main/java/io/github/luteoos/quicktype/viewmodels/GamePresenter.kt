package io.github.luteoos.quicktype.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.network.RestApi
import io.github.luteoos.quicktype.network.api.ApiAll
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class GamePresenter: BaseViewModel() {
    val LOG_IN_FAILED = "LOG_IN_FAILED"

    val wordsArray: MutableLiveData<Array<String>> = MutableLiveData()

    fun getWordsFromApi(){
        val client = RestApi.createService(ApiAll::class.java)
        disposable.add(client.getWordsArray()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.code()==200){
                    wordsArray.value = it.body()
                }
            },{
                message.value = LOG_IN_FAILED
            }))
    }
}