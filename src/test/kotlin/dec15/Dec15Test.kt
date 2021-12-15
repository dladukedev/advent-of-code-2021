package dec15

import org.junit.Assert
import org.junit.Test

class Dec15Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
        val expected = 40

        // When
        //val actual = getRiskLevel(input) // getRiskLevel(input)
        val actual2 = doWork(input) // getRiskLevel(input)

        // Then
        Assert.assertEquals(expected, actual2)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
        val expected = 315L

        // When
        //val actual = getRiskLevel2(input) // getRiskLevel(input)

        // Then
        //Assert.assertEquals(expected, actual)
    }
}