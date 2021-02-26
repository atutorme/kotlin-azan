# kotlin-azan
﷽  
More idiomatic Kotlin implementation of Azan, Adhan, Azaan, Adhaan, Athan, Athaan, Salat, Salaat, Salah, Salaah, Namaz, Namaaz, Prayer times!

## Formulae used
http://praytimes.org/wiki/Prayer_Times_Calculation  

https://en.wikipedia.org/wiki/Position_of_the_Sun

https://www.esrl.noaa.gov/gmd/grad/solcalc/calcdetails.html

### Special note about extreme latitudes
[Praytimes.org](Praytimes.org) suggests three methods for calculating the prayer times at extreme latitudes.

However, I've taken the first approach described in "Fiqh Made Easy" - choosing the prayer times for the closest
location within a latitude of -48° to +48°.  
For more details on this, see the relevant sections of the [Fiqh Made Easy pdf](docs/fiqh-made-easy-dr-sadlan.pdf)
included in the docs folder.

### Note about effect of altitude on prayer time calculations
The well-known [IslamicFinder.org](IslamicFinder.org) website (one of the first and best resources - may Allah reward
them) does not take into account the height above sea-level when calculating salat times.

I have included an option that implements the algorithm described on [Praytimes.org](Praytimes.org). This has an
important effect on the prayer times and can result in differences of a few minutes.
For example, Makkah has an altitude of 304m above sea-level. The difference between sunrise and sunset when taking into
account altitude is about 2-3 minutes!

_Example - salat times on 26 Feb 2021_:  
- Makkah sunrise (no altitude correction - IslamicFinder): 06:43AM
- Makkah sunrise (with altitude correction): 06:40:51
- Makkah sunset (no altitude correction - IslamicFinder): 06:24PM
- Makkah sunset (with altitude correction): 18:26:14