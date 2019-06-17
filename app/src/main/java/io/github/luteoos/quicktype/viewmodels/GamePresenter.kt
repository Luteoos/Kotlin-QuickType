package io.github.luteoos.quicktype.viewmodels

import android.arch.lifecycle.MutableLiveData
import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.network.RestApi
import io.github.luteoos.quicktype.network.api.ApiAll
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class GamePresenter: BaseViewModel() {
    val GET_WORDS_FAILED = 15

    val wordsArray: MutableLiveData<Array<String>> = MutableLiveData()

    fun getWordsFromApi(){
        wordsArray.value = arrayOf("mockknock")
//        TODO MOCKED
//        val client = RestApi.createService(ApiAll::class.java)
//        disposable.add(client.getWordsArray()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if(it.code()==200){
//                    wordsArray.value = it.body()
//                }
//            },{
//                message.value = GET_WORDS_FAILED
//            }))
    }
}