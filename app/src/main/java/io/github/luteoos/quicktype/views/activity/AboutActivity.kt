package io.github.luteoos.quicktype.views.activity

import android.os.Bundle
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.viewmodels.MainScreenPresenter
import kotlinx.android.synthetic.main.activity_about.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AboutActivity : BaseActivityMVVM<MainScreenPresenter>(){
    override fun getLayoutID(): Int = R.layout.activity_about

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this)
        btnHome2.onClick{
            onBackPressed()
        }
    }
}