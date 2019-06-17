package io.github.luteoos.quicktype.views.activity

import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.hardware.input.InputManager
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
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
    private var time = 59
    private val timeHandler = Handler()
    private var isGameEnd = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this)
        this.connectToVMMessage()
        setBindings()
        viewModel.getWordsFromApi()
        tvTimer.text = time.toString()
        Session.reset()
    }

    override fun finish() {
        isGameEnd = true
        timeHandler.removeCallbacks(null)
        super.finish()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        finish()
    }
    override fun onVMMessage(msg: Int?) {
        when(msg){
            viewModel.GET_WORDS_FAILED -> {
                Toasty.error(ctx,R.string.api_error).show()
                finish()
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
               finish()
        })
    }

    private fun beginGame(){
        tvWord.text = words[steps]
        startTimer()
    }

    private fun startTimer(){
        timeHandler.postDelayed({updateTimer()}, 1000)
    }

    private fun updateTimer(){
        time--
        if(time > 0){
            tvTimer.text = time.toString()
            startTimer()
        }
        else
            if(!isGameEnd)
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

    private fun gameFinishedAnimation(){
        constrainLayoutGame.foreground = ColorDrawable(getColor(R.color.colorAccent))
        val animator = ObjectAnimator.ofInt(this.constrainLayoutGame.foreground,"alpha",0,255).apply {
            duration = 500
        }
        animator.start()
        Handler().postDelayed({startScoreDisplayActivity()},500)
    }

    private fun endGame(){
        isGameEnd = true
        timeHandler.removeCallbacks(null)
        Session.score += time
        this.hideKeyboard()
        gameFinishedAnimation()
    }

    private fun startScoreDisplayActivity(){
        val intent = Intent(this, ScoreActivity::class.java)
        startActivity(intent)
    }
}