package dec05

data class Point(
    val x: Int,
    val y: Int,
)

data class Line(
    val start: Point,
    val end: Point,
)

fun getPointsOnLine(line: Line): List<Point> {
    return when {
        line.start.x == line.end.x -> {
            val (start, end) = listOf(line.start.y, line.end.y).sorted()
            (start..end).map { Point(line.start.x, it) }
        }
        line.start.y == line.end.y -> {
            val (start, end) = listOf(line.start.x, line.end.x).sorted()
            (start..end).map { Point(it, line.start.y) }
        }
        line.start.y > line.end.y && line.start.x > line.end.x -> {
            (0..(line.start.y-line.end.y)).map { offset ->
                Point(line.start.x-offset,line.start.y-offset)
            }
        }
        line.start.y > line.end.y && line.start.x < line.end.x -> {
            (0..(line.start.y-line.end.y)).map { offset ->
                Point(line.start.x+offset,line.start.y-offset)
            }
        }
        line.start.y < line.end.y && line.start.x > line.end.x -> {
            (0..(line.end.y-line.start.y)).map { offset ->
                Point(line.start.x-offset,line.start.y+offset)
            }
        }
        line.start.y < line.end.y && line.start.x < line.end.x -> {
            (0..(line.end.y-line.start.y)).map { offset ->
                Point(line.start.x+offset,line.start.y+offset)
            }
        }
        else -> emptyList()
    }
}

fun parseStringFromLine(str: String): Line {
    val (start, end) = str.split(" -> ")

    val (x1, y1) = start.split(",")
    val (x2, y2) = end.split(",")

    return Line(
        Point(x1.toInt(), y1.toInt()),
        Point(x2.toInt(), y2.toInt())
    )
}

fun getOverlapCountHorizontalVertical(input: List<String>): Int {
    return input.map { parseStringFromLine(it) }
        .filter { it.start.x == it.end.x || it.start.y == it.end.y }
        .fold(mutableMapOf<Point, Int>()) { acc, line ->
            val points = getPointsOnLine(line)

            points.forEach {
                acc[it] = (acc[it] ?: 0) + 1
            }

            acc
        }.count { map -> map.value > 1 }
}

fun getOverlapCount(input: List<String>): Int {
    return input.map { parseStringFromLine(it) }
        .fold(mutableMapOf<Point, Int>()) { acc, line ->
            val points = getPointsOnLine(line)

            points.forEach {
                acc[it] = (acc[it] ?: 0) + 1
            }

            acc
        }.count { map -> map.value > 1 }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getOverlapCountHorizontalVertical(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getOverlapCount(input)
    println("result: $result2")
}