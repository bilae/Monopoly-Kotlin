package com.example.projetmonopoly

import android.view.View
import GameObserver
import android.widget.TextView

class BotMortObserver(private val joueurs: MutableList<Joueur>, private val botViews: MutableList<TextView>) : GameObserver {
    override fun onGameEvent(event: String) {
        if (event.startsWith("bot_mort_")) {
            val botIndex = event.removePrefix("bot_mort_").toIntOrNull() //Selection de l'index du bot mort
            if (botIndex != null && botIndex in joueurs.indices) {
                val bot = joueurs[botIndex]
                bot.argent = -100 //défini l'argent du bot en négatif
                //Retire les Textviews et ImageView du bot et de son pion
                bot.pion.image.visibility = View.INVISIBLE
                botViews[botIndex-1].visibility = TextView.INVISIBLE
            }
        }
    }
}


