package ph.edu.auf.nuguid.marcbrian.rpggame

sealed class Move

data class AttackMove(val target: Character) : Move()
data object DefenseMove : Move()
data object HealMove : Move()