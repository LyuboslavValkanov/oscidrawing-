Projektbeschreibung :

Oszilloskope sind hauptsächlich dafür bekannt, dass sie zum Visualisieren des Verlaufs von elektrischen Spannungen verwendet werden. Analoge Oszilloskope schießen mit einem Elektronenstrahl auf den Anzeigebildschirm, der durch Fluoreszenz dort aufleuchtet, wo die Elektronen auftreffen.
Mittels Elektromagneten kann der Elektronenstrahl durch zwei elektrische Signale umgeleitet werden. Ein Signal bestimmt die Verschiebung in x-Richtung, das andere in y-Richtung.
Im klassischen Anwendungsfall ist das x-Signal ein Sägezahn-Signal, das den Elektronenstrahl "langsam" von links nach rechts fahren lässt und dann sehr schnell wieder nach links springt. Dadurch, dass während dieser Links-Rechts-Bewegung das zweite Signal die y-Verschiebung (Höhe auf dem Bildschirm) bestimmt, wird der Spannungsverlauf des zweiten Signals auf dem Bildschirm sichtbar.
Es können aber auch beide Signale beliebig gewählt werden und somit die Position des Elektronenstrahls beliebig auf dem Bildschirm gesteuert werden.
Die selben beiden Signale können aber auch gleichzeitig als Töne interpretiert werden, also als ein Stereo-Audiosignal. Zum Beispiel bestimmt der Spannungsverlaufs des einen Signals die x-Position auf dem Oszilloskop und gleichzeitig Töne für den linken Lautsprecher, während der Spannungsverlauf des anderen Signals die y-Position des Elektronenstrahls manipuliert und als Ton auf dem rechten Lautsprecher ausgegeben wird.
Einige Leute haben sich eingehend damit beschäftigt, Stereosignale zu erzeugen, die einerseits interessante Bilder auf einem Oszilloskop zeichnen, sich gleichzeitig aber auch musikalisch anhören. Dieses Video gibt einige interessante Einblicke dazu.
Das Hauptziel dieser Aufgabe ist es, eine SignalFactory-Klassen zu implementieren, die einige Methoden anbietet, die bei der Erstellung von Signalen helfen, um letztendlich verschiedene Muster und Effekte auf dem Oszilloskopbildschirm anzuzeigen.
Um die erzeugten Signale gut untersuchen zu können werden vorher zwei Plotting-Varianten implementiert. Einerseits wird mit SignalTimePlotter eine Klasse implementiert, die den Verlauf von Signalen über die Zeit plottet (ähnlich der klassischen Verwendung eines Oszilloskops), andererseits erlaubt Osci2DPlotter die Bewegung des Elektronenstrahls eines Oszilloskops, die durch ein Stereosignal verursacht wird, zu visualisieren.
Damit die geplotteten Bilder auch betrachtet werden können, wird zuvor ein Exporter implementiert, der diese als PNG-Dateien abspeichern kann.
Das implementierte 2D-Plotting funktioniert wunderbar für "statische" Zeichnungen, sobald aber Bewegung stattfindet, ist schwierig zu erkennen was passiert, weil alle Elektronenstrahlpositionen im Plot eingezeichnet sind. Deshalb wird zudem ein Exporter implementiert, der es erlaubt Stereosignale als Audiodateien abzuspeichern und diese dann entweder auf einem echtem Oszilloskop oder einem Oszilloskop-Emulator anzeigen zu lassen.





Dieses Projekt bietet ein Toolkit zur Erzeugung, Visualisierung und Ausgabe von Signalen, die auf einem Oszilloskop dargestellt werden können. Dabei wird der Ansatz von Oscilloscope Music genutzt: Stereosignale dienen gleichzeitig als Audioquelle und als Steuersignale für die x- und y-Ablenkung des Elektronenstrahls.

*Features
🔧 SignalFactory:
 - Eine zentrale Klasse zum Erzeugen verschiedener Signalformen und Muster. 
 - Geeignet für statische Figuren, Animationen und experimentelle Effekte auf dem Oszilloskop.

📈 Visualisierung:
SignalTimePlotter
-Visualisiert Signale über die Zeit – ähnlich dem klassischen Einsatzzweck eines Oszilloskops.

Osci2DPlotter:
-Stellt die 2D-Bewegung des Elektronenstrahls dar, basierend auf einem Stereosignal.
-Ideal, um Figuren, Lissajous-Muster oder komplexe Zeichnungen zu analysieren.

📤 Exporter:
-PNG-Exporter
-Speichert geplottete Visualisierungen als PNG-Bilder.

:Audio-Exporter
 -Exportiert erzeugte Stereosignale als Audiodateien, um diese auf echten Oszilloskopen oder Emulatoren anzeigen zu lassen.

Hintergrund
Analoge Oszilloskope steuern den Elektronenstrahl durch zwei Signale:

x-Ablenkung
y-Ablenkung

Statt eines festen Sägezahnsignals für die x-Achse können beliebige Signalverläufe genutzt werden. Dadurch lassen sich Formen, Kurven und Animationen direkt auf dem Bildschirm eines Oszilloskops zeichnen. Werden diese Signale zusätzlich als Stereo-Audio interpretiert, ergeben sich klanglich interessante und visuell beeindruckende Effekte.

*Ziel

Das Projekt stellt Werkzeuge bereit, um:
 - eigene Stereosignale zu generieren
- zeitbasierte und 2D-Darstellungen zu plotten
- Ergebnisse als Bilder oder Audiodateien zu exportieren
 - Muster und „Oszilloskop-Kunst“ zu erzeugen und zu analysieren


