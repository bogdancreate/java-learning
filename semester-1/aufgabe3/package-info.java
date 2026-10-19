/**

Paket mit der dritten Übungsaufgabe zu Programmiertechnik 1 für AIN/1.

<p>
Innerhalb der Methode
{@link aufgabe3.Notenstatistik#main aufgabe3.Notenstatistik.main}
wird dazu aufgefordert, Prüfungsnoten einzugeben.
Ausgabe ist eine Statistik zum Prüfungsergebnis.
</p>
<p>
Beispiel (Eingaben sind fett gedruckt):</p>
<pre>
   Noten Ganze,Zehntel oder Ganze.Zehntel eingeben (Ende mit Strg-D/Strg-Z):
   <b>1.7 2,3 2-3 3,0 3,1 2,7 5.0 6.0 1 1,75 gut x,y 4.0 x,0 x.3 1,y 2.y</b>
   Note 2-3 wird wegen Formatfehler ignoriert!
   Note 3,1 wird wegen Nachkommastelle 1 ignoriert!
   Note 6.0 wird wegen Vorkommastelle 6 ignoriert!
   Note 1 wird wegen Formatfehler ignoriert!
   Note 1,75 wird wegen Formatfehler ignoriert!
   Note gut wird wegen Formatfehler ignoriert!
   Note x,y wird wegen Formatfehler ignoriert!
   Note x,0 wird wegen Formatfehler ignoriert!
   Note x.3 wird wegen Formatfehler ignoriert!
   Note 1,y wird wegen Formatfehler ignoriert!
   Note 2.y wird wegen Formatfehler ignoriert!
   <b>Strg-Z</b>
   Anzahl beruecksichtigter Noten: 6
   Anzahl Bestandene: 5
   Beste Note: 1,7
   Schlechteste Note: 5,0
   Durchschnitt Bestandene: 2,7
   Durchfallquote: 16,7%
</pre>
<p>
Zur Spezifikation der gültigen und ungütigen Eingaben sowie der Statistik
siehe die Dokumentation der Klasse
{@link aufgabe3.Notenstatistik aufgabe3.Notenstatistik}.
Bei ungültigen Eingaben gibt das Programm die im obigen Beispiel
gezeigten Fehlermeldungen aus und ignoriert die Note.
</p>

@see
<a href="../../Aufgabe3.html" target="_blank">Anleitungen zu Aufgabe 3</a>

@author  H.Drachenfels
@version 5.8.2025

*/

package aufgabe3;

