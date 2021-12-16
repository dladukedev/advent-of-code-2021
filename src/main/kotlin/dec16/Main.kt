package dec16

sealed class Packet(
    open val version: Int,
    open val type: Int,
) {
    open val packetSize = 0

    data class LiteralPacket(
        override val version: Int,
        override val type: Int,
        val data: List<String>,
    ) : Packet(version, type) {
        override val packetSize = data.size * 5 + 6
        val dataValue = data.map { it.drop(1) }.joinToString("").toLong(2)
    }

    data class OperatorPacket(
        override val version: Int,
        override val type: Int,
        val lengthType: Int,
        val subPackets: List<Packet>
    ) : Packet(version, type) {
        override val packetSize = 7 + if (lengthType == 0) {
            15
        } else {
            11
        } + subPackets.sumOf { it.packetSize }
    }
}

fun characterToHexBinaryValue(char: Char): String {
    return when (char) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'A' -> "1010"
        'B' -> "1011"
        'C' -> "1100"
        'D' -> "1101"
        'E' -> "1110"
        'F' -> "1111"
        else -> throw Exception("Unknown character input: $char")
    }
}

fun convertInputToBinaryRepresentation(input: String): String {
    return input.toCharArray().joinToString("") { characterToHexBinaryValue(it) }
}

fun String.splitAt(location: Int): Pair<String, String> {
    val start = this.take(location)
    val end = this.drop(location)

    return start to end
}

fun parsePackets(binaryMessage: String): List<Packet> {
    if(binaryMessage.toCharArray().all { it == '0' }) {
        return emptyList()
    }

    val (version, restAfterVersion) = binaryMessage.splitAt(3)
    val (type, restAfterType) = restAfterVersion.splitAt(3)

    when (type.toInt(2)) {
        4 -> {
            val literalsCount = restAfterType.chunked(5).takeWhile { it.first() != '0' }.size
            val literals = restAfterType.chunked(5).take(literalsCount + 1)

            val packet = Packet.LiteralPacket(
                version = version.toInt(2),
                type = type.toInt(2),
                data = literals,
            )

            return listOf(packet)
        }
        else -> {
            val (lengthType, restAfterLengthType) = restAfterType.splitAt(1)

            when (lengthType.toInt(2)) {
                0 -> {
                    val (length, restAfterLength) = restAfterLengthType.splitAt(15)
                    val (subPacketsMessage, restAfterSubPackets) = restAfterLength.splitAt(
                        length.toInt(2)
                    )

                    var remaining = subPacketsMessage
                    val subPackets = mutableListOf<Packet>()

                    while(remaining.isNotEmpty()) {
                        val newPackets = parsePackets(remaining)
                        subPackets.addAll(newPackets)
                        val subPacketLength = newPackets.sumOf { it.packetSize }
                        remaining = remaining.splitAt(subPacketLength).second
                    }

                    val packet = Packet.OperatorPacket(
                        version = version.toInt(2),
                        type = type.toInt(2),
                        lengthType = lengthType.toInt(2),
                        subPackets = subPackets,
                    )

                    return listOf(packet)
                }
                1 -> {
                    val (length, restAfterLength) = restAfterLengthType.splitAt(11)

                    var remaining = restAfterLength
                    val subPackets = (0 until length.toInt(2)).flatMap {
                        val packets = parsePackets(remaining)

                        val subPacketLength = packets.sumOf { it.packetSize }
                        // update remaining?
                        remaining = remaining.splitAt(subPacketLength).second

                        packets
                    }

                    val packet = Packet.OperatorPacket(
                        version = version.toInt(2),
                        type = type.toInt(2),
                        lengthType = lengthType.toInt(2),
                        subPackets = subPackets,
                    )

                    return listOf(packet)

                }
                else -> throw Exception("Invalid length type: $lengthType")
            }

        }
    }
}

tailrec fun sumVersionNumbers(packets: List<Packet>, accum: Long = 0): Long {
    if (packets.isEmpty()) {
        return accum
    }

    val levelVersion = packets.sumOf { it.version }
    val nextLevel = packets.flatMap {
        when (it) {
            is Packet.LiteralPacket -> emptyList()
            is Packet.OperatorPacket -> it.subPackets
        }
    }

    return sumVersionNumbers(nextLevel, accum + levelVersion)
}

fun computeValue(packet: Packet): Long {
   return when(packet) {
        is Packet.LiteralPacket -> packet.dataValue
        is Packet.OperatorPacket -> {
            when(packet.type) {
                0 -> {
                    packet.subPackets.sumOf { computeValue(it) }
                }
                1 -> {
                    packet.subPackets.fold(1L) { acc, pack ->
                        acc * computeValue(pack)
                    }
                }
                2 -> {
                    packet.subPackets.minOf { computeValue(it)  }
                }
                3 -> {
                    packet.subPackets.maxOf { computeValue(it)  }
                }
                5 -> {
                    if(packet.subPackets.size > 2) throw Exception("Invalid size ${packet.subPackets.size}")
                    val (first, second) = packet.subPackets
                    val firstValue = computeValue(first)
                    val secondValue = computeValue(second)
                    if(firstValue > secondValue) { 1L } else { 0L }
                }
                6 -> {
                    if(packet.subPackets.size > 2) throw Exception("Invalid size ${packet.subPackets.size}")
                    val (first, second) = packet.subPackets
                    val firstValue = computeValue(first)
                    val secondValue = computeValue(second)
                    if(firstValue < secondValue) { 1L } else { 0L }
                }
                7 -> {
                    if(packet.subPackets.size > 2) throw Exception("Invalid size ${packet.subPackets.size}")
                    val (first, second) = packet.subPackets
                    val firstValue = computeValue(first)
                    val secondValue = computeValue(second)
                    if(firstValue == secondValue) { 1L } else { 0L }
                }
                else -> throw Exception("Invalid id: ${packet.type}")
            }
        }
    }


}

fun sumOfVersionNumbers(input: String): Long {
    val binaryMessage = convertInputToBinaryRepresentation(input)

    val packets = parsePackets(binaryMessage)

    return sumVersionNumbers(packets)
}

fun valueOfPackets(input: String): Long {
    val binaryMessage = convertInputToBinaryRepresentation(input)

    val packets = parsePackets(binaryMessage)

    return computeValue(packets.single())
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = sumOfVersionNumbers(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = valueOfPackets(input)
    println("result: $result2")

}