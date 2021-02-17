import java.time.LocalDate

fun main() {
    val s = Sun(LocalDate.now())
    println(s.equationOfTime)
    println(s.daysSinceJ2000)
}