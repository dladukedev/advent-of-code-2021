package dec09

data class HeatmapLocation(
    val rowIndex: Int,
    val colIndex: Int,
    val value: Int,
) {
    val riskLevel = value + 1
}

data class Heatmap(
    val points: List<List<Int>>
) {
    private fun getOrNull(row: Int, col: Int): HeatmapLocation? {
        return points.getOrNull(row)?.getOrNull(col)?.let {
            HeatmapLocation(row, col, it)
        }
    }

    operator fun get(row: Int, col: Int): HeatmapLocation {
        return getOrNull(row, col) ?: throw Exception("No value at $row, $col")
    }

    fun getAdjacentPoints(location: HeatmapLocation): List<HeatmapLocation> {
        return listOfNotNull(
            getOrNull(location.rowIndex - 1, location.colIndex),
            getOrNull(location.rowIndex, location.colIndex - 1),
            getOrNull(location.rowIndex + 1, location.colIndex),
            getOrNull(location.rowIndex, location.colIndex + 1),
        )
    }
}

fun parseHeatMap(input: String): Heatmap {
    val points = input.lines()
        .filter { it.isNotBlank() }
        .map { line ->
            line.toCharArray().map { point ->
                point.toString().toInt()
            }
        }

    return Heatmap(points)
}

fun getLowPoints(heatMap: Heatmap): List<HeatmapLocation> {
    return (heatMap.points.indices).flatMap { row ->
        (heatMap.points.first().indices).map { col ->
            val point = heatMap[row, col]

            val adjacentPoints = heatMap.getAdjacentPoints(point)

            if (adjacentPoints.all { adjacentPoint -> adjacentPoint.value > point.value }) {
                point
            } else {
                null
            }
        }
    }.filterNotNull()
}

tailrec fun calculateBasinDepth(
    heatMap: Heatmap,
    locations: List<HeatmapLocation>,
    basin: HashSet<HeatmapLocation> = hashSetOf()
): HashSet<HeatmapLocation> {
    basin.addAll(locations)

    val nextDepth = locations.flatMap { location ->
        heatMap.getAdjacentPoints(location)
    }.filter { !basin.contains(it) && it.value != 9 }

    return if (nextDepth.isEmpty()) {
        basin
    } else {
        calculateBasinDepth(heatMap, nextDepth, basin)
    }
}

fun calculateRiskLevel(input: String): Long {
    val heatMap = parseHeatMap(input)

    return getLowPoints(heatMap).sumOf { point -> point.riskLevel.toLong() }
}

fun getLargestBasins(input: String): Long {
    val heatMap = parseHeatMap(input)

    return getLowPoints(heatMap).map {
        calculateBasinDepth(heatMap, listOf(it))
    }
        .map { it.size.toLong() }
        .sorted()
        .takeLast(3)
        .reduce { acc, basin ->
            acc * basin
        }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = calculateRiskLevel(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getLargestBasins(input)
    println("result: $result2")
}