package dec10

import java.util.*

fun parseInput(input: String): List<String> {
    return input.lines()
        .filter { it.isNotBlank() }
}

fun findFirstCorruptingCharacterInLine(line: String): Char? {
    val stack = Stack<Char>()

    val delimiters = line.toCharArray()

    delimiters.forEach {
        if(it == '(' || it == '{' || it == '<' || it == '[') {
            stack.push(it)
        } else {
            val openingCharacter = stack.pop()
            if(openingCharacter == '{' && it == '}'
                || openingCharacter == '(' && it == ')'
                || openingCharacter == '<' && it == '>'
                || openingCharacter == '[' && it == ']'
            ) {
            } else {
                return@findFirstCorruptingCharacterInLine it
            }
        }
    }

    return null
}

fun findMissingCharacterInLine(line: String): List<Char> {
    val stack = Stack<Char>()

    line.toCharArray().forEach {
        if(it == '(' || it == '{' || it == '<' || it == '[') {
            stack.push(it)
        } else {
            stack.pop()
        }
    }

    return stack.toList()
        .map {
            when(it) {
                '{' -> '}'
                '(' -> ')'
                '<' -> '>'
                '[' -> ']'
                else -> throw Exception("Invalid Character $it")
            }
        }
        .reversed()
}

fun calculateMissingLineScore(line: List<Char>): Long {
    val scoreMap = mapOf(
        ')' to 1L,
        ']' to 2L,
        '}' to 3L,
        '>' to 4L,
    )

    return line
        .map { scoreMap[it] ?: throw Exception("Invalid Character $it") }
        .reduce { acc, char ->
            (acc * 5) + char
        }
}

fun calculateIncompleteLinesAutoCompleteScore(input: String): Long {
    val scores =  parseInput(input)
        .filter { findFirstCorruptingCharacterInLine(it) == null }
        .map { findMissingCharacterInLine(it) }
        .map { calculateMissingLineScore(it) }
        .sorted()

    return scores[(scores.size / 2)]
}

fun calculateCorruptLinesSyntaxErrorScore(input: String): Long {
    val scoreMap = mapOf(
        ')' to 3L,
        ']' to 57L,
        '}' to 1197L,
        '>' to 25137L,
    )

    return parseInput(input).sumOf {
        val corruptedChar = findFirstCorruptingCharacterInLine(it)
        scoreMap[corruptedChar] ?: 0
    }

}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = calculateCorruptLinesSyntaxErrorScore(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = calculateIncompleteLinesAutoCompleteScore(input)
    println("result: $result2")
}