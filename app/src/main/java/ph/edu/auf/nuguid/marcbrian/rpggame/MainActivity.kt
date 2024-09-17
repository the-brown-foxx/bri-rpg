package ph.edu.auf.nuguid.marcbrian.rpggame

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import ph.edu.auf.nuguid.marcbrian.rpggame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val hero = Hero("Hero", CharacterStats(hp = 25, def = 8))
    private val enemy = Enemy("Goblin", CharacterStats(hp = 15, def = 5))
    private var gameOver = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }

            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

        with(binding) {
            heroName.text = hero.name
            heroHp.text = hero.characterStats.hp.toString()
            enemyName.text = enemy.name
            enemyHp.text = enemy.characterStats.hp.toString()

            attackButton.setOnClickListener {
                if (!gameOver) makeMove(AttackMove(enemy))
            }

            defendButton.setOnClickListener {
                if (!gameOver) makeMove(DefenseMove)
            }

            healButton.setOnClickListener {
                if (!gameOver) makeMove(HealMove)
            }
        }
    }


    private fun ActivityMainBinding.makeMove(move: Move) {
        val heroResult = hero.makeMove(move)
        heroResultText.updateFromResults(hero, heroResult)
        val enemyResult = enemy.makeRandomMove(hero)
        enemyResultText.updateFromResults(enemy, enemyResult)
        val heroDefense = hero.receiveDamage()
        heroResultText.updateFromResults(hero, heroDefense)
        val enemyDefense = enemy.receiveDamage()
        enemyResultText.updateFromResults(enemy, enemyDefense)

        heroHp.text = hero.characterStats.hp.toString()
        enemyHp.text = enemy.characterStats.hp.toString()

        if (hero.characterStats.hp <= 0) {
            heroName.text = "${hero.name} (Defeated)"
            enemyName.text = "${enemy.name} (Winner)"
            gameOver = true
        } else if (enemy.characterStats.hp <= 0) {
            heroName.text = "${hero.name} (Winner)"
            enemyName.text = "${enemy.name} (Defeated)"
            gameOver = true
        }
    }

    private fun TextView.updateFromResults(
        character: Character,
        moveResult: MoveResult,
    ) {
        text = when (moveResult) {
            is AttackResult -> "${character.name} attacked for ${moveResult.damage} damage"
            is DefenseResult -> "${character.name} defended"
            is HealResult -> "${character.name} healed for ${moveResult.healing} HP"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun TextView.updateFromResults(
        character: Character,
        moveResult: SuccessfulDefenseResult?,
    ) {
        if (moveResult is SuccessfulDefenseResult) {
            text = "${character.name} defended and only received ${moveResult.damage} damage"
        }
    }
    
    private fun CharacterInterface.makeRandomMove(opponent: Character): MoveResult {
        val move = listOf("attack", "defend", "heal").random()

        return when(move){
            "attack" -> makeMove(AttackMove(opponent))
            "defend" -> makeMove(DefenseMove)
            else -> makeMove(HealMove)
        }
    }
}