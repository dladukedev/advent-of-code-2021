package dec17

data class TargetArea(
    val xMin: Int,
    val xMax: Int,
    val yMin: Int,
    val yMax: Int,
) {
    fun isPastArea(point: Point): Boolean {
        return point.x > xMax || point.y < yMin
    }

    fun isInArea(point: Point): Boolean {
       return point.x in xMin..xMax && point.y in yMin..yMax
    }
}

data class Point(
    val x: Int,
    val y: Int
)

data class ShotInput(
    val forward: Int,
    val vertical: Int,
)

fun parseTargetArea(input: String): TargetArea {
    val (xRange, yRange) = input.split(",")
    val (xMin, xMax) = xRange.split("..")
    val (yMin, yMax) = yRange.split("..")

    return TargetArea(
        xMin.toInt(),
        xMax.toInt(),
        yMin.toInt(),
        yMax.toInt(),
    )
}

fun computeIsShotLandsInArea(area: TargetArea, shotInput: ShotInput): Int {
    var maxHeight = 0

    var xAccel = shotInput.forward
    var yAccel = shotInput.vertical
    var location = Point(0,0)
    do {
        location = location.copy(
            x = location.x + xAccel,
            y = location.y + yAccel,
        )

        if(location.y > maxHeight) {
            maxHeight = location.y
        }

        if(area.isInArea(location)) {
            return maxHeight
        }

        xAccel = when {
            xAccel >= 1 -> xAccel - 1
            xAccel <= -1 -> xAccel + 1
            else -> xAccel
        }

        yAccel -= 1

    } while (!area.isPastArea(location))

    return -1
}

fun getHighestShot(input: String): Int {
    val targetArea = parseTargetArea(input)

    return (0..targetArea.xMax).flatMap { forward ->
        (0..6000).map { vertical ->
            computeIsShotLandsInArea(targetArea, ShotInput(forward, vertical))
        }
    }.maxOrNull() ?: throw Exception("nope")
}

fun getCountOfSuccessfulShots(input: String): Int {
    val targetArea = parseTargetArea(input)

    return (0..targetArea.xMax).flatMap { forward ->
        (-10000..10000).map { vertical ->
            computeIsShotLandsInArea(targetArea, ShotInput(forward, vertical))
        }
    }.count { it != -1 }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getHighestShot(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getCountOfSuccessfulShots(input)
    println("result: $result2")
}