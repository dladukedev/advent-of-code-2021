package dec25

import org.junit.Assert
import org.junit.Test

class Dec25Test {
    @Test
    fun part1_test() {
        // Given
        val input = """
            v...>>.vv>
            .vv>>.vv..
            >>.>v>...v
            >>v>>.>.v.
            v>v.vv.v..
            >.>>..v...
            .vv..>.>v.
            v.v..>>v.v
            ....v..v.>
        """.trimIndent()
        val expected = 58

        // When
        val actual = getStopPoint(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}