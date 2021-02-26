# kotlin-azan
More idiomatic Kotlin implementation of Azan, Adhan, Azaan, Adhaan, Athan, Athaan, Salat, Salaat, Salah, Salaah, Namaz, Namaaz, Prayer times!

## Formulae used
http://praytimes.org/wiki/Prayer_Times_Calculation  

https://en.wikipedia.org/wiki/Position_of_the_Sun

https://www.esrl.noaa.gov/gmd/grad/solcalc/calcdetails.html

### Special Note about extreme latitudes
Praytimes.org suggests three methods for calculating the prayer times at extreme latitudes.

However, I've taken the first approach described in "Fiqh Made Easy" - choosing the prayer times for the closest
location within a latitude of -48° to +48°.  
For more details on this, see the relevant sections of the [Fiqh Made Easy pdf](docs/fiqh-made-easy-dr-sadlan.pdf) includes in the docs folder.