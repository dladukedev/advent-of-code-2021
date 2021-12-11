package dec11

import org.junit.Assert
import org.junit.Test

class Dec11Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
        val expected10 = 204L
        val expected100 = 1656L

        // When
        val actual10 = getFlashCount(input, 10)
        val actual100 = getFlashCount(input, 100)

        // Then
        Assert.assertEquals(expected10, actual10)
        Assert.assertEquals(expected100, actual100)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
        val expected = 195L

        // When
        val actual = getFirstFullFlashRound(input)

        // Then

        Assert.assertEquals(expected, actual)
    }
}