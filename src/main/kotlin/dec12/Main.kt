package dec12

fun <T> List<T>.hasDuplicate(): Boolean {
    return this.size != this.toHashSet().size
}


sealed class Cave {
    data class MiddleCave(val name: String, val connections: List<Cave>) : Cave()
    object End : Cave()

}

fun findConnections(
    input: List<List<String>>,
    findTarget: String,
    allowDuplicateCave: Boolean,
    travelledSmallCaves: List<String> = emptyList()
): List<Cave> {
    val connections = input
        .filter { it.any { cave -> cave == findTarget } }
        .map { it.single { cave -> cave != findTarget } }
        .filter { it != "start" }
        .filter { if(!allowDuplicateCave || travelledSmallCaves.hasDuplicate()) { !travelledSmallCaves.contains(it) } else true }

    return connections.map {
        when (it) {
            "end" -> {
                Cave.End
            }
            it.lowercase() -> {
                val innerConnections = findConnections(input, it, allowDuplicateCave, travelledSmallCaves + listOf(it))
                Cave.MiddleCave(it, innerConnections)
            }
            else -> {
                val innerConnections = findConnections(input, it, allowDuplicateCave, travelledSmallCaves)
                Cave.MiddleCave(it, innerConnections)
            }
        }
    }

}

fun buildCaveSystem(input: List<String>, allowDuplicateCave: Boolean): List<Cave> {
    val pairs = input.map { it.split("-") }

    return findConnections(pairs, "start", allowDuplicateCave)
}

fun countChildren(caves: List<Cave>): Long {
    if(caves.isEmpty()) {
        return 0
    }

    val nextLayer = caves.flatMap {
        when(it) {
            Cave.End -> emptyList()
            is Cave.MiddleCave -> it.connections
        }
    }

    return caves.count { it is Cave.End } + countChildren(nextLayer)
}

fun getPathCount(input: List<String>, allowDuplicateCave: Boolean = false): Long {
    val caveSystem = buildCaveSystem(input, allowDuplicateCave)
    return countChildren(caveSystem)
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getPathCount(input, false)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getPathCount(input, true)
    println("result: $result2")
}