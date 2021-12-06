package dec06

import org.junit.Assert
import org.junit.Test

class Dec06Test {
    @Test
    fun part1_test() {
        // Given
        val input = listOf(3,4,3,1,2)
        val expected1 = 26
        val expected2 = 5934

        // When
        val actual1 = oldGetLanternFishCount(input, 18)
        val actual2 = oldGetLanternFishCount(input, 80)

        Assert.assertEquals(expected1, actual1)
        Assert.assertEquals(expected2, actual2)
    }

    @Test
    fun part2_test() {
        // Given
        val input = listOf(3,4,3,1,2)
        val expected1 = 26L
        val expected2 = 5934L

        // When
        val actual1 = getLanternFishCount(input, 18)
        val actual2 = getLanternFishCount(input, 80)

        Assert.assertEquals(expected1, actual1)
        Assert.assertEquals(expected2, actual2)
    }
}