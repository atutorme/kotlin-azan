import java.time.LocalDateTime

fun main() {
    val sunNOAA = SunNOAA(LocalDateTime.now())

    println("dateTime: ${sunNOAA.dateTime}")
    println("numDaysInYear: ${sunNOAA.numDaysInYear}")
    println("dayOfYear: ${sunNOAA.dayOfYear}")
    println("hour: ${sunNOAA.hour}")
    println("fractionalYear: ${sunNOAA.fractionalYear}")
}