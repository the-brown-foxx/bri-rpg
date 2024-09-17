package ph.edu.auf.nuguid.marcbrian.rpggame

import kotlin.math.max
import kotlin.random.Random
import kotlin.random.nextInt

class Enemy(name: String, characterStats: CharacterStats) : Character(name, characterStats), CharacterInterface {
    fun attack(target: Character): AttackResult {
        target.damageQueued = Random.nextInt(1..40)
        return AttackResult(target.damageQueued)
    }

    fun defend(): DefenseResult {
        val supposedDefense = characterStats.def
        defenseQueued = supposedDefense
        return DefenseResult(supposedDefense)
    }

    fun heal(): HealResult {
        val amount = Random.nextInt(20)
        characterStats.hp += amount
        println("$name Healed $amount")
        return HealResult(amount)
    }

    override fun makeMove(move: Move): MoveResult {
        return when (move) {
            is AttackMove -> attack(move.target)
            DefenseMove -> defend()
            HealMove -> heal()
        }
    }

    override fun receiveDamage(): SuccessfulDefenseResult? {
        val supposedDamage = damageQueued - defenseQueued
        val limitedDamage = max(0, supposedDamage)
        damageQueued = limitedDamage

        val newHp = characterStats.hp - damageQueued
        val limitedHp = max(0, newHp)
        characterStats.hp = limitedHp

        val result = when {
            defenseQueued > 0 -> SuccessfulDefenseResult(limitedDamage)
            else -> null
        }

        damageQueued = 0
        defenseQueued = 0

        return result
    }
}