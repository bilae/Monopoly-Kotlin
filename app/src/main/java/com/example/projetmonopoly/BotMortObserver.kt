package com.example.projetmonopoly

import android.view.View
import GameObserver

class BotMortObserver(private val joueurs: List<Joueur>) : GameObserver {
    override fun onGameEvent(event: String) {
        if (event.startsWith("bot_mort_")) {
            val botIndex = event.removePrefix("bot_mort_").toIntOrNull()
            if (botIndex != null && botIndex in joueurs.indices) {
                val bot = joueurs[botIndex]
                bot.pion.image.visibility = View.INVISIBLE
            }
        }
    }
}


