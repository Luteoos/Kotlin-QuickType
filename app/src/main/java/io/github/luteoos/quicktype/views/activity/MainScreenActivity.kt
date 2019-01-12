package io.github.luteoos.quicktype.views.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.viewmodels.MainScreenPresenter
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainScreenActivity: BaseActivityMVVM<MainScreenPresenter>() {
    override fun getLayoutID(): Int = R.layout.activity_main_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainScreenPresenter()
        this.connectToVMMessage()
        setBindings()
    }

    override fun onVMMessage(msg: String?) {
    }

    private fun createDialogAbout(){
        val textView = TextView(this)
       AlertDialog.Builder(this, R.style.DialogTheme)
            .setTitle(R.string.title_about)
            .setCancelable(true)
            .setView(textView.apply {
                setText(R.string.text_about)
                setPadding(16,16,16,16)
            })
            .setPositiveButton(R.string.ok){dialog,_ ->
                dialog.cancel()
            }
            .show()
    }

    private fun createDialogHelp(){
        val textView = TextView(this)
        AlertDialog.Builder(this, R.style.DialogTheme)
            .setTitle(R.string.title_help)
            .setCancelable(true)
            .setView(textView.apply {
                setText(R.string.text_help)

                setPadding(16,16,16,16)
            })
            .setPositiveButton(R.string.ok){dialog,_ ->
                dialog.cancel()
            }
            .show()
    }

    private fun setBindings(){
        btnExit.onClick {
            onBackPressed()
        }
        btnAbout.onClick {
            createDialogAbout()
        }
        btnHelp.onClick {
            createDialogHelp()
        }
        btnPlay.onClick {
            if(isNetworkOnLine)
                startGameActivity()
            else
                Toasty.error(ctx, R.string.api_error).show()
        }
        btnScore.onClick {
            if(isNetworkOnLine)
                startTopScoresActivity()
            else
                Toasty.error(ctx, R.string.api_error).show()
        }
    }

    private fun startGameActivity(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    private fun startTopScoresActivity(){
        val intent = Intent(this, TopScoresActivity::class.java)
        startActivity(intent)
    }
}