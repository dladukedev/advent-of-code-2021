package dec04

import org.junit.Assert
import org.junit.Test

class Dec04Test {
    @Test
    fun part1_test() {
        // Given
        val callInput = listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1)
        val boardInput = listOf(
            """22 13 17 11  0
8  2 23  4 24
21  9 14 16  7
6 10  3 18  5
1 12 20 15 19""",

            """3 15  0  2 22
9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6""",

            """14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
2  0 12  3  7""",
        )

        val expected = 4512

        // When
        val actual = getFinalScore(callInput, boardInput)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun colWin_works() {
        val calls = listOf(2, 7, 12, 17, 22)
        val boardInput = listOf(
            """ 0  1  2  3  4
                5  6  7  8  9
                10 11 12 13 14
                15 16 17 18 19
                20 21 22 23 24
            """.trimIndent()
        )

        val board = parseInputToBoards(boardInput).single()

        calls.forEach { call ->
            board.handleCall(call)
        }

//        println(board.spaces.indices.any { colIndex -> board.spaces.indices.all { rowIndex -> board[rowIndex,colIndex].isMarked } })

        Assert.assertTrue(board.hasWin)
    }

    @Test
    fun part2_test() {
        // Given
        val callInput = listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1)
        val boardInput = listOf(
            """22 13 17 11  0
8  2 23  4 24
21  9 14 16  7
6 10  3 18  5
1 12 20 15 19""",

            """3 15  0  2 22
9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6""",

            """14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
2  0 12  3  7""",
        )

        val expected = 1924

        // When
        val actual = getLastFinalScore(callInput, boardInput)

        // Then
        Assert.assertEquals(expected, actual)
    }
}