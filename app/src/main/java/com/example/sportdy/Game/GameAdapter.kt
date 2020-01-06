package com.example.sportdy.Game

import android.content.Context
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportdy.Database.SportGame
import com.example.sportdy.R
import java.text.SimpleDateFormat
import kotlin.random.Random

class GameAdapter internal constructor(context: Context, onGameClickListener: OnGameClickListener) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sportGames = emptyList<SportGame>()
    private val monGameClickListener: OnGameClickListener = onGameClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = inflater.inflate(R.layout.recycler_view_game, parent, false)
        return GameViewHolder(itemView, monGameClickListener)
    }

    override fun getItemCount(): Int {
        return sportGames.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val sportGameRec: SportGame = sportGames.get(position)
        holder.tvGameType.text = sportGameRec.gameType
        holder.tvGameDate.text = SimpleDateFormat("EEE, DD MMM YYYY").format(sportGameRec.gameDate)
        var time = sportGameRec.gameTime
        var gameTime : String = ""
        if (time/60 > 12) {
            gameTime = String.format("%02d:%02d PM", time/60 - 12, time%60)
        }
        else {
            gameTime = String.format("%02d:%02d AM", time/60, time%60)
        }
        holder.tvGameTime.text = gameTime
        holder.tvLocation.text = sportGameRec.location + ", " + sportGameRec.state
        holder.tvHosterName.text = sportGameRec.hosterName
        holder.ivGameType.setImageResource(getGameTypeImage(sportGameRec.gameType))
        holder.ivHoster.setImageResource(getHosterImage())

    }

    fun setSportGame(sportGames: List<SportGame>) {
        this.sportGames = sportGames
        notifyDataSetChanged()
    }

    private fun getGameTypeImage(gameType: String): Int {
        return when (gameType) {
            "Basketball" -> R.drawable.basketball_icon
            "Bowling" -> R.drawable.bowling_icon
            "Cycling" -> R.drawable.cycling_icon
            "Workout" -> R.drawable.gym_icon
            "Jogging" -> R.drawable.jogging_icon
            "Soccer" -> R.drawable.soccer_icon
            else -> R.drawable.basketball_icon
        }
    }

    private fun getHosterImage(): Int {
        val random = Random.nextInt(6)
        return when (random) {
            0 -> R.drawable.long_hair_woman_icon
            1 -> R.drawable.mid_man_icon
            2 -> R.drawable.old_man_icon
            3 -> R.drawable.short_hair_woman_icon
            4 -> R.drawable.young_boy_icon
            else -> R.drawable.young_girl_icon
        }
    }

    inner class GameViewHolder(itemView: View, onGameClickListener: OnGameClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val itemHolderView: View = itemView
        val tvGameType: TextView = itemView.findViewById(R.id.tvGameType)
        val tvGameDate: TextView = itemView.findViewById(R.id.tvGameDate)
        val tvGameTime: TextView = itemView.findViewById(R.id.tvGameTime)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvHosterName: TextView = itemView.findViewById(R.id.tvHosterName)
        val ivGameType: ImageView = itemView.findViewById(R.id.ivGameType)
        val ivHoster: ImageView = itemView.findViewById(R.id.ivHoster)
        val btnMore: Button = itemView.findViewById(R.id.btnMore)

        private val onGameClickListener: OnGameClickListener = onGameClickListener

        init {
            itemHolderView.setOnClickListener(View.OnClickListener {
                onGameClickListener.onGameClick(adapterPosition)
                Log.i("FindGame", "Clicked ${adapterPosition}")
            })
            btnMore.setOnClickListener(View.OnClickListener {
                onGameClickListener.onGameClick(adapterPosition)
                Log.i("FindGame", "Clicked ${adapterPosition}")
            })
        }

        override fun onClick(itemView: View) {
            onGameClickListener.onGameClick(adapterPosition)
            Log.i("FindGame", "Clicked ${adapterPosition}")
        }
    }

    public interface OnGameClickListener {
        fun onGameClick(position: Int)
    }
}