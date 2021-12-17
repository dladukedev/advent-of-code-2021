package dec17

import org.junit.Assert
import org.junit.Test

class Dec17Test {
    @Test
    fun part1_test() {
        // Given
        val input = "20..30,-10..-5"
        val expected = 45

        // When
        val actual = getHighestShot(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = "20..30,-10..-5"
        val expected = 112

        // When
        val actual = getCountOfSuccessfulShots(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

}