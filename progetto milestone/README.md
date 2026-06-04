# SkillSwap School

## Descrizione del progetto

SkillSwap School è un prototipo console-based sviluppato in Java che permette agli studenti di scambiare competenze scolastiche e personali.

Ogni studente può:

* registrarsi nel sistema
* offrire competenze
* richiedere aiuto su altre competenze
* trovare match compatibili
* creare uno scambio (Exchange)
* lasciare recensioni e voti dopo uno scambio completato

Il progetto utilizza una persistenza su file CSV per salvare i dati.

---

# Funzionalità implementate

## Student

* registrazione studenti
* visualizzazione elenco studenti
* profilo studente

## Skill

* gestione competenze
* categorie skill:

  * SUBJECT
  * LANGUAGE
  * SOFT_SKILL
  * TECH

## Offer e Request

* aggiunta offerte
* aggiunta richieste
* lista offerte e richieste

## Matching

* one-way matching
* swap matching reciproco
* sistema di punteggio:

  * +3 skill uguale
  * +2 livello sufficiente
  * +1 stessa classe

## Exchange

Workflow completo:

* PROPOSED
* ACCEPTED
* COMPLETED
* CANCELLED

## Review

* recensioni con voto 1–5
* commento testuale
* aggiornamento rating medio studente

## Persistenza

Salvataggio su file CSV:

* students.csv
* skills.csv
* offers.csv
* requests.csv
* exchanges.csv
* reviews.csv

---

# Architettura del progetto

## Package principali

### it.skillswap.domain

Contiene le classi del dominio:

* Student
* Skill
* Offer
* Request
* Exchange
* Review
* MatchResult

### it.skillswap.service

Contiene la logica applicativa:

* SkillSwapService
* MatchingService
* ExchangeService
* ReviewService
* IdGenerator

### it.skillswap.storage

Gestione persistenza:

* Storage
* InMemoryStorage
* FileStorage
* SkillSwapState

### it.skillswap.app

Applicazione console:

* SkillSwapApp
* ConsoleReportPrinter

---

# Come eseguire il progetto

## Compilazione

Linux:

```bash
javac -d out $(find src -name "*.java") $(find test -name "*.java")
```

PowerShell:

```powershell
javac -d out (Get-ChildItem -Path .\src, .\test -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
```

## Avvio programma

```bash
java -cp out it.skillswap.app.SkillSwapApp
```

## Avvio test

Esempio:

```bash
java -ea -cp out it.skillswap.MatchingServiceTest
```

---

# Esempio sessione console

```text
[1] Crea studente
[4] Aggiungi offer
[5] Aggiungi request
[9] One-way matches

Student ID: S1

MATCH #1
Studente trovato : Luca
Skill offerta    : Programmazione C
Score            : 6
```

---

# Testing

Sono stati implementati 12 test principali:

* MatchingServiceTest
* SwapMatchingTest
* NoSelfMatchTest
* InactiveOfferTest
* LevelValidationTest
* ExchangeServiceTest
* ExchangeAcceptTest
* ExchangeCompleteTest
* ExchangeCancelTest
* ReviewServiceTest
* DuplicateReviewTest
* StudentValidationTest

---

# Persistenza dati

I dati vengono caricati automaticamente dai file CSV presenti nella cartella `data/`.

Il sistema utilizza:

* FileStorage per persistenza reale
* InMemoryStorage per test e sviluppo

---

# Repository GitHub

Repository:
https://github.com/s10886820t/compito_milestone
