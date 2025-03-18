package com.example.projetmonopoly

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerBots: Spinner = findViewById(R.id.spinnerBots)
        val btnStartGame: Button = findViewById(R.id.btnStartGame)

        // Ici on choisit le nb de joueurs (des bots enft)
        val botOptions = arrayOf("1 Bot", "2 Bots", "3 Bots", "4 Bots")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, botOptions)
        spinnerBots.adapter = adapter

        btnStartGame.setOnClickListener {
            val selectedBots = spinnerBots.selectedItem.toString().split(" ")[0].toInt()

            // Transfer de l'info vers le Game Activity
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("NUM_BOTS", selectedBots)
            startActivity(intent)
        }
    }
}
