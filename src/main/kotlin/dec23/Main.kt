package dec23

import java.util.*
import kotlin.math.abs

data class Location(
    val x: Int,
    val y: Int,
)

data class Amphipod(
    val id: Int,
    val name: Char,
)

sealed class Occupancy {
    object Empty : Occupancy()
    data class Occupied(val pawn: Amphipod) : Occupancy()
}

data class State(
    val room: MutableMap<Location, Occupancy>,
    val totalCost: Int,
): Comparable<State> {
    fun isComplete(): Boolean {
        val allAHome = listOfNotNull(
            room[Location(2, 4)],
            room[Location(2, 3)],
            room[Location(2, 2)],
            room[Location(2, 1)],
        ).all { it is Occupancy.Occupied && it.pawn.name == 'A' }

        val allBHome = listOfNotNull(
            room[Location(4, 4)],
            room[Location(4, 3)],
            room[Location(4, 2)],
            room[Location(4, 1)],
        ).all { it is Occupancy.Occupied && it.pawn.name == 'B' }

        val allCHome = listOfNotNull(
            room[Location(6, 4)],
            room[Location(6, 3)],
            room[Location(6, 2)],
            room[Location(6, 1)],
        ).all { it is Occupancy.Occupied && it.pawn.name == 'C' }

        val allDHome = listOfNotNull(
            room[Location(8, 4)],
            room[Location(8, 3)],
            room[Location(8, 2)],
            room[Location(8, 1)],
        ).all { it is Occupancy.Occupied && it.pawn.name == 'D' }

        return allAHome && allBHome && allCHome && allDHome
    }

    override fun compareTo(other: State): Int {
       return this.totalCost - other.totalCost
    }

    private fun getCharAtLocation(location: Location): Char {
        return when(val occupancy = room[location]) {
            Occupancy.Empty -> ' '
            is Occupancy.Occupied -> occupancy.pawn.name
            null -> '!'
        }
    }

    override fun toString(): String {
        val a = getCharAtLocation(Location(0,0))
        val b = getCharAtLocation(Location(1,0))
        val c = getCharAtLocation(Location(3,0))
        val d = getCharAtLocation(Location(5,0))
        val e = getCharAtLocation(Location(7,0))
        val f = getCharAtLocation(Location(9,0))
        val g = getCharAtLocation(Location(10,0))
        val h = getCharAtLocation(Location(2,1))
        val i = getCharAtLocation(Location(2,2))
        val j = getCharAtLocation(Location(2,3))
        val k = getCharAtLocation(Location(2,4))
        val l = getCharAtLocation(Location(4,1))
        val m = getCharAtLocation(Location(4,2))
        val n = getCharAtLocation(Location(4,3))
        val o = getCharAtLocation(Location(4,4))
        val p = getCharAtLocation(Location(6,1))
        val q = getCharAtLocation(Location(6,2))
        val r = getCharAtLocation(Location(6,3))
        val s = getCharAtLocation(Location(6,4))
        val t = getCharAtLocation(Location(8,1))
        val u = getCharAtLocation(Location(8,2))
        val v = getCharAtLocation(Location(8,3))
        val w = getCharAtLocation(Location(8,4))

        val x = """
            █████████████
            █$a$b $c $d $e $f$g█
            ███$h█$l█$p█$t███
            ███$i█$m█$q█$u███
            ███$j█$n█$r█$v███
            ███$k█$o█$s█$w███
            █████████████
            Total Cost: $totalCost
        """.trimIndent()
        return x
    }
}

val costs = mapOf(
    'A' to 1,
    'B' to 10,
    'C' to 100,
    'D' to 1000,
)

val homeCol = mapOf(
    'A' to 2,
    'B' to 4,
    'C' to 6,
    'D' to 8,
)

fun getOpenLocationsLeft(location: Location, room: MutableMap<Location, Occupancy>): List<Location> {
    val validHallLocations = listOf(0, 1, 3, 5, 7, 9, 10)
    val locations = mutableListOf<Location>()
    var currentX = location.x
    while (currentX > 0) {
        currentX -= 1
        val searchLocation = Location(currentX, 0)
        if (validHallLocations.contains(currentX)) {
            if (room[searchLocation] is Occupancy.Empty) {
                locations.add(searchLocation)
            } else {
                break
            }
        }
    }
    return locations
}

fun getOpenLocationsRight(location: Location, room: MutableMap<Location, Occupancy>): List<Location> {
    val validHallLocations = listOf(0, 1, 3, 5, 7, 9, 10)
    val locations = mutableListOf<Location>()
    var currentX = location.x
    while (currentX < 10) {
        currentX += 1
        val searchLocation = Location(currentX, 0)
        if (validHallLocations.contains(currentX)) {
            if (room[searchLocation] is Occupancy.Empty) {
                locations.add(searchLocation)
            } else {
                break
            }
        }
    }
    return locations
}

fun getPossibleNextLocations(
    pawnLocation: Pair<Location, Occupancy.Occupied>,
    room: MutableMap<Location, Occupancy>,
): List<Location> {
    val (location, occupancy) = pawnLocation
    val (pawn) = occupancy

    val pawnHomeCol = homeCol[pawn.name]!!
    val pawnIsHome = location.x == pawnHomeCol

    val listOfHomeRooms = listOfNotNull(
        room[Location(pawnHomeCol, 4)],
        room[Location(pawnHomeCol, 3)],
        room[Location(pawnHomeCol, 2)],
        room[Location(pawnHomeCol, 1)],
    )

    if (pawnIsHome && listOfHomeRooms.all { it is Occupancy.Empty || (it is Occupancy.Occupied && it.pawn.name == pawn.name) }) {
        return emptyList()
    }

    if (location.y == 1 ||
        (location.y == 2 && room[location.copy(y = 1)] is Occupancy.Empty) ||
        (location.y == 3 && room[location.copy(y = 1)] is Occupancy.Empty && room[location.copy(y = 2)] is Occupancy.Empty) ||
        (location.y == 4 && room[location.copy(y = 1)] is Occupancy.Empty && room[location.copy(y = 2)] is Occupancy.Empty && room[location.copy(
            y = 3
        )] is Occupancy.Empty)
    ) {
        return getOpenLocationsLeft(location, room) + getOpenLocationsRight(location, room)
    } else if(location.y == 0) {
        // Check if blocked by right or left
        val hallCols = listOf(0,1,3,5,7,9,10)
        val blocked = if(location.x > pawnHomeCol) {
            hallCols.filter { it < location.x && it > pawnHomeCol }.any { room[Location(it,0)] is Occupancy.Occupied }
        } else if(location.x < pawnHomeCol) {
            hallCols.filter { it > location.x && it < pawnHomeCol }.any { room[Location(it,0)] is Occupancy.Occupied }
        } else {
            throw Exception("Invalid State")
        }

        if(blocked) {
            return emptyList()
        }


        listOfHomeRooms.forEachIndexed { index, homeOccupancy ->
            if(homeOccupancy is Occupancy.Occupied && homeOccupancy.pawn.name != pawn.name) {
                return emptyList()
            } else if(homeOccupancy is Occupancy.Empty) {
                return listOf(Location(pawnHomeCol, listOfHomeRooms.size - index))
            }
        }

        return emptyList()
    }

    return emptyList()
}

fun getNextStates(prevState: State): List<State> {
    val pawns = prevState.room.toList()
        .filter { it.second is Occupancy.Occupied } as List<Pair<Location, Occupancy.Occupied>>

    val states =  pawns.flatMap { prev ->
        getPossibleNextLocations(prev, prevState.room).map { next ->
            val previousRoomState = prevState.room.toMap().toMutableMap()
            previousRoomState[prev.first] = Occupancy.Empty
            previousRoomState[next] = prev.second

            val distance = abs(prev.first.x - next.x) + abs(prev.first.y - next.y)

            val cost = distance * costs[prev.second.pawn.name]!!

            val totalCost = if(prevState.totalCost == Int.MAX_VALUE) cost else prevState.totalCost + cost

            State(previousRoomState, totalCost)
        }
    }

    return states
}

fun cheapest(room: MutableMap<Location, Occupancy>): State {
    val visitedStates = hashSetOf<MutableMap<Location, Occupancy>>()
    val queue = PriorityQueue<State>()

    queue.add(State(room, Int.MAX_VALUE))

    while (queue.isNotEmpty()) {
        val state = queue.remove()

        if (visitedStates.contains(state.room)) {
            continue
        }
        visitedStates.add(state.room)

        if (state.isComplete()) {
            return state
        }

        queue.addAll(getNextStates(state))

        queue.sortedBy { it.totalCost }
    }

    throw Exception("Failed to find Path")
}

fun parseBasicPuzzle(input: String): MutableMap<Location, Occupancy> {
    val row1 = input.lines()[2]
    val row2 = input.lines()[3]

    return mutableMapOf(
        Location(0, 0) to Occupancy.Empty,
        Location(1, 0) to Occupancy.Empty,
        Location(3, 0) to Occupancy.Empty,
        Location(5, 0) to Occupancy.Empty,
        Location(7, 0) to Occupancy.Empty,
        Location(9, 0) to Occupancy.Empty,
        Location(10, 0) to Occupancy.Empty,

        Location(2, 1) to Occupancy.Occupied(Amphipod(1, row1[3])),
        Location(2, 2) to Occupancy.Occupied(Amphipod(2, row2[3])),

        Location(4, 1) to Occupancy.Occupied(Amphipod(3, row1[5])),
        Location(4, 2) to Occupancy.Occupied(Amphipod(4, row2[5])),

        Location(6, 1) to Occupancy.Occupied(Amphipod(5, row1[7])),
        Location(6, 2) to Occupancy.Occupied(Amphipod(6, row2[7])),

        Location(8, 1) to Occupancy.Occupied(Amphipod(7, row1[9])),
        Location(8, 2) to Occupancy.Occupied(Amphipod(8, row2[9])),
    )
}


fun parseAdvancedPuzzle(input: String): MutableMap<Location, Occupancy> {
    val row1 = input.lines()[2]
    val row2 = "  #D#C#B#A#"
    val row3 = "  #D#B#A#C#"
    val row4 = input.lines()[3]

    return mutableMapOf(
        Location(0, 0) to Occupancy.Empty,
        Location(1, 0) to Occupancy.Empty,
        Location(3, 0) to Occupancy.Empty,
        Location(5, 0) to Occupancy.Empty,
        Location(7, 0) to Occupancy.Empty,
        Location(9, 0) to Occupancy.Empty,
        Location(10, 0) to Occupancy.Empty,

        Location(2, 1) to Occupancy.Occupied(Amphipod(1, row1[3])),
        Location(2, 2) to Occupancy.Occupied(Amphipod(2, row2[3])),
        Location(2, 3) to Occupancy.Occupied(Amphipod(3, row3[3])),
        Location(2, 4) to Occupancy.Occupied(Amphipod(4, row4[3])),

        Location(4, 1) to Occupancy.Occupied(Amphipod(5, row1[5])),
        Location(4, 2) to Occupancy.Occupied(Amphipod(6, row2[5])),
        Location(4, 3) to Occupancy.Occupied(Amphipod(7, row3[5])),
        Location(4, 4) to Occupancy.Occupied(Amphipod(8, row4[5])),

        Location(6, 1) to Occupancy.Occupied(Amphipod(9, row1[7])),
        Location(6, 2) to Occupancy.Occupied(Amphipod(10, row2[7])),
        Location(6, 3) to Occupancy.Occupied(Amphipod(11, row3[7])),
        Location(6, 4) to Occupancy.Occupied(Amphipod(12, row4[7])),

        Location(8, 1) to Occupancy.Occupied(Amphipod(13, row1[9])),
        Location(8, 2) to Occupancy.Occupied(Amphipod(14, row2[9])),
        Location(8, 3) to Occupancy.Occupied(Amphipod(15, row3[9])),
        Location(8, 4) to Occupancy.Occupied(Amphipod(16, row4[9])),
    )
}

fun basicPuzzleLowestCost(input: String): Int {
    val room = parseBasicPuzzle(input)
    return cheapest(room).totalCost
}

fun advancedPuzzleLowestCost(input: String): Int {
    val room = parseAdvancedPuzzle(input)
    return cheapest(room).totalCost
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = basicPuzzleLowestCost(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = advancedPuzzleLowestCost(input)
    println("result: $result2")

}