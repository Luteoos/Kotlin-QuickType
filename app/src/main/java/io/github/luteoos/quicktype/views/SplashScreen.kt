package io.github.luteoos.quicktype.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.viewmodels.SplashScreenPresenter
import io.github.luteoos.quicktype.views.activity.MainScreenActivity

class SplashScreen: BaseActivityMVVM<BaseViewModel>() {
    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = SplashScreenPresenter()
        Handler().postDelayed({startMainScreenActivity()}, 600)
    }

    private fun startMainScreenActivity(){
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}