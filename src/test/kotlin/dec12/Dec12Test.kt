package dec12

import org.junit.Assert
import org.junit.Test

class Dec12Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end",
        )
        val expected = 10L

        // When
        val actual = getPathCount(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(
            "start-A",
            "start-b",
            "A-c",
            "A-b",
            "b-d",
            "A-end",
            "b-end",
        )
        val expected = 36L

        // When
        val actual = getResult2(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}