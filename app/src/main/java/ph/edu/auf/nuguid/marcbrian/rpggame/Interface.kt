package ph.edu.auf.nuguid.marcbrian.rpggame

interface CharacterInterface {
    fun attack(target: Character): AttackResult
    fun defend(): DefendResult
    fun heal(): HealResult
    fun receiveDamage(): DamageResult



}