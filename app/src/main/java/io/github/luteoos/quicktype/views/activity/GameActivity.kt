package io.github.luteoos.quicktype.views.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.utils.Session
import io.github.luteoos.quicktype.viewmodels.GamePresenter
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.textChangedListener

class GameActivity: BaseActivityMVVM<GamePresenter>() {

    override fun getLayoutID(): Int = R.layout.activity_game

    private var words: Array<String> = Array(0) {""}
    private var steps: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = GamePresenter()
        this.connectToVMMessage()
        setBindings()
        viewModel.getWordsFromApi()
    }

    override fun onBackPressed() {
        //todo rework to make sure keyboard is always on display, temporary solution
        finish()
    }

    override fun onVMMessage(msg: String?) {
        when(msg){
            viewModel.LOG_IN_FAILED -> {
                Toasty.error(ctx,R.string.api_error).show()
                onBackPressed()
            }
        }
    }

    private fun setBindings(){
        etAnswer.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if (steps < words.size){
                    if(p0.toString().toLowerCase() == words[steps])
                        addPointAndScroll()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        viewModel.wordsArray.observe(this, Observer{ array ->
            if(array != null){
                words = array
                beginGame()
            }else
                onBackPressed()
        })
    }

    private fun beginGame(){
        tvWord.text = words[steps]
        startTimer()
    }

    private fun startTimer(){
        Handler().postDelayed({updateTimer()}, 1000)
    }

    private fun updateTimer(){
        var time = tvTimer.text.toString().toInt()
        if(time-- >0){
            tvTimer.text = time.toString()
            startTimer()
        }
        else
            endGame()
    }

    fun addPointAndScroll(){
        Session.score++
        steps++
        etAnswer.text.clear()
        if(steps < words.size)
            tvWord.text = words[steps]
        else
            endGame()
    }

    private fun endGame(){
        Toasty.info(ctx, "Not Implemented yet!").show()
    }
}