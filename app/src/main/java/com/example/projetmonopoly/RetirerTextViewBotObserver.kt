package com.example.projetmonopoly

import GameObserver
import android.widget.TextView

class RetirerTextViewBotObserver(private val joueurs: MutableList<Joueur>, private val botViews: MutableList<TextView>) : GameObserver {
    override fun onGameEvent(event: String) {
        if (event.startsWith("bot_mort_")) {
            val botIndex = event.removePrefix("bot_mort_").toIntOrNull() //Selection de l'index du bot mort
            if (botIndex != null && botIndex in joueurs.indices) {
                val bot = joueurs[botIndex]
                botViews[botIndex-1].visibility = TextView.INVISIBLE
            }
        }
    }
}


