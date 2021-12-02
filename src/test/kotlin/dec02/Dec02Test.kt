package dec02

import org.junit.Assert
import org.junit.Test

class Dec02Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
        )
        val expected = 150L

        // When
        val actual = calculateHorizontalDistance(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
        )
        val expected = 900L

        // When
        val actual = calculateHorizontalDistanceWithAim(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}