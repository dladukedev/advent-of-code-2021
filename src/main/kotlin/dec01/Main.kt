package dec01

fun toDepthChange(list: List<Int>, index: Int, current: Int): Int {
    return when (index) {
        0 -> 0
        else -> {
            val previous = list[index - 1]
            current - previous
        }
    }
}

fun toSlidingWindow(list: List<Int>, index: Int, current: Int): Int {
    val next1 = list.getOrNull(index + 1)
    val next2 = list.getOrNull(index + 2)

    return if (next1 == null || next2 == null) {
        0
    } else {
        current + next1 + next2
    }

}

fun getDepthIncreaseCount(input: List<Int>): Int {
    return input.mapIndexed { index, current -> toDepthChange(input, index, current) }
        .count { it > 0 }
}

fun getSlidingWindowDepthIncreaseCount(input: List<Int>): Int {
    val slidingWindows =
        input.mapIndexed { index, current -> toSlidingWindow(input, index, current) }
            .dropLast(2)

    return slidingWindows.mapIndexed { index, current ->
        toDepthChange(
            slidingWindows,
            index,
            current
        )
    }
        .count { it > 0 }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getDepthIncreaseCount(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getSlidingWindowDepthIncreaseCount(input)
    println("result: $result2")
}