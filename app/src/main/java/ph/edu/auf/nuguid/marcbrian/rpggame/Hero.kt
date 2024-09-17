package ph.edu.auf.nuguid.marcbrian.rpggame

import kotlin.math.max
import kotlin.random.Random
import kotlin.random.nextInt

class Hero(name: String, characterStats: CharacterStats) : Character(name, characterStats), CharacterInterface {

    override fun attack(target: Character): AttackResult {
        target.damageQueued = Random.nextInt(1..40)
        println("$name Attacked ${target.name} for ${target.damageQueued}!")
        return AttackResult(target.damageQueued)
    }

    override fun defend(): DefendResult {
        val supposedDefense = characterStats.def
        defenseQueued = supposedDefense
        return DefendResult(supposedDefense)
    }

    override fun heal(): HealResult {
        val amount = Random.nextInt(20)
        characterStats.hp += amount
        println("$name Healed $amount")
        return HealResult(amount)
    }

    override fun receiveDamage(): DamageResult {
        val supposedDamage = damageQueued - defenseQueued
        val limitedDamage = max(0, supposedDamage)
        damageQueued = limitedDamage

        val newHp = characterStats.hp - damageQueued
        val limitedHp = max(0, newHp)
        characterStats.hp = limitedHp

        if (defenseQueued > 0){
            println("$name successfully defended and took $limitedDamage damage!")
        }

        damageQueued = 0
        defenseQueued = 0

        return DamageResult(limitedDamage)
    }
}