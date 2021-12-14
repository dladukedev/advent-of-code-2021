package dec14

import org.junit.Assert
import org.junit.Test

class Dec14Test {
    @Test
    fun part1_test() {
        // Given
        val inputStart = "NNCB"
        val inputInstructions = listOf(
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C",
        )
        val expected = 1588L

        // When
        val actual = calculateDifferenceBetweenMostAndLeastCommonDigit(inputStart, inputInstructions, 10)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val inputStart = "NNCB"
        val inputInstructions = listOf(
            "CH -> B",
            "HH -> N",
            "CB -> H",
            "NH -> C",
            "HB -> C",
            "HC -> B",
            "HN -> C",
            "NN -> C",
            "BH -> H",
            "NC -> B",
            "NB -> B",
            "BN -> B",
            "BB -> N",
            "BC -> B",
            "CC -> N",
            "CN -> C",
        )
        val expected = 2188189693529L

        // When
        val actual = calculateDifferenceBetweenMostAndLeastCommonDigit(inputStart, inputInstructions, 40)

        // Then
        Assert.assertEquals(expected, actual)
    }
}