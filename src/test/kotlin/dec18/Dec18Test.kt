package dec18

import org.junit.Assert
import org.junit.Test

class Dec18Test {
    @Test
    fun parseSnailNumber_test() {
        // Given
        val input = "[[[[1,9],[9,5]],[[5,0],[3,1]]],[[[6,7],[8,8]],[[7,3],0]]]"
        val expected = "[[[[1,9],[9,5]],[[5,0],[3,1]]],[[[6,7],[8,8]],[[7,3],0]]]"

        // When
        val actual = parseSnailNumber(input).toString()

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun explode_test() {
        // Given
        val input = "[[[[[9,8],1],2],3],4]"
        val expected = "[[[[0,9],2],3],4]"

        // When
        val underTest = parseSnailNumber(input)
        underTest.explode()
        val actual  = underTest.toString()

        // Then
        Assert.assertEquals(expected, actual)

    }

    @Test
    fun split_test() {
        // Given
        val input = "[[[[0,7],4],[15,[0,13]]],[1,1]]"
        val expected = "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]"

        // When
        val underTest = parseSnailNumber(input)
        underTest.split()
        val actual  = underTest.toString()

        // Then
        Assert.assertEquals(expected, actual)

    }

    @Test
    fun reduceTest0() {
        // Given
        val input = "[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"
        val expected = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"

        // When
        val underTest = parseSnailNumber(input)
        underTest.reduce()
        val actual  = underTest.toString()


        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun reduceTest01() {
        // Given
        val input = """
            [[[[4,3],4],4],[7,[[8,4],9]]]
            [1,1]
        """.trimIndent()
        val expected = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"

        // When
        val actual = reduceNumber(input).toString()

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun reduceTest1() {
        // Given
        val input = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
        """.trimIndent()
        val expected = "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"

        // When
        val actual = reduceNumber(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun reduceTest() {
        // Given
        val input = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent()
        val expected = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"

        // When
        val actual = reduceNumber(input).toString()

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part1_test() {
        // Given
        val input = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent()
        val expected = 4140L

        // When
        val actual = getSnailNumberMagnitude(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent()
        val expected = 3993L

        // When
        val actual = getMaxMagnitude(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}