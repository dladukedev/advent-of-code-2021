package dec19

import kotlin.math.abs

data class Location(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    operator fun plus(other: Location): Location {
        return Location(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    operator fun minus(other: Location): Location {
        return Location(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }
}

data class Scanner(
    val name: String,
    val beaconLocations: List<Location>,
    val positionRelativeTo00: Location = Location(0, 0, 0),
)

fun parseInput(input: String): List<Scanner> {
    return input
        .split("\n\n")
        .map {
            val lines = it.lines()
            val locations = lines.drop(1).map { line ->
                val (x, y, z) = line.split(",")
                Location(x.toInt(), y.toInt(), z.toInt())
            }
            Scanner(
                name = lines.first(),
                beaconLocations = locations
            )
        }
}

fun permutations(location: Location): List<Location> {
    val (x, y, z) = location
    return listOf(
        Location(x, y, z),
        Location(x, y, -z),
        Location(x, -y, z),
        Location(x, -y, -z),
        Location(-x, y, z),
        Location(-x, y, -z),
        Location(-x, -y, z),
        Location(-x, -y, -z),

        Location(x, z, y),
        Location(x, z, -y),
        Location(x, -z, y),
        Location(x, -z, -y),
        Location(-x, z, y),
        Location(-x, z, -y),
        Location(-x, -z, y),
        Location(-x, -z, -y),

        Location(y, x, z),
        Location(y, x, -z),
        Location(y, -x, z),
        Location(y, -x, -z),
        Location(-y, x, z),
        Location(-y, x, -z),
        Location(-y, -x, z),
        Location(-y, -x, -z),

        Location(y, z, x),
        Location(y, z, -x),
        Location(y, -z, x),
        Location(y, -z, -x),
        Location(-y, z, x),
        Location(-y, z, -x),
        Location(-y, -z, x),
        Location(-y, -z, -x),

        Location(z, x, y),
        Location(z, x, -y),
        Location(z, -x, y),
        Location(z, -x, -y),
        Location(-z, x, y),
        Location(-z, x, -y),
        Location(-z, -x, y),
        Location(-z, -x, -y),

        Location(z, y, x),
        Location(z, y, -x),
        Location(z, -y, x),
        Location(z, -y, -x),
        Location(-z, y, x),
        Location(-z, y, -x),
        Location(-z, -y, x),
        Location(-z, -y, -x),
    )
}

fun findScannerLocationRelative(scanner1: Scanner, scanner2: Scanner): Scanner? {
    val permutations = scanner2.beaconLocations.map {
        permutations(it)
    }

    val pointInSpaceRelativeToScanner1 = permutations.flatMap { locations ->
        locations.flatMap { location ->
            scanner1.beaconLocations.map { scannerLocation ->
                scannerLocation + location
            }
        }
    }.groupingBy { it }
        .eachCount()
        .filter { it.value >= 12 }
        .toList()
        .singleOrNull()
        ?.first ?: return null

    val beaconLocationsRelativeToScanner1 =  permutations.first().indices.map { index ->
        permutations.map {
            it[index]
        }
    }.map { x -> x.map { pointInSpaceRelativeToScanner1 - it } }
        .find { it.intersect(scanner1.beaconLocations).count() >= 12 } ?: return null

    return scanner2.copy(
        beaconLocations = beaconLocationsRelativeToScanner1,
        positionRelativeTo00 = pointInSpaceRelativeToScanner1
    )
}

fun getAllScannersRelativeTo0(scanners: List<Scanner>): List<Scanner> {
    val map = mutableMapOf<Int, Scanner>()
    map[0] = scanners.first()

    val remainingScanners = scanners.toMutableList()
    remainingScanners.removeAt(0)

    val searchWithScanners = mutableListOf(scanners.first())

    while (map.size < scanners.size) {
        val searchScanner = searchWithScanners.removeFirst()

        scanners.map { scanner ->
            findScannerLocationRelative(searchScanner, scanner)
        }.forEachIndexed { index, scanner ->
            if (scanner != null && map[index] == null) {
                searchWithScanners.add(scanner)
                map[index] = scanner
            }
        }
    }

    return map.values.toList()
}

fun getDistictBeaconCount(input: String): Int {
    val scanners = parseInput(input)
    val relativeScanners = getAllScannersRelativeTo0(scanners)
    return relativeScanners.flatMap { it.beaconLocations }.distinct().count()
}

fun getMaxManhattanDistance(input: String): Int {
    val scanners = parseInput(input)
    val relativeScanners = getAllScannersRelativeTo0(scanners)
    return relativeScanners.flatMap { scanner1 ->
        relativeScanners.map { scanner2 ->
            abs(scanner2.positionRelativeTo00.x - scanner1.positionRelativeTo00.x) +
                    abs(scanner2.positionRelativeTo00.y - scanner1.positionRelativeTo00.y) +
                    abs(scanner2.positionRelativeTo00.z - scanner1.positionRelativeTo00.z)
        }
    }.maxOf { it }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getDistictBeaconCount(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getMaxManhattanDistance(input)
    println("result: $result2")
}