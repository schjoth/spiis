# CONTRIBUTING.md

Dette dokumentet beskriver hvordan vi bruker GitLab som verktøy for smidig utvikling.

## Sprintplanlegging

Arbeidet er delt inn i sprinter, og hver sprint begynner med en sprintplanlegging.
Brukerhistorier blir valgt ut i samarbeid med produkteier, om omgjort til oppgaver av Scrum Master.
Oppgavene skal være konkrete stykker arbeid som et gruppemedlem skal kunne utføre,
og alle beslutninger relatert til oppgaven skal være avgjort før sprinten begynner.
Dette er for å hindre at mye tid går til diskusjoner underveis i sprinten.
Detaljer for oppgaven skrives ned i oppgavens GitLab issue,
og alle oppgavene i en gitt sprint tilegnes samme GitLab Milestone.
Gruppa gir også hver oppgave et estimat på arbeidsmengde i form av Story Points.
Det er et mål å unngå at oppgaver legges til underveis i sprinten,
så sprintplanleggingen må dele opp iterasjonens brukerhistorier fullstendig.
I tillegg kan oppgaver hentes fra Backlog. På GitLab vil dette si alle issues som ikke er tilegnet noen Milestone.

## Underveis i Sprinten

For å holde orden på sprintkøen brukes GitLab sin tavle-funksjon. Vi har en tavle som heter Sprint,
med kolonner for "Åpen", "Utviklingsklar", "Gjøres", "Til vurdering" og "Lukket".
For å kun vise oppgaver relatert til sprinten brukes tavlens søkefunksjon `Milestone = <Sprint-navn>`.

En oppgave begynner som "åpen", og blir flyttet til "Utviklingsklar" når master-grenen er i en slik
tilstand at oppgaven er gjennomførbar. Dette kan f.eks. være fordi oppgaven måtte vente på tidligere oppgaver.

Gruppemedlemmer velger seg ut oppgaver fra "Utviklingsklar"-kolonnen, og setter seg selv som ansvarlig.
Deretter flytter de oppgaven til "Gjøres"-kolonnen.

Husk at det er to standups i uka, der medlemmer forteller om hvilke oppgaver de jobber med,
og om det er hindringer. Disse møtene skal ikke brukes til å diskutere nye ting, men andre kan komme med hjelp.

Dersom du underveis i sprinten føler at noe burde gjøres, og det ikke har noen oppgave,
kan du lage en ny oppgave på GitLab. Den ligger da i backlog, og venter på å få en Milestone i en senere sprint.

### Arbeid på oppgaver

Når man jobber på en oppgave skal man jobbe i en egen git-gren for den oppgaven.
Navnet på git-grenen skal starte med oppgavens GitLab issue-nummer, f.eks `12-vue-login`.
Lag en ny gren med følgende kommando:
```sh
git checkout master #Vær sikker på at vi er på master
git pull #Vær sikker på at vi har alt det nyeste fra master
git checkout -b 123-min-nye-gren
```
Dette lager en ny gren med navn `123-min-nye-gren`, basert på nyeste utgave av master-grenen.

Du kan til enhver tid sjekke hvilken gren du er i med `git branch`, og bytte gren med `git checkout <gren-navn>`.

Når du har gjort et stykke forandringer i en gren, brukes `git commit` for å lage en "bunt" av forandringer.
Før vi kan lage en commit må forandringene være registrert i git. Den letteste måten er å ta med alle forandringer:
```sh
git status #Viser hvilke forantinger som er staged og ikke staged
git add -A #Ta med alle forandringer
git status #Viser hvilke forandringer som er med (staged)
git commit -m "feat(#12): legge til http-klient i vue"
```
Alle commits skal inneholde type commit, oppgavenummer og en forklarende setning, f.eks:
```
feat(#12): legge til http-klient i vue 

Dette er en frivillig beskrivelse dersom mer info er nyttig
```
Den første linjen er den viktigste, resten er frivillig. Det første ordet beskriver typen forandring.
 - `feat` for ny funksjonalitet
 - `fix` for bugfixer eller fiksing av skrivefeil
 - `style` for fiksing av kodestil
 - `docs` for skriving av dokumentasjon
 - `refactor` for omstrukturering av kode
 - `test` for endringer i tester
 - `build` for ting relatert til byggesystemet
 - `ci` for ting relatert til GitLab-pipeline
 - `chore` for alt annet

Deretter kommer `#<oppgavenummeret>` i parentes, etterfulgt av en setning om foranringen.
Setningen skal ikke ha stor forbokstav, ikke ha punktum, og skal passe inn i formatet
```
Dersom denne forandringen flettes inn i prosjektet, vil den ___________________.
```

Når du har gjort ferdig oppgaven, pakket det inn i én eller flere commits, og ønsker å laste forandringene opp på GitLab, bruk:
```sh
git push -u origin <gren-navn>
```

**Merk:** Husk å teste og linte koden før du laster opp, med mindre du vet at den er uferdig.

### Vurdering av oppgaver

Når et gruppemedlem sier seg ferdig med en oppgave skal det opprettes en GitLab Merge Request fra oppgave-grenen til master.
GitLab kommer automatisk til å merke at grenen hører til oppgaven, basert på gren-navnet.
Merge Requesten må inneholde en beskrivelse av hva den gjør, men den trenger ikke være lang, siden den lenker til oppgaven.
Oppgaven flyttes deretter til tavle-kolonnen "Til vurdering", og et annet gruppemedlem må gå gjennom og kontrollere kvalitet.
Dersom noen automatiske tester feiler, kan man ikke flette grenen inn i master. Dersom oppgaven har lagt til ny funksjonalitet,
men ikke lagt til tilhørende tester, må de legges til før grenen kan flettes inn.

Man trenger ikke lage ny Merge Restest selv om man gjør forandringer. Bare fortsett å gjøre commits på oppgave-grenen.

Når oppgaven er godkjent skriftlig av et annet gruppemedlem kan fletteforespørselen flettes inn i master,
og oppgaven blir automatisk lukket av GitLab.
