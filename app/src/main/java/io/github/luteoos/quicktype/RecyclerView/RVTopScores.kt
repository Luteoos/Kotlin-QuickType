package io.github.luteoos.quicktype.RecyclerView

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.luteoos.quicktype.R
import io.github.luteoos.quicktype.R.id.parent
import io.github.luteoos.quicktype.network.api.response.scoreApiResponse
import kotlinx.android.synthetic.main.recycler_view_top_scores_holder.view.*
import org.jetbrains.anko.backgroundColor

class RVTopScores(val list: MutableList<scoreApiResponse>,
                  val context: Context) : RecyclerView.Adapter<RVTopScoresViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RVTopScoresViewHolder {
        return RVTopScoresViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_top_scores_holder, p0, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RVTopScoresViewHolder, position: Int) {
        holder.score.text = list[position].score.toString()
        holder.username.text = list[position].nickname
        when(position){
            0 -> holder.cardview.backgroundColor = context.getColor(R.color.gold)
            1 -> holder.cardview.backgroundColor = context.getColor(R.color.silver)
            else -> holder.cardview.backgroundColor = context.getColor(R.color.colorAfter)
        }
    }
}

class RVTopScoresViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val score = view.tvScoreInt
    val username = view.tvUsername
    val cardview = view.cardView
}