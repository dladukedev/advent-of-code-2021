package dec01

import org.junit.Assert
import org.junit.Test

class Dec01Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
        val expected = 7

        // When
        val actual = getDepthIncreaseCount(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
        val expected = 5

        // When
        val actual = getSlidingWindowDepthIncreaseCount(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}