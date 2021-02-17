import java.time.LocalDate
import java.time.temporal.JulianFields

fun main() {
    val s = Sun(LocalDate.now())
    println("daysSinceJ2000 : ${s.daysSinceJ2000}")
    println("meanAnomaly : ${s.meanAnomaly}")
    println("meanLongitude : ${s.meanLongitude}")
    println("eclipticLongitude : ${s.eclipticLongitude}")
    println("sunToEarthDistanceAstronomicalUnits : ${s.sunToEarthDistanceAstronomicalUnits}")
    println("obliquityOfEcliptic : ${s.obliquityOfEcliptic}")
    println("rightAscension : ${s.rightAscension}")
    println("declination : ${s.declination}")
    println("equationOfTime : ${s.equationOfTime}")
    println(JulianFields.JULIAN_DAY.getFrom(LocalDate.now()))
}