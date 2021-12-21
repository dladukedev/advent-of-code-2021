package dec21

import org.junit.Assert
import org.junit.Test

class Dec21Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent()
        val expected = 739785

        // When
        val actual = getWinningResult(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            Player 1 starting position: 4
            Player 2 starting position: 8
        """.trimIndent()
        val expected = 444356092776315L

        // When
        val actual = getQuantumWinCount(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}