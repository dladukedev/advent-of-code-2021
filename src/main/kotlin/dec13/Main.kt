package dec13

sealed class Instruction {
    data class FoldHorizontal(val line: Int): Instruction()
    data class FoldVertical(val line: Int): Instruction()
}

data class Coordinate(
    val x: Int,
    val y: Int,
) {
    constructor(strX: String, stry: String): this(
        strX.toInt(),
        stry.toInt()
    )
}

fun parseCoordinates(input: List<String>): List<Coordinate> {
    return input.map {
        val (strX, strY) = it.split(",")
        Coordinate(strX, strY)
    }
}

fun parseInstructions(input: List<String>): List<Instruction> {
    return input.map {
        val (_, _, instructionPart) = it.split(" ")
        val (axis, value) = instructionPart.split("=")

        when(axis.lowercase()) {
            "x" -> Instruction.FoldHorizontal(value.toInt())
            "y" -> Instruction.FoldVertical(value.toInt())
            else -> throw Exception("Unknown Axis value $axis")
        }
    }
}

fun applyFold(points: List<Coordinate>, instruction: Instruction): List<Coordinate> {
    val updatedPoints = when(instruction) {
        is Instruction.FoldHorizontal -> {
            points.map { point ->
                if(point.x > instruction.line) {
                    val distance = point.x - instruction.line
                    point.copy(x = point.x - (distance * 2))
                } else {
                    point
                }
            }
        }
        is Instruction.FoldVertical -> {
            points.map { point ->
                if(point.y > instruction.line) {
                    val distance = point.y - instruction.line
                    point.copy(y = point.y - (distance * 2))
                } else {
                    point
                }
            }
        }
    }

    return updatedPoints.distinct()
}

fun countPointsAfterFirstFold(pointsInput: List<String>, instructionsInput: List<String>): Int {
    val points = parseCoordinates(pointsInput)
    val instruction = parseInstructions(instructionsInput).first()

    val result = applyFold(points, instruction)

    return result.count()
}

fun findStringInResult(coordinates: List<Coordinate>): String {
    val paper = coordinates.toHashSet()

    return (0..35 step 5).map { pointer ->
        val firstRow = listOf(
            paper.contains(Coordinate(pointer, 0)),
            paper.contains(Coordinate(pointer+1, 0)),
            paper.contains(Coordinate(pointer+2, 0)),
            paper.contains(Coordinate(pointer+3, 0)),
        ).filter { it }

        val thirdRow = listOf(
            paper.contains(Coordinate(pointer, 3)),
            paper.contains(Coordinate(pointer+1, 3)),
            paper.contains(Coordinate(pointer+2, 3)),
            paper.contains(Coordinate(pointer+3, 3)),
        ).filter { it }

        when {
            firstRow.size == 1 -> "L"
            firstRow.size == 2 && thirdRow.size == 1 -> "C"
            firstRow.size == 2 && thirdRow.size == 2 -> "H"
            firstRow.size == 2 && thirdRow.size == 4 -> "A"
            firstRow.size == 3 -> "R"
            else -> ""
        }
    }.joinToString("")
}

fun paperAfterFinalFold(pointsInput: List<String>, instructionsInput: List<String>): String {
    val points = parseCoordinates(pointsInput)
    val instructions = parseInstructions(instructionsInput)

    val finalPaper = instructions.fold(points) { pointsAccum, instruction ->
        applyFold(pointsAccum, instruction)
    }

    return findStringInResult(finalPaper)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = countPointsAfterFirstFold(inputPoints, inputInstructions)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = paperAfterFinalFold(inputPoints, inputInstructions)

    println("result: $result2")
}