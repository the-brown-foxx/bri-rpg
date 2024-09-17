package ph.edu.auf.nuguid.marcbrian.rpggame

interface CharacterInterface {
    fun makeMove(move: Move): MoveResult
    fun receiveDamage(): SuccessfulDefenseResult?
}