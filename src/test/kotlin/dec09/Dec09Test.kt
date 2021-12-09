package dec09

import org.junit.Assert
import org.junit.Test

class Dec09Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
        val expected = 15L

        // When
        val actual = calculateRiskLevel(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
        val expected = 1134L

        // When
        val actual = getLargestBasins(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}