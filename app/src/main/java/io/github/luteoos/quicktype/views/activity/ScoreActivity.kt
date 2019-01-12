package io.github.luteoos.quicktype.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.utils.Session
import io.github.luteoos.quicktype.viewmodels.ScorePresenter
import kotlinx.android.synthetic.main.activity_score.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.onClick

class ScoreActivity : BaseActivityMVVM<ScorePresenter>() {

    override fun getLayoutID(): Int = R.layout.activity_score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ScorePresenter()
        connectToVMMessage()
        tvScore.text = Session.score.toString()
        setBindings()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideKeyboard()
        startMainScreenActivity()
    }

    override fun onVMMessage(msg: String?) {
        when(msg){
            viewModel.UPLOAD_SUCCESS -> {
                etUsername.visibility = View.INVISIBLE
                tvUsername.visibility = View.INVISIBLE
                btnPostScore.visibility = View.INVISIBLE
            }
            viewModel.UPLOAD_FAILED -> Toasty.error(ctx, R.string.api_error)
        }
    }

    private fun setBindings(){
        btnHome.onClick {
            startMainScreenActivity()
        }
        btnPostScore.onClick {
            viewModel.postScore(etUsername.text.toString(), Session.score)
        }
    }

    private fun startMainScreenActivity(){
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}