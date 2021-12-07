package dec07

import org.junit.Assert
import org.junit.Test

class Dec07Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(16,1,2,0,4,2,7,1,2,14)
        val expected = 37L

        // When
        val actual = fuelCalc(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(16,1,2,0,4,2,7,1,2,14)
        val expected = 168L

        // When
        val actual = exponentialFuelCalc(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}