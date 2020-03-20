package expansion

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sin

fun main(args: Array<String>) {

    try {
        getSin()
    } catch (exc: Exception) {
        println("Coś się popsuło, włącz program ponownie")
    }
}

fun getSin() {

    while (true) {
        println("Dzień dobry, jak chcesz podać wartość?")
        println("Wpisz 1 jeśli chcesz podać w radianach")
        println("Wpisz 2 jeśli chcesz podać w stopniach")
        val optionInput = readLine()

        if (optionInput == null || optionInput.isEmpty()) {
            println("Brak inputu")
            continue
        }
        val optionNumber = optionInput.toInt()

        println("Podaj wartość")
        val input = readLine()

        if (input == null) {
            println("Brak inputu")
            continue
        }

        var argument = input.toDouble()
        if (optionNumber == 2) {
            argument = getRadiansFrom(argument)
        } else if (optionNumber != 1) {
            println("Wybierz 1 lub 2 !!!")
            continue
        }

        argument = getAccurateRadian(argument)
        val result = getSinByTaylorExpansion(argument)
        val libraryResult = sin(argument)

        println("Wynik ekspansji po 10 wyrazach: $result")
        println("Wynik wbudowanej bibloteki: $libraryResult")

        val roznica = abs(result - libraryResult)
        println("Wartość różnicy: $roznica")
    }
}

fun getRadiansFrom(degrees: Double): Double {
    return degrees * PI / 180
}

fun getAccurateRadian(radians: Double): Double {
    var value = radians
    if (value > 2 * PI) {
        value = approx(radians)
    }

    return when {
        value < PI / 2 -> value
        value < PI -> PI - value
        value < (3 * PI) / 2 -> value - PI
        else -> (2 * PI) - value
    }
}

fun approx(x: Double): Double {
    var reducedX = x
    if (reducedX > 2 * PI) {
        reducedX = approx(reducedX - 2 * PI)
    }
    return reducedX
}

fun getSinByTaylorExpansion(radians: Double): Double {
    val oddSigns = arrayOf(3, 5, 7, 9)
    var sinResult = radians
    var isPlusSign = false

    for (value in oddSigns) {
        if (isPlusSign) {
            sinResult += nextSign(radians, value)
            isPlusSign = false
        } else {
            sinResult -= nextSign(radians, value)
            isPlusSign = true
        }
    }
    return sinResult
}

fun nextSign(value: Double, times: Int): Double {
    val counter = value.pow(times)
    val denominator = strong(times)
    print("$times: $counter/$denominator\n")
    return counter / denominator
}

fun strong(value: Int): Int {
    var result = 1
    for (x in 2..value) {
        result *= x
    }
    return result
}