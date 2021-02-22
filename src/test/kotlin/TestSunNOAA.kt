import java.time.LocalDateTime
import kotlin.math.acos

fun main() {
    // Test formulae against https://www.esrl.noaa.gov/gmd/grad/solcalc/azel.html
    // New website here: https://www.esrl.noaa.gov/gmd/grad/solcalc/
    val dateTimeForTest = LocalDateTime.of(2021, 2, 22, 13, 58, 40)

    val webEqTime = -13.4
    val webSolarDecl = -9.88
    val webSolarAzimuth = 211.69
    val webSolarElevation = 34.5
    val cosWebSolarZenithAngle = 0.5664

    val locationBoulderCo = Location(
        "Boulder, CO",
        40.25,
        0.67,
        1624.0,
        7.0
    )

    val sunNOAA = SunNOAA(dateTimeForTest, locationBoulderCo)

    println("dateTime: ${sunNOAA.dateTime}")
    println("numDaysInYear: ${sunNOAA.numDaysInYear}")
    println("fractionalYear: ${sunNOAA.fractionalYear}")
    println("eqTime: ${sunNOAA.eqTime} vs web $webEqTime")
    println("decl: ${sunNOAA.decl.degrees()} vs web $webSolarDecl")
    println("timeOffset: ${sunNOAA.timeOffset}")
    println("tst: ${sunNOAA.tst}")
    println("ha: ${sunNOAA.ha}")
    println("phi: ${sunNOAA.phi} vs web ${acos(cosWebSolarZenithAngle)}")
    println("theta: ${sunNOAA.theta} vs web $webSolarAzimuth")
}