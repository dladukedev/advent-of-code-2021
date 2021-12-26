package dec22

enum class Status { ON, OFF }

data class Cube(
    var x1: Int,
    var x2: Int,
    var y1: Int,
    var y2: Int,
    var z1: Int,
    var z2: Int,
    val status: Status = Status.OFF
) {
    val pointCountInCube: Long
        get() {
            return (x2 - x1).toLong() * (y2 - y1).toLong() * (z2 - z1).toLong()
        }

    fun overlapsOther(other: Cube): Boolean {
        return (other.x2 > x1 && other.x1 < x2) &&
                (other.y2 > y1 && other.y1 < y2) &&
                (other.z2 > z1 && other.z1 < z2)
    }


}

data class Instruction(
    val cube: Cube,
)

// So much help from - https://pastebin.com/vKSar4KB
fun getSplitCubes(existing: Cube, new: Cube): List<Cube> {
    if (!existing.overlapsOther(new)) {
        return listOf(existing)
    }

    val cubes = mutableListOf<Cube>()

    if (existing.x1 < new.x1) {
        cubes.add(existing.copy(x2 = new.x1))
        existing.x1 = new.x1
    }
    if (existing.x2 > new.x2) {
        cubes.add(existing.copy(x1 = new.x2))
        existing.x2 = new.x2
    }


    if (existing.y1 < new.y1) {
        cubes.add(existing.copy(y2 = new.y1))
        existing.y1 = new.y1
    }
    if (existing.y2 > new.y2) {
        cubes.add(existing.copy(y1 = new.y2))
        existing.y2 = new.y2
    }

    if (existing.z1 < new.z1) {
        cubes.add(existing.copy(z2 = new.z1))
        existing.z1 = new.z1
    }
    if (existing.z2 > new.z2) {
        cubes.add(existing.copy(z1 = new.z2))
        existing.z2 = new.z2
    }

    return cubes
}

fun parseInput(input: String, boundingBox: Boolean = false): List<Instruction> {
    return input.lines()
        .map { line ->
            val (instruction, coordinates) = line.split(" ")

            val status = when (instruction) {
                "on" -> Status.ON
                "off" -> Status.OFF
                else -> throw Exception("Unknown status: $instruction")
            }

            val (xRange, yRange, zRange) = coordinates.split(",")
                .map { it.split("=").last() }

            val (xMin, xMax) = xRange.split("..").map { it.toInt() }.sorted()
            val (yMin, yMax) = yRange.split("..").map { it.toInt() }.sorted()
            val (zMin, zMax) = zRange.split("..").map { it.toInt() }.sorted()

            val cube = Cube(
                xMin, xMax + 1,
                yMin, yMax + 1,
                zMin, zMax + 1,
                status = status,
            )

            Instruction(cube)
        }.filter { instruction ->
            !boundingBox || (
                    instruction.cube.x1 >= -50 && instruction.cube.x2 <= 50 &&
                    instruction.cube.y1 >= -50 && instruction.cube.y2 <= 50 &&
                    instruction.cube.z1 >= -50 && instruction.cube.z2 <= 50
                    )
        }
}

fun runInstructions(input: String, boundingBox: Boolean = false): Long {
    return parseInput(input, boundingBox).fold(listOf<Cube>()) { cubes, instruction ->
        cubes.flatMap { getSplitCubes(it, instruction.cube) } + listOf(instruction.cube)
    }.filter { it.status == Status.ON }.sumOf { it.pointCountInCube }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = runInstructions(input, true)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = runInstructions(input)
    println("result: $result2")

}