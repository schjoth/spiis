# Spiis - Middagsdeling

## Organisering

Appen er delt i to deler.
 - `server/` inneholder en tjener skrevet i `Spring Boot`
 - `client/` inneholder en webapp skrevet i `Vue.js 3` 

## Bygging

For å bygge tjeneren må `maven 3.6.3`  og `Java 15` være installert. Pass på at `JAVA_HOME` ikke peker til noen annen installasjon av Java.

For å bygge klienten må `yarn 1.22.10` og `nodejs 15.6.0` være installert.

### Lokal bygging+kjøring

For å bygge og kjøre tjeneren lokalt, skriv
``` sh
mvn spring-boot:run -pl server
```
Dette starter Spring Boot-tjeneren på port `XXX`.

For å bygge og åpne webappen lokalt, gå inn i `client/` og skriv
``` sh
yarn serve
```
Dette starter en enkel webserver med Vue-nettsiden på `localhost:XXX`.

### Testing

Både klienten og tjeneren har enhetstester, som kan kjøres for begge med
``` sh
mvn test
```

I tillegg kommer verktøy for kodestil og kodekvalitet. For å kjøre alt sammen brukes
``` sh
mvn verify
```

### CI/CD

