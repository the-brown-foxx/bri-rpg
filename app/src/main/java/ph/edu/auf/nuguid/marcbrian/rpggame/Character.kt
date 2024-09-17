package ph.edu.auf.nuguid.marcbrian.rpggame

abstract class Character(
    val name: String,
    val characterStats: CharacterStats,
) {
    var damageQueued = 0
    var defenseQueued = 0
}