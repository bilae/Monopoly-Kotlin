package com.example.projetmonopoly
import android.widget.ImageView

class Pion(var couleur : String,var xpos : Float, var ypos: Float, var prison : Boolean, var image : ImageView, var case : Int) {

    fun goto(x : Float,y: Float,Plateau : ImageView) {
        Plateau.post {
            image.animate() //Animation par chatgpt
                .x(x)
                .y(y)
                .setDuration(500) // Animation de 500ms
                .start()

            // Mettre à jour les positions réelles du pion
            xpos = x.toFloat()
            ypos = y.toFloat()
        }
    }
    }
