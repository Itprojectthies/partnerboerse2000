# Aufgabe

Modul: 335040 lnformationssysteme 

Hochschule der Medien Stuttgart

Aufgabenstellung IT-Projekt SS 2016



#### Beschreibung des Zielsystems

Jede Gruppe hat die Aufgabe, ein verteiltes System zur Realisierung einer Partnerbörse, wie z. B. Friendscout oder Parship, zu realisieren. 
Profile stellen die Teilnehmer einer Partnerbörse dar. Zu jedem Profil gibt es einen Merkzettel, mit dessen Hilfe sich jeder Teilnehmer andere Profile für eine eventuelle, spätere Kontaktaufnahme merken kann. Jeder Teilnehmer kann einzelne, andere Teilnehmer mit einer Kontaktsperre belegen. Neben dem Profil eines Teilnehmers ist eine beliebige Anzahl weiterer Info-Objekte zu einem Profil zuordenbar, das den jeweiligen Teilnehmer weiter beschreiben soll. Die einzelnen Info-Objekte beziehen sich jeweils auf ein Eigenschaftsobjekt. Die Menge jener Eigenschaften wird durch das System vorgegeben. Es existieren zwei Formen von Eigenschaften, solche, die durch Auswahl festgelegt werden und solche mit freier Beschreibung.

Das System soll das Erstellen, Verwalten und Anzeigen einer passwortgeschützten Partnerbörse sowie deren Elemente ermöglichen.
Für eine solche Funktionsweise müssen dem System die Begriffe Profil, Eigenschaft, Auswahl, Beschreibung, Info, Merkzettel, Kontaktsperre und Ähnlichkeitsmaß bekannt sein. Objekte der vorgenannten Begriffe (Klassen) sollen in dem Zielsystem jederzeit angelegt, editiert und gelöscht werden können.
Für jedes Element kann eine Reihe von Attributen festgelegt werden. Dies sind für Profil mindestens: Nachname, Vorname, Geburtsdatum, Haarfarbe, Körpergröße, Raucher, Religion. Jedes Info-Objekt besitzt einen Text, der entweder frei formuliert wurde oder der sich aus einer Auswahleigenschaft ergibt. Jedem Info-Objekt wird die Eigenschaft zugeordnet, auf die sich das Info-Objekt bezieht.

Eine Auswahleigenschaft besitzt eine Liste von Werten, die durch die Anwender selektierbar sind. Jede Eigenschaft besitzt eine textuelle Erläuterung. Ein Merkzettel ordnet einem Interessenten, der als Profil repräsentiert wird, eine Liste von Profilen zu, für die sich der jeweilige Interessent interessiert. Jeder Teilnehmer kann beliebig viele andere Teilnehmer durch jeweils eine Kontaktsperre an der Kontaktaufnahme hindern. Kontaktsperren wirken sich auf Suchergebnisse dahingehend aus, dass gesperrte Teilnehmer das sperrende Profil nicht mehr angezeigt bekommen. Ein Ähnlichkeitsmaß besitzt einen Wert, der die prozentuale Übereinstimmung von Profilattributen und weitern Info-Objekte zweier Teilnehmer darstellt. Somit ist es erforderlich, für jedes dieser Maße zwei Profile, nämlich das Referenzprofil sowie das Vergleichsprofil, zuweisen zu können. 

Wie zu Beginn dieses Abschnitts erwähnt, soll es sich bei dem Zielsystem um ein verteiltes System handeln, auf das von mehreren Benutzern simultan zugegriffen werden kann. Im Zuge dessen soll eine dreischichte Architektur realisiert werden (vgl. Abbildung 1). Die unterste Schicht wird die Datenbankschicht sein. Diese Schicht sorgt für die so genannte Persistenz der mit dem System gehandhabten Daten. Hier soll mit Hilfe von Google Cloud SQL bzw. mySQL eine relationale Datenbank entworfen und implementiert werden, die sämtliche Daten des Systems aufnehmen kann. 

Die oberste Schicht wird die Präsentationsschicht sein. Sie wird durch zwei getrennte Clients mit graphischer Benutzungsschnittstelle (engl.: Graphical User Interface, GUI) realisiert. Der erste Client dient der Interaktion mit der Partnerbörse und ihren Elementen. Der zweite Client ist ein einfacher „Report-Generator“, mit dessen Hilfe die durch die Nutzung entstandenen Daten sehr einfach zur Anzeige gebracht werden  können. Die Clients werden nachfolgend als Editor (Client 1) und als Report-Generator (Client 2) bezeichnet.
Es ist darauf zu achten, dass die Clients eine klare Aufteilung von Funktionsbereichen besitzen. Zur Navigation in einer größeren Datenmenge (Editor) bietet sich z. B. die Verwendung einer scroll-baren, listen- oder baumartigen Darstellung an.

Zum Erzeugen von Objekten sollte für jede Klasse (z. B. Profil, Merkzettel, Info) eine spezifische Interaktionsform existieren, mit deren Hilfe die jeweiligen Objekte erzeigt oder gelöscht werden können. Die Interaktion wird durch Auswahl von Schaltfläche oder Selektion aus Listendarstellungen initiiert. Die Zuordnung von z. B. Profilen zu Merkzetteln ergibt sich aus der Platzierung und Auswahl der jeweiligen Interaktionselemente. Die bloße Eingabe von werten in Zahlen- oder Textform, wie sich z. B. von Primärschlüsseln relationaler Datenbanken bekannt sind, ist hier zur Selektion nicht ausreichend.

Die Reports sollen in HTML-Form ausgegeben werden. Es ist nicht erforderlich, mit Hilfe der einzelnen Abschnitte eines Reports eine weitere Navigation in der Datenbasis zu ermöglichen. Ein Report ist also eine einmalige, statische Ausgabe.

Als Reporting soll das System strukturierte Suchprofile handhaben können sowie die Möglichkeit bieten, Partnervorschläge mit umfassenden Informationen in einer geeigneten, aggregierten Listenform auszugeben.
Die mittlere Schicht nimmt die Applikationslogik (auch Anwendungslogik oder Business-Logik genannt) auf. Sie enthält die Algorithmen, Regeln und Strukturen, die notwendig sind, um die Elemente (Profile, Merkzettel, Infos, etc.) und Funktionen (Zuordnen, Erstellen, Löschen, Modifizieren, etc.) einer Anwendung beschreiben zu können. Damit erfolgt eine Trennung von den anderen Funktionen des Systems, die sich z. B. mit der äußeren Darstellung oder der internen Abspeicherung der Daten befassen. Für eine Applikationslogik lassen sich somit mehrere Präsentationslogiken und mehrere Speicherlogiken definieren.
Die mittlere Schicht stellt den sog. Applikationsserver dar. Er soll der Präsentationsschicht mindestens folgende Dienste anbieten:

1. Anlagen, Editieren und Löschen von Profilen, Merkzetteln, Kontaktsperren, Infos, Eigenschaften, Auswahlen, Beschreibungen, Suchprofilen
2. Zuordnung zwischen Profilen, Infos, Eigenschaften, Merkzetteln und Kontaktsperren. Für das Umgehen mit Nutzerdaten soll auf die Google-Accounts-API zurückgegriffen werden.
3. Abfrage von bislang nicht angesehen Partnervorschlägen geordnet nach Ähnlichkeitsmaß
4. Abfrage von Partnervorschläge anhand von Suchprofilen geordnet nach Ähnlichkeitsmaß.

1)+2) werden von dem Editor 3)+4) durch den Report-Generator genutzt. Weitere Funktionen der Applikationslogik können bei Bedarf hinzugefügt werden. Es ist in dem gefsamten System auf die Einhaltung der referentiellen Integrität zu achten.
Die oben genannte Funktionalität erfordert, dass sich die Applikationslogik in Kontakt mit der Datenbank befindet, um die nötigen Daten aus der Datenbank ablegen zu können. Die Datenbankschicht wird demzufolge mit dem Datenbank-Server sowie seiner Anbindung an den Applikationsserver (Mapper-Klassen) realisiert, der Zugriff auf die abgespeicherten Daten als Dienst zur Verfügung stellt. Als Datenbank-Server ist Google Cloud SQL zu verwenden. Hierzu müssen die Gruppen mindestens ein Benutzerkonto bei diesem Dienst anlegen. Applikationsserver und Datenbankserver müssen bei der Präsentation vollständig in der Cloud laufen. Die Clients sind auf einem beliebigen Rechner, der auf die Cloud zugreifen kann, vorzuführen.

Die Teilnehmer haben dafür Sorge zu tragen, dass die Präsentation vorab in dem ihnen genannten Raum lauffähig ist. Hierzu bietet sich ein Test an. Der Raum wird den Teilnehmern rechtzeitig vorher bekannt gegeben. Termine für Tests sind rechtzeitig zu vereinbaren.

Die Aufteilung in drei Schichten bietet einige wichtige Vorteile gegenüber z. B. zweischichtigen Ansätzen. Zum einen wird durch die Konzentration sämtlicher Applikationslogik auf die mittlere Schicht eine Entkopplung der Präsentationsschicht von der Datenbank erreicht. Zum anderen können die Präsentations-Clients verändert werden, ohne die Applikationslogik modifizieren zu müssen. Darüber hinaus ist es so möglich, auf derselben Applikationslogik unterschiedliche Clients aufsetzen zu können (z. B. Editor und Report-Generator). Dies könnten einerseits Clients bzgl. Unterschiedlicher Medien (Webbasiert oder dedizierte Applikationen) oder bzgl. Unterschiedlicher Benutzerrollen (z. B. für Reporting oder Systemnutzung) sein. Aus diesem Grund ist unbedingt darauf zu achten, dass die Clients keinerlei Applikationslogik enthalten. Diese ist ausschließlich im Applikationsserver zu finden!

#### Im Folgenden sollen noch einige weitere, schicht-spezifische Anforderungen erläutern werden.

Die Präsentationsschicht soll mit Hilfe des Google Web Toolkit (kurz: GWT) realisiert werden. Hierbei dürfen keine sog. GUI-Builder verwendet werden!

Ein Teil der Datenbankschicht soll relationale Strukturen (Datenbanktabellen, Tupel, usw.) in Objekte und vice versa transformieren.

Die Applikationsschicht soll Datenstrukturen und Algorithmen realisieren, mit deren Hilfe überhaupt erst das Management einer Partnerbörse in dem System beschrieben werden kann. Dabei ist in dieser Schicht besonders darauf Wert zu legen, dass Randbedingungen der Anwendung eingehalten werden.

Zudem soll die Applikationsschicht mit der Präsentationsschicht mittels des GWT REomte Procedure Call (kurz: GWT RPC) kommunizieren. Die Applikationslogik ist auf Basis von Google App Engine zu realisieren. Die Kommunikation mit dem Datenbankmanagementsystem erfolgt gemäß den Vorgaben von Google Cloud SQL mittels der Java Database Connectivity (JDBC).

Die Datenbankschicht soll, wie bereits oben erwähnt, mit Hilfe des Datenbank-Management-Systems Google Cloud SQL bzw. mysQL realisiert werden. Hierzu sind geeignete Entitty-Relationsship-Modelle in Chen-Notation zu erstellen und in einer Datenbank abzubilden.

# Team
  - Daniel Volz (dv021)
  - Christopher Funke
  - Christina Burggraf
  - Ulrike Dieterle
  - Benjamin Henn
  - Carolin Tröster

### Programme und Bibliotheken

Das Projekt wurde mit den folgenden Bibliotheken und Programmen realisiert:

* [GWT] - Google Web Toolkit
* [GAE] - Google App Engine
* [Java] - Java 1.7
* [Eclipse] - OpenSource IDE
* [Google Eclipse Plugin] - Google Plugin für Eclipse
* [Purecss] - Simples css-Framework
* [FontAwesome] - Kostenloses Iconset
* [Toastr] - Schöne Toastnotifications
* [SequelPro] - OSX App für SQL-Datenbanken

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [Google Eclipse Plugin]: <https://developers.google.com/eclipse/>
   [GWT]: <http://www.gwtproject.org/>
   [GAE]: <https://cloud.google.com/appengine/>
   [Purecss]: <http://purecss.io/>
   [Eclipse]: <https://eclipse.org/>
   [FontAwesome]: <http://fontawesome.io/>
   [Toastr]: <https://github.com/CodeSeven/toastr>
   [SequelPro]: <http://www.sequelpro.com/>
   [Java]: <http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html>
  
