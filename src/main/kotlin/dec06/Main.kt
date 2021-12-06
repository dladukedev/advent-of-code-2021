package dec06

fun oldGetLanternFishCount(startAges: List<Int>, days: Int): Int {
    return (1..days).fold(startAges) { currentAges, _ ->
        val updatedFishAges = currentAges.map { age ->
            when(age) {
                0 -> 6
                else -> age -1
            }
        }
        val newFish = currentAges.count { age -> age == 0 }
        updatedFishAges + List(newFish) { 8 }
    }.size
}

fun getLanternFishCount(startAges: List<Int>, days: Int): Long {
    val ageMap = mutableMapOf<Int, Long>()
    startAges.forEach { age ->
        ageMap[age] = (ageMap[age] ?: 0) + 1
    }

    return (1..days).fold(ageMap) { map, day ->

        val newMap = mutableMapOf<Int, Long>()

        newMap[0] = map[1] ?: 0
        newMap[1] = map[2] ?: 0
        newMap[2] = map[3] ?: 0
        newMap[3] = map[4] ?: 0
        newMap[4] = map[5] ?: 0
        newMap[5] = map[6] ?: 0
        newMap[6] = ((map[7] ?: 0) + (map[0] ?: 0))
        newMap[7] = map[8] ?: 0
        newMap[8] = map[0] ?: 0

        newMap
    }.values.sum()
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getLanternFishCount(input, 80)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getLanternFishCount(input, 256)
    println("result: $result2")
}