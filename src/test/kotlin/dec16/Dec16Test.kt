package dec16

import org.junit.Assert
import org.junit.Test

class Dec16Test {
    @Test
    fun `test parsing input`() {
        // Given
        val input = "110100101111111000101000"
        val expected = Packet.LiteralPacket(6, 4, listOf("10111", "11110", "00101"))
        val expectedValue = 2021

        // When
        val actual = parsePackets(input).single()
        val actualValue = (actual as Packet.LiteralPacket).dataValue

        // Then
        Assert.assertEquals(expected, actual)
        Assert.assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `test computing literal`() {
        // Given
        val input = "D2FE28"
        val expected = "110100101111111000101000"

        // When
        val actual = convertInputToBinaryRepresentation(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `string split returns correct halves`() {
        // Given
        val input = "ABCDEF"
        val expectedStart = "AB"
        val expectedEnd = "CDEF"

        // When
        val (actualStart, actualEnd) = input.splitAt(2)

        // Then
        Assert.assertEquals(expectedStart, actualStart)
        Assert.assertEquals(expectedEnd, actualEnd)
    }


    @Test
    fun part1_test() {
        // Given
        val input = "8A004A801A8002F478"
        val expected = 16L

        // When
        val actual = sumOfVersionNumbers(input)

        // Then
        Assert.assertEquals(expected, actual)
    }


    @Test
    fun part1_test1() {
        // Given
        val input = "620080001611562C8802118E34"
        val expected = 12L

        // When
        val actual = sumOfVersionNumbers(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part1_test2() {
        // Given
        val input = "C0015000016115A2E0802F182340"
        val expected = 23L

        // When
        val actual = sumOfVersionNumbers(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun part1_test3() {
        // Given
        val input = "A0016C880162017C3686B18A3D4780"
        val expected = 31L

        // When
        val actual = sumOfVersionNumbers(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test() {
        // Given
        val input = "C200B40A82"
        val expected = 3L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test1() {
        // Given
        val input = "04005AC33890"
        val expected = 54L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test2() {
        // Given
        val input = "880086C3E88112"
        val expected = 7L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test3() {
        // Given
        val input = "CE00C43D881120"
        val expected = 9L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test4() {
        // Given
        val input = "D8005AC2A8F0"
        val expected = 1L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun part2_test5() {
        // Given
        val input = "F600BC2D8F"
        val expected = 0L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun part2_test6() {
        // Given
        val input = "9C005AC2F8F0"
        val expected = 0L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
    @Test
    fun part2_test7() {
        // Given
        val input = "9C0141080250320F1802104A08"
        val expected = 1L

        // When
        val actual = valueOfPackets(input)

        // Then
        Assert.assertEquals(expected, actual)
    }
}