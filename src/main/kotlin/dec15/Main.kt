package dec15

import java.util.*

fun getGrid(input: String): List<List<Int>> {
    return input.lines()
        .filter { it.isNotBlank() }
        .map { line -> line.toCharArray().map { it.toString().toInt() } }
}

fun getBigGrid(input: String): List<List<Int>> {
    val lines = input.lines()
        .filter { it.isNotBlank() }
        .map { line -> line.toCharArray().map { it.toString().toInt() } }


    return (0..4).flatMap { row ->
        lines.map { line ->
            (0..4).flatMap { col ->
                line.map {
                    when (val newValue = it + row + col) {
                        in 1..9 -> newValue
                        else -> newValue - 9
                    }
                }
            }
        }
    }
}

data class Point(
    val row: Int,
    val col: Int,
) {
    val neighbors get() = listOf(
        Point(row+1, col),
        Point(row-1, col),
        Point(row, col+1),
        Point(row, col-1),
    )
}

data class Cell(
    val point: Point,
    val dist: Int,
): Comparable<Cell> {
    override fun compareTo(other: Cell): Int {
        return this.dist - other.dist
    }

    fun getNeighbors(grid: List<List<Int>>): List<Cell> {
        return point.neighbors.map { point ->
            grid.getOrNull(point.row)?.getOrNull(point.col)?.let { risk -> Cell(point, risk) }
        }.filterNotNull()
    }
}


// Referenced https://github.com/daniero/code-challenges/blob/master/aoc2021/ruby/15.rb#L5
fun shortest(grid: List<List<Int>>): Int {
    val maxRow = grid.size
    val maxCol = grid.first().size

    val start = Point(0,0)
    val target = Point(maxRow - 1, maxCol - 1)

    val visited = hashSetOf<Point>()
    val queue = PriorityQueue<Cell>()
    queue.add(Cell(start,0))

    while(queue.isNotEmpty()) {
        val cell = queue.remove()

        if(visited.contains(cell.point)) {
            continue
        }
        visited.add(cell.point)

        if(cell.point == target) {
            return cell.dist
        }

        cell.getNeighbors(grid).forEach {
            queue.add(Cell(it.point, cell.dist + grid[it.point.row][it.point.col]))
        }

        queue.sortedBy { it.dist }
    }

    throw Exception("Failed to find Path")
}

fun calculateRiskLevel(input: String): Int {
    val grid = getGrid(input)
    return shortest(grid)
}

fun calculateRiskLevelForLargeGrid(input: String): Int {
    val grid = getBigGrid(input)
    return shortest(grid)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = calculateRiskLevel(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = calculateRiskLevelForLargeGrid(input)
    println("result: $result2")
}