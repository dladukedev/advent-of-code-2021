package dec20

fun Int.toBinary(len: Int): String {
    return String.format(
        "%" + len + "s",
        Integer.toBinaryString(this)
    ).replace(" ".toRegex(), "0")
}

enum class LightStatus { LIT, DARK }

data class EnhancementAlgorithm(
    val algorithm: Map<String, LightStatus>
)

data class Image(
    val pixels: List<List<LightStatus>>,
) {
    private fun tryGetPixel(row: Int, col: Int, count: Int): LightStatus {
        if(row < 0 || row >= pixels.size || col < 0 || col >= pixels.first().size) {
            return if(count % 2 == 0) {
                LightStatus.DARK
            } else {
                LightStatus.LIT
            }
        }

        return pixels[row][col]
    }

    fun getSiblings(row: Int, col: Int, count: Int): List<LightStatus> {
        return listOf(
            tryGetPixel(row - 1, col - 1, count),
            tryGetPixel(row - 1, col, count),
            tryGetPixel(row - 1, col + 1, count),

            tryGetPixel(row, col - 1, count),
            tryGetPixel(row, col, count),
            tryGetPixel(row, col + 1, count),

            tryGetPixel(row + 1, col - 1, count),
            tryGetPixel(row + 1, col, count),
            tryGetPixel(row + 1, col + 1, count),
        )
    }

    fun expanded(count: Int): Image {
        val outerStatus = if(count % 2 == 0) {
            LightStatus.DARK
        } else {
            LightStatus.LIT
        }

        val newPixels = listOf(List(pixels.size + 2) { outerStatus }) +
                pixels.map { row ->
                    listOf(outerStatus) + row + listOf(outerStatus)
                } +
                listOf(List(pixels.size + 2) { outerStatus })

        val image =  Image(newPixels)

        return image
    }

    override fun toString(): String {
        return pixels.joinToString("\r\n") { row ->
            row.joinToString("") { pixel ->
                when(pixel) {
                    LightStatus.LIT -> "#"
                    LightStatus.DARK -> "."
                }
            }
        }
    }
}

fun parseAlgorithm(input: String): EnhancementAlgorithm {
    val algorithm = input.lines()
        .first()
        .mapIndexed { index, c ->
            val status = when(c) {
                '.' -> LightStatus.DARK
                '#' -> LightStatus.LIT
                else -> throw Exception("Unknown Character {$c} ")
            }
            index.toBinary(9) to status
        }.toMap()

    return EnhancementAlgorithm(algorithm)
}

fun parseImage(input: String): Image {
    val pixels = input.lines()
        .drop(2)
        .map { line ->
            line.trim().toCharArray().map {
                when(it) {
                    '.' -> LightStatus.DARK
                    '#' -> LightStatus.LIT
                    else -> throw Exception("Unknown Character {$it} ")
                }
            }
        }

    return Image(pixels)
}

fun enhanceImage(image: Image, algorithm: EnhancementAlgorithm, count: Int): Image {
    val imageToEnhance = image.expanded(count)

    val enhancedPixels = imageToEnhance.pixels.indices.map { row ->
        imageToEnhance.pixels.first().indices.map { col ->
            val newPoint = imageToEnhance.getSiblings(row, col, count)
                .joinToString("") { when(it) {
                    LightStatus.LIT -> "1"
                    LightStatus.DARK -> "0"
                } }

            algorithm.algorithm[newPoint]!!
        }
    }

    return Image(enhancedPixels)
}

fun getLitPixelsAfterEnhancements(input: String, enhancementCount: Int): Int {
    val algorithm = parseAlgorithm(input)
    val image = parseImage(input)

    val finalImage = (0 until enhancementCount).fold(image) { currentImage, iteration ->
        enhanceImage(currentImage, algorithm, iteration)
    }

    return finalImage.pixels.flatten().count { it == LightStatus.LIT }
}

fun main() {
    println("------------ PART 1 ------------")
    val result1 = getLitPixelsAfterEnhancements(input, 2)
    println("result: $result1")

    println("------------ PART 2 ------------")
    val result2 = getLitPixelsAfterEnhancements(input, 50)
    println("result: $result2")
}