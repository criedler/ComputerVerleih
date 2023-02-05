# Projekt "MyGrover.com", `Informationssysteme 4AHITN 2022 / 2023`

## Aufgabenstellung
Es ist die Managementsoftware für einen Verleiher von Computer(-Hardware) umzusetzen.
In dieser Software soll der gesamte Kundenstamm (Privatpersonen) verwaltet werden. Neben dem Kundenstamm gibt es mehrere Computermarken (z.B. DELL, Lenovo, Apple, ...) die ebenfalls über die Software gewartet werden sollen. Jeder Marke können mehrere Modelle zugeordnet werden (z.B. Latitude, Yoga, MacBook, ...).
Kunden können Computer entleihen. Hierfür wird das Ausleihe- und das geplante Rückgabedatum gespeichert. Jeder Kunde kann im selben Zeitraum (oder in überschneidenden Zeiträumen) mehrere Computer gleichzeitig ausleihen.

> **Beispiel:** Herr Müller ist aufgrund einer Dienstreise der Suche nach einem Mietlaptop. Es soll sich dabei um einen Lenovo Yoga 7 handeln. Er benötigt ihn ab dem 26.01.2023 bis zum 02.03.2023. 
> Sein mitreisender Softwareentwickler benötigt ebenfalls ein Leihgerät. Da der Mitarbeiter später zur Dienstreise dazustößt (am 02.02.2023) wird dieser erst sieben Tage später als der von Herr Müller benötigt. Die Wahl des Softwareentwicklers fällt auf ein MacBook Pro M2 von Apple.

Die entstehenden Kosten setzen sich aus einzelnen Tagessätzen und entstandenen Ladezyklen zusammen. So gibt es folgende Regeln:
* Die Tagessätze sind je Gerät variabel und für jeden Computer getrennt zu speichern
* Die Kosten pro Ladezyklus betragen immer 0,05€.

> **Beispiel:** Herr Müller bringt am 02.03.2023 den entliehenen Laptop. Der Tagessatz für den Lenovo Yoga ist mit 7,50€ festgelegt. Der Laut Akku kamen 10 Ladezyklen hinzu. In Summe ergibt das also:
> 36 Tagessätze * 7,50€ pro Tag + 10 Ladezyklen * 0,05€ pro Ladezyklus = 270,50€.
> Für das MacBook bezahlt er am 02.03.2023 29 Tagessätze mit jeweils 13,50€. Sein Mitarbeiter hatte 18 Ladezyklen. Daraus ergibt sich ein Endbetrag von: 29 Tagessätze * 13,50€ pro Tag + 18 Ladezyklen * 0,05€ pro Ladezyklus = 392,40€.

## Umsetzung
Erstellen Sie _**VOR der Umsetzung**_ ein ER - Diagramm für die Problemstellung.

Danach erst erstellen Sie für den obenstehenden Sachverhalt eine JavaFX - Anwendung. Arbeiten Sie mit dem Spring - Framework und der darin mitgelieferten Java Persistance API. Erstellen Sie für die Entitäten jeweils eine Model - Klasse (z.B. Marke, Computer, Kunde, Ausleihung, ...) in denen Sie die Eigenschaften und die Beziehungen der einzelnen Klassen zueinander abbilden. Diese Model - Klassen sollen über die  entsprechenden Repositories und der JPA mit der Datenbank kommunizieren.

Erstellen Sie in Ihrer Anwendung Fenster zum Verwalten der Kunden (neu anlegen / bearbeiten / löschen), zum Verwalten der Computermarken (neu anlegen / bearbeiten / löschen), der Computer (neu anlegen / bearbeiten / löschen) und zum Entleihen eines PCs (entleihen / zurückbringen).

Beim Entleihen eines Coputers muss zuerst der Zeitraum gewählt werden. Anschließend werden alle Computer, die im gewählten Zeitraum noch verfügbar sind, angezeigt. Nun können die Ergebnisse wiederum nach Marke und Modell gefiltert werden. Durch einen Doppelklick auf ein Ergebnis wird ein Dialog geöffnet, in dem anschließend noch der Kunde eingetragen wird und die Entleihung in der Datenbank gespeichert wird.

Bei der Rückgabe eines Computers muss zuerst der Kunde aus einer Liste gewählt werden. Anschließend werden alle entliehenen Computer, die derzeit für den Kunden noch "offen" sind, angezeigt. Aus dieser Liste kann ein Computer gewählt werden. Nun müssen die Ladezyklen eingegeben werden und es wird der Preis berechnet und ausgegeben.

Befüllen Sie Ihre Anwendung mit sinnvollen Testdaten (Kunden, Marken, Computerhardware, Entleihungen, ...). Gerne können Sie bei Unklarheiten eigene Annahmen machen. Bitte vermerken Sie diese jedoch in Ihrer Projektdokumentation.

## docker-compose.yml
```yml
version: '3.8'

services:
  database:
    container_name: database_mygrover
    image: mysql:8.0
    command: --default-authentication-plugin=mysql_native_password --log_bin_trust_function_creators=1
    environment:
      MYSQL_ROOT_PASSWORD: rootpwd
      MYSQL_DATABASE: mygrover
      MYSQL_USER: my
      MYSQL_PASSWORD: grover
    ports:
      - '4306:3306'
    volumes:
      - ./mysql:/var/lib/mysql
```

## Abgabe
Abgabe ist am 02.03.2023 am Ende der Stunde (09:40 Uhr).
Abgabe: Projektverzeichnis inkl. ER - Diagramm und einen aktuellen SQL - Dump.
Abgabeverzeichnis: `H:\Abgabe\INSY\Computerverleih`

Für verspätete Abgaben gilt: Pro Tag Verspätung -> ein Notengrad schlechter.