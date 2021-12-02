package dec02

enum class Direction {
    FORWARD,
    DOWN,
    UP,
}

data class Instruction(
    val direction: Direction,
    val distance: Long,
)

data class LocationState(
    val vertical: Long = 0,
    val horizontal: Long = 0,
)

data class AimLocationState(
    val aim: Long = 0,
    val vertical: Long = 0,
    val horizontal: Long = 0,
)

fun asDirection(dirString: String): Direction {
    return when(dirString.lowercase()) {
        "forward" -> Direction.FORWARD
        "up" -> Direction.UP
        "down" -> Direction.DOWN
        else -> throw Exception("Unknown Direction Value: $dirString")
    }
}

fun parseInstructionString(input: String): Instruction {
    val (dirString, distString) = input.split(" ")

    val direction = asDirection(dirString)
    val distance = distString.toLong()

    return Instruction(direction, distance)
}

fun reduce(state: LocationState, instruction: Instruction): LocationState {
    return when(instruction.direction) {
        Direction.FORWARD -> state.copy(horizontal = state.horizontal + instruction.distance)
        Direction.DOWN -> state.copy(vertical = state.vertical + instruction.distance)
        Direction.UP -> state.copy(vertical = state.vertical - instruction.distance)
    }
}

fun calculateHorizontalDistance(input: List<String>): Long {
    val finalLocation = input.map(::parseInstructionString)
        .fold(LocationState(), ::reduce)

    return finalLocation.horizontal * finalLocation.vertical
}

fun reduceWithAim(state: AimLocationState, instruction: Instruction): AimLocationState {
    return when(instruction.direction) {
        Direction.FORWARD -> state.copy(horizontal = state.horizontal + instruction.distance, vertical = state.vertical + (state.aim * instruction.distance))
        Direction.DOWN -> state.copy(aim = state.aim + instruction.distance)
        Direction.UP -> state.copy(aim = state.aim - instruction.distance)
    }
}

fun calculateHorizontalDistanceWithAim(input: List<String>): Long {
    val finalLocation = input.map(::parseInstructionString)
        .fold(AimLocationState(), ::reduceWithAim)

    return finalLocation.horizontal * finalLocation.vertical
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = calculateHorizontalDistance(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = calculateHorizontalDistanceWithAim(input)
    println("result: $result2")
}