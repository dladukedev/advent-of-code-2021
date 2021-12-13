package dec13

import org.junit.Assert
import org.junit.Test

class Dec13Test {
    @Test
    fun part1_test() {
        // Given
        val inputPoints = listOf(
            "6,10",
            "0,14",
            "9,10",
            "0,3",
            "10,4",
            "4,11",
            "6,0",
            "6,12",
            "4,1",
            "0,13",
            "10,12",
            "3,4",
            "3,0",
            "8,4",
            "1,10",
            "2,14",
            "8,10",
            "9,0",
        )
        val inputInstructions = listOf(
            "fold along y=7",
            "fold along x=5",
        )
        val expected = 17

        // When
        val actual = countPointsAfterFirstFold(inputPoints, inputInstructions)

        // Then
        Assert.assertEquals(expected, actual)
    }
}