package io.github.luteoos.quicktype.views.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.RecyclerView.RVTopScores
import io.github.luteoos.quicktype.network.api.response.scoreApiResponse
import io.github.luteoos.quicktype.viewmodels.TopScoresPresenter
import kotlinx.android.synthetic.main.activity_top_scores.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.onClick

class TopScoresActivity : BaseActivityMVVM<TopScoresPresenter>() {

    override fun getLayoutID(): Int = R.layout.activity_top_scores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this)
        setBindings()
        viewModel.getTopScores()
    }

    override fun onVMMessage(msg: Int?) {
        when(msg){
            viewModel.GET_FAILED -> Toasty.error(ctx, R.string.api_error)
        }
    }

    private fun setBindings(){
        viewModel.scoresList.observe(this, Observer {
            if(it != null)
                setTopList(it)})
        btnHome2.onClick {
            onBackPressed()
        }
    }

    private fun setTopList(list: MutableList<scoreApiResponse>){
        rvTopScores.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = RVTopScores(list, context)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    rvTopScores?.invalidate()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }
}