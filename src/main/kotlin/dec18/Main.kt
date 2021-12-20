package dec18

import dec16.splitAt
import kotlin.math.roundToInt

enum class ChildDirection {
    LEFT,
    RIGHT
}

sealed class SnailNumber(
    open var direction: ChildDirection?,
    open var parent: SnailNumber?
) {
    data class NumPair(
        var left: SnailNumber,
        var right: SnailNumber,
        override var direction: ChildDirection?,
        override var parent: SnailNumber?
    ) : SnailNumber(direction, parent) {
        override fun toString(): String {
            return "[$left,$right]"
        }
    }

    data class Regular(
        var num: Int,
        override var direction: ChildDirection?,
        override var parent: SnailNumber?
    ) :
        SnailNumber(direction, parent) {
        override fun toString(): String {
            return "$num"
        }
    }

    fun getDepth(): Int {
        var depth = 1
        var parent = this.parent
        while (parent != null) {
            depth++
            parent = parent.parent
        }

        return depth
    }

    fun getNextRight(): Regular? {
        var target = this.parent
        while (target != null) {
            if (target.direction == ChildDirection.LEFT && target.parent is NumPair) {
                if((target.parent as NumPair).right is Regular) {
                    return (target.parent as NumPair).right as Regular
                } else {
                    var nextTarget = ((target.parent as NumPair).right as NumPair).left
                    while(true) {
                        when(nextTarget) {
                            is NumPair -> {
                                nextTarget = nextTarget.left
                            }
                            is Regular -> {
                                return nextTarget
                            }
                        }
                    }
                }
            }

            target = target.parent
        }
        return null
    }

    fun getNextLeft(): Regular? {
        var target = this.parent

        while (target != null) {
            if (target.direction == ChildDirection.RIGHT && target.parent is NumPair) {
                if((target.parent as NumPair).left is Regular) {
                    return (target.parent as NumPair).left as Regular
                } else {
                    var nextTarget = ((target.parent as NumPair).left as NumPair).right
                    while(true) {
                        when(nextTarget) {
                            is NumPair -> {
                                nextTarget = nextTarget.right
                            }
                            is Regular -> {
                                return nextTarget
                            }
                        }
                    }
                }
            }

            target = target.parent
        }

        return null
    }

    operator fun plus(other: SnailNumber): SnailNumber {
        val newPair = NumPair(this, other, null, null)
        this.direction = ChildDirection.LEFT
        other.direction = ChildDirection.RIGHT
        this.parent = newPair
        other.parent = newPair
        newPair.reduce()
        return newPair
    }

    fun split(): Boolean {
        when (this) {
            is NumPair -> {
                return this.left.split() || this.right.split()
            }
            is Regular -> {
                if (this.num > 9) {
                    val half = this.num / 2.0
                    val leftHalf = half.toInt()
                    val rightHalf = half.roundToInt()

                    val leftReg = Regular(leftHalf, ChildDirection.LEFT, null)
                    val rightReg = Regular(rightHalf, ChildDirection.RIGHT, null)

                    when (direction) {
                        ChildDirection.LEFT -> {
                            val newPair = NumPair(leftReg, rightReg, ChildDirection.LEFT, this.parent)
                            rightReg.parent = newPair
                            leftReg.parent = newPair
                            (this.parent as NumPair).left = newPair
                        }
                        ChildDirection.RIGHT -> {
                            val newPair = NumPair(leftReg, rightReg, ChildDirection.RIGHT, this.parent)
                            rightReg.parent = newPair
                            leftReg.parent = newPair
                            (this.parent as NumPair).right = newPair
                        }
                        null -> TODO()
                    }

                    return true
                }
            }
        }

        return false
    }

    fun explode(): Boolean {
        if (this is NumPair) {
            if (left is Regular && right is Regular && getDepth() > 4) {
                val right = right.getNextRight()
                val left = left.getNextLeft()

                if (right != null) {
                    right.num += (this.right as Regular).num
                }

                if (left != null) {
                    left.num += (this.left as Regular).num
                }

                when (direction) {
                    ChildDirection.LEFT -> {
                        (this.parent as NumPair).left = Regular(0, ChildDirection.LEFT, this.parent)
                    }
                    ChildDirection.RIGHT -> {
                        (this.parent as NumPair).right = Regular(0, ChildDirection.RIGHT, this.parent)
                    }
                    null -> TODO()
                }

                return true
            }

            return this.left.explode() || this.right.explode()
        }

        return false
    }

    fun reduce() {
        while(this.explode() || this.split()) {
        }
    }

    fun getMagnitude(): Long {
        return when(this) {
            is NumPair -> {
                (3 * left.getMagnitude()) + (2 * right.getMagnitude())
            }
            is Regular -> {
                when(direction) {
                    ChildDirection.LEFT -> {
                        num.toLong()
                    }
                    ChildDirection.RIGHT -> {
                        num.toLong()
                    }
                    null -> TODO()
                }
            }
        }
    }
}


fun findSplittingComma(input: String): Int {
    var open = 0
    (0..input.length).forEach { index ->
        when (input[index]) {
            '[' -> open++
            ']' -> open--
            ',' -> if (open == 1) return index + 1
        }
    }

    return -1
}

fun parseSnailNumber(input: String): SnailNumber {
    return if (input.first() == '[') {
        val commaIndex = findSplittingComma(input)
        val (start, end) = input.splitAt(commaIndex)
        val left = parseSnailNumber(start.drop(1).dropLast(1))
        val right = parseSnailNumber(end.dropLast(1))
        val tree = SnailNumber.NumPair(
            left,
            right,
            null,
            null,
        )
        right.parent = tree
        right.direction = ChildDirection.RIGHT
        left.parent = tree
        left.direction = ChildDirection.LEFT

        tree
    } else {
        SnailNumber.Regular(input.toInt(), null, null)
    }
}

fun parseInput(input: String): List<SnailNumber> {
    return input.lines().filter { it.isNotBlank() }
        .map { parseSnailNumber(it) }
}

fun reduceNumber(input: String): SnailNumber {
    return parseInput(input).reduce { acc, snailNumber ->
        acc + snailNumber
    }
}

fun getSnailNumberMagnitude(input: String): Long {
    return reduceNumber(input).getMagnitude()
}

fun getMaxMagnitude(input: String): Long {
    val numbers = parseInput(input)

    return (numbers.indices).flatMap { index1 ->
        (numbers.indices).map { index2 ->
            if(index1 == index2) { 0 } else {
                val numberx = parseInput(input)
                val num1 = numberx[index1]
                val num2 = numberx[index2]
                val x = (num1 + num2).getMagnitude()
                x
            }
        }
    }.maxOf { it }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getSnailNumberMagnitude(input)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getMaxMagnitude(input)
    println("result: $result2")
}