package ph.edu.auf.nuguid.marcbrian.rpggame

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ph.edu.auf.nuguid.marcbrian.rpggame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val hero = Hero("Hero", CharacterStats(hp = 25, def = 8))
        val enemy = Enemy("Goblin", CharacterStats(hp = 15, def = 5))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            heroName.text = hero.name
            enemyName.text = enemy.name
            enemyHp.text = enemy.characterStats.hp.toString()
            heroHp.text = hero.characterStats.hp.toString()

            attackButton.setOnClickListener {
                hero.attack(enemy)
                actionDialogue.text = "Hero attacked Goblin for ${hero.damageQueued} damage!"
            }

            defendButton.setOnClickListener {
                hero.defend()
                actionDialogue.text = "Hero Defended!"
            }

            healButton.setOnClickListener {
                val healResult = hero.heal()
                actionDialogue.text = "Hero healed for ${healResult.healing}!"
            }

            if (hero.characterStats.hp == 0){

                actionDialogue.text = "Enemy won"
            }else if (enemy.characterStats.hp == 0){
                actionDialogue.text = "Hero won"
            }

            makeRandomMove(enemy, hero)

        }

    }


    fun makeRandomMove(character: CharacterInterface, opponent: Character) {

        var move = listOf("attack", "defend", "heal").random()

        when(move){
            "attack" -> character.attack(opponent)
            "defend" -> character.defend()
            "heal" -> character.heal()
        }

    }
}