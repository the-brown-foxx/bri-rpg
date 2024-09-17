package ph.edu.auf.nuguid.marcbrian.rpggame

sealed interface MoveResult

data class AttackResult(val damage: Int) : MoveResult
data class DefendResult(val defense: Int) : MoveResult
data class HealResult(val healing: Int) : MoveResult
data class DamageResult(val damage: Int) : MoveResult