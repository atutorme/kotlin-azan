import java.time.LocalDate

fun main() {
    val s = Sun(LocalDate.now())
    println(s.julianDate)
    println(s.equationOfTime)
}