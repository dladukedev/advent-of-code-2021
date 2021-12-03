package dec03

import org.junit.Assert
import org.junit.Test

class Dec03Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
        val expected = 198L

        // When
        val actual = getPowerConsumption(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
        val expected = 230L

        // When
        val actual = getLifeSupportRating(input)

        // Then
        Assert.assertEquals(expected, actual)
    }


}