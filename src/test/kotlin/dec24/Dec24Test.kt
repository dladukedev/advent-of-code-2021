package dec24

import org.junit.Assert
import org.junit.Test

enum class Register { W, X, Y, Z }

fun getRegister(input: String): Register {
    return when (input) {
        "w" -> Register.W
        "x" -> Register.X
        "y" -> Register.Y
        "z" -> Register.Z
        else -> throw Exception("Unknown Register: $input")
    }
}

sealed class Operation {
    data class Read(val first: Register) : Operation()

    data class AddNum(val first: Register, val second: Int) : Operation()
    data class AddReg(val first: Register, val second: Register) : Operation()

    data class MultiplyNum(val first: Register, val second: Int) : Operation()
    data class MultiplyReg(val first: Register, val second: Register) : Operation()

    data class DivideNum(val first: Register, val second: Int) : Operation()
    data class DivideReg(val first: Register, val second: Register) : Operation()

    data class ModNum(val first: Register, val second: Int) : Operation()
    data class ModReg(val first: Register, val second: Register) : Operation()

    data class EqualNum(val first: Register, val second: Int) : Operation()
    data class EqualReg(val first: Register, val second: Register) : Operation()
}

fun parseInput(input: String): List<Operation> {
    return input.lines()
        .map {
            if (it.split(" ").first() == "inp") {
                val (_, in1) = it.split(" ")
                Operation.Read(getRegister(in1))
            } else {
                val (op, in1, in2) = it.split(" ")
                when (op) {
                    "add" -> {
                        if (in2.toIntOrNull() != null) {
                            Operation.AddNum(getRegister(in1), in2.toInt())
                        } else {
                            Operation.AddReg(getRegister(in1), getRegister(in2))
                        }
                    }
                    "mul" -> {
                        if (in2.toIntOrNull() != null) {
                            Operation.MultiplyNum(getRegister(in1), in2.toInt())
                        } else {
                            Operation.MultiplyReg(getRegister(in1), getRegister(in2))
                        }
                    }
                    "div" -> {
                        if (in2.toIntOrNull() != null) {
                            Operation.DivideNum(getRegister(in1), in2.toInt())
                        } else {
                            Operation.DivideReg(getRegister(in1), getRegister(in2))
                        }
                    }
                    "mod" -> {
                        if (in2.toIntOrNull() != null) {
                            Operation.ModNum(getRegister(in1), in2.toInt())
                        } else {
                            Operation.ModReg(getRegister(in1), getRegister(in2))
                        }
                    }
                    "eql" -> {
                        if (in2.toIntOrNull() != null) {
                            Operation.EqualNum(getRegister(in1), in2.toInt())
                        } else {
                            Operation.EqualReg(getRegister(in1), getRegister(in2))
                        }
                    }
                    else -> throw Exception("Unknown Op: $op")
                }
            }
        }
}

fun validateInput(testValue: Long, instructions: List<Operation>): Boolean {
    val registers = mutableMapOf(
        Register.W to 0,
        Register.X to 0,
        Register.Y to 0,
        Register.Z to 0,
    )

    fun getFromRegister(reg: Register): Int {
        return registers[reg]!!
    }

    var testValueDigits = testValue.toString().map { it.toString().toInt() }.toMutableList()

    instructions.forEach {
        when (it) {
            is Operation.AddNum -> {
                registers[it.first] = getFromRegister(it.first) + it.second
            }
            is Operation.AddReg -> {
                registers[it.first] = getFromRegister(it.first) + getFromRegister(it.second)
            }
            is Operation.DivideNum -> {
                registers[it.first] = getFromRegister(it.first) / it.second
            }
            is Operation.DivideReg -> {
                registers[it.first] = getFromRegister(it.first) / getFromRegister(it.second)
            }
            is Operation.EqualNum -> {
                registers[it.first] = if (getFromRegister(it.first) == it.second) 1 else 0
            }
            is Operation.EqualReg -> {
                registers[it.first] =
                    if (getFromRegister(it.first) == getFromRegister(it.second)) 1 else 0
            }
            is Operation.ModNum -> {
                registers[it.first] = getFromRegister(it.first) % it.second
            }
            is Operation.ModReg -> {
                registers[it.first] = getFromRegister(it.first) % getFromRegister(it.second)
            }
            is Operation.MultiplyNum -> {
                registers[it.first] = getFromRegister(it.first) * it.second
            }
            is Operation.MultiplyReg -> {
                registers[it.first] = getFromRegister(it.first) * getFromRegister(it.second)
            }
            is Operation.Read -> {
                registers[it.first] = testValueDigits.removeFirst()
            }
        }
    }


    return registers[Register.Z] == 0
}

class Dec24Test {
    @Test
    fun part1_test() {
        // Given
        val instructions = parseInput(input)

        // When
        val result = getLargestNumber(input)

        // Then
        Assert.assertTrue(validateInput(result, instructions))
    }

    @Test
    fun part2_test() {
        // Given
        val instructions = parseInput(input)

        // When
        val result = getSmallestNumber(input)

        // Then
        Assert.assertTrue(validateInput(result, instructions))
    }
}