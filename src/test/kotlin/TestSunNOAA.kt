import java.time.LocalDateTime

fun main() {
    val sunNOAA = SunNOAA(LocalDateTime.now())

    println("dateTime: ${sunNOAA.dateTime}")
    println("numDaysInYear: ${sunNOAA.numDaysInYear}")
    println("dayOfYear: ${sunNOAA.dayOfYear}")
    println("hour: ${sunNOAA.hour}")
    println("fractionalYear: ${sunNOAA.fractionalYear}")
    println("eqTime: ${sunNOAA.eqTime}")
    println("decl: ${sunNOAA.decl}")
    println("timeOffset: ${sunNOAA.timeOffset}")
    println("tst: ${sunNOAA.tst}")
    println("ha: ${sunNOAA.ha}")
}