package dec24

import kotlin.math.abs

sealed class Action {
    data class Push(val index: Int, val offset: Int): Action()
    data class Pop(val index: Int, val offset: Int): Action()
}

data class Association(
    val pushInstruction: Int,
    val popInstruction: Int,
    val offset: Int,
)

fun getAssociations(input: String): List<Association> {
    val actions = input.lines().chunked(18).mapIndexed { index, set ->
        val a = set[4].split(" ").last().toInt()
        val b = set[5].split(" ").last().toInt()
        val c = set[15].split(" ").last().toInt()

        if(a == 1) {
            Action.Push(index, c)
        } else {
            Action.Pop(index, b)
        }
    }

    val stack = mutableListOf<Action.Push>()
    val associations = mutableListOf<Association>()

    actions.forEach { action ->
        when(action) {
            is Action.Pop -> {
                val push = stack.removeLast()

                associations.add(
                    Association(push.index, action.index, push.offset + action.offset)
                )
            }
            is Action.Push -> {
                stack.add(action)
            }
        }
    }

    return associations
}

tailrec fun findLargestNumberInAssociations(associations: List<Association>, array: Array<Char?> = Array(14) { null }): String {
    if(array.none { it == null }) {
        return array.joinToString("")
    }

    val searchIndex = array.indexOfFirst { it == null }

    val association = associations.find { it.pushInstruction == searchIndex } ?: throw Exception("No Association Found")

    val max = if(association.offset > 0) { 9 - association.offset } else { 9 }
    val other = max + association.offset

    array[association.pushInstruction] = max.toString().single()
    array[association.popInstruction] = other.toString().single()

    return findLargestNumberInAssociations(associations, array)
}

tailrec fun findSmallestNumberInAssociations(associations: List<Association>, array: Array<Char?> = Array(14) { null }): String {
    if(array.none { it == null }) {
        return array.joinToString("")
    }

    val searchIndex = array.indexOfFirst { it == null }

    val association = associations.find { it.pushInstruction == searchIndex } ?: throw Exception("No Association Found")

    val min = if(association.offset < 0) { abs(association.offset) + 1 } else { 1 }
    val other = min + association.offset

    array[association.pushInstruction] = min.toString().single()
    array[association.popInstruction] = other.toString().single()

    return findSmallestNumberInAssociations(associations, array)
}

fun getSmallestNumber(input: String): Long {
    val associations = getAssociations(input)

    return findSmallestNumberInAssociations(associations).toLong()
}

fun getLargestNumber(input: String): Long {
    val associations = getAssociations(input)

    return findLargestNumberInAssociations(associations).toLong()
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getLargestNumber(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getSmallestNumber(input)
    println("result: $result2")
}
