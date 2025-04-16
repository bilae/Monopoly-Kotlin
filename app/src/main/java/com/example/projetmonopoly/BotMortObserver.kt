package com.example.projetmonopoly

import android.view.View
import GameObserver
import android.widget.TextView

class BotMortObserver(private val joueurs: MutableList<Joueur>, private val botViews: MutableList<TextView>) : GameObserver {
    override fun onGameEvent(event: String) {
        if (event.startsWith("bot_mort_")) {
            val botIndex = event.removePrefix("bot_mort_").toIntOrNull()
            if (botIndex != null && botIndex in joueurs.indices) {
                val bot = joueurs[botIndex]
                bot.argent = -1
                bot.pion.image.visibility = View.INVISIBLE
                botViews[botIndex-1].visibility = TextView.INVISIBLE
            }
        }
    }
}


