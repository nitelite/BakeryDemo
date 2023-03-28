# Bakeriet
Du skal lage kjernen i et system for et bakeri. Koden du skriver skal brukes av andre utviklere, som skal lage andre deler av systemet (f.eks. brukergrensesnittet).
Bakeriet lager flere produkter, etter registrerte oppskrifter. Produktene er klassifisert som enten Kremkaker, Gjærbakst, Småkaker eller Annet Bakverk. De er ute etter et system for å holde styr på oppskriftene, og lagerbeholdningen av ingredienser.
For hver ingrediens skal systemet lagre ID, navn og lagerbeholdning i gram.
For hver oppskrift skal systemet lagre ID, et navn, dato oppskriften ble registrert, en produktklassifisering (Kremkake, Gjærbakst, Småkake eller Annet Bakverk) og en eller flere ingredienser. Det skal lagres både hvilke ingredienser som trengs, og hvor mye som trengs av hver ingrediens i gram. (For enkelhets skyld måler vi absolutt alle ingredienser i gram.)

## Funksjonalitetskrav 1: Registrering og uthenting
Lag en metode for å registrere en ny ingrediens. Ta alt utenom ID som parameter, ID'en bør genereres av systemet.
Lag en metode for å registrere en ny oppskrift. Ta alt utenom ID og dato som parameter, ID'en bør genereres av systemet, og systemet bør automatisk lagre dagens dato som registreringsdato.
Lag en metode som oppdaterer lagerbeholdningen for en ingrediens, ved å ta inn ID på ingrediens og et antall gram som legges til i lageret.
Lag en metode som henter ut alle oppskrifter for en gitt produktklassifisering, sortert alfabetisk på navn.
Lag en metode som henter ut alle oppskrifter som ble registrert mellom to datoer. (Trenger ikke sorteres.)

## Funksjonalitetskrav 2: Kakeproduksjon
Lag en metode som sjekker om det er mulig å produsere et gitt antall av en gitt oppskrift (oppgitt ved ID), gitt nåværende lagerbeholdning. F.eks. om vi har nok ingredienser til å produsere 9 kransekaker.
Lag en metode som, dersom det er mulig, fjerner nok ingredienser fra lagerbeholdningen til å produsere et gitt antall av en gitt oppskrift. (Så om kransekaker krever 2 gram egg og 1 gram sukker, så skal denne metoden fjerne 18 gram egg og 9 gram sukker dersom vi ønsker å produsere 9 kransekaker - men bare dersom det er nok ingredienser på lager.)

## Funksjonalitetskrav 3: Feilhåndtering
Når det oppstår problemer i systemet, f.eks. om det oppgis ugyldig input til en metode (f.eks. negativt antall gram for en ingrediens), så skal dette behandles fornuftig.mmet.
