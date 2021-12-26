package dec23

import org.junit.Assert
import org.junit.Test

class Dec23Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent()
        val expected = 12521

        // When
        val actual = basicPuzzleLowestCost(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            #############
            #...........#
            ###B#C#B#D###
              #A#D#C#A#
              #########
        """.trimIndent()
        val expected = 44169

        // When
        val actual = advancedPuzzleLowestCost(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}