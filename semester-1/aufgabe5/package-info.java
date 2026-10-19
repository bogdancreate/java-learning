/**

Paket mit der fünften Übungsaufgabe zu Programmiertechnik 1 für AIN/1.

<p>
Innerhalb der Methode
{@link aufgabe5.Notenspiegel#main aufgabe5.Notenspiegel.main}
werden die Namen von Fächern mit zugehörigen Noten in eine
verkettete Liste einlesen. Noten sind Wertobjekte (value objects)
der Klasse {@link aufgabe5.Note aufgabe5.Note}.
Die verkettete Liste {@link aufgabe5.FachnotenListe aufgabe5.FachnotenListe}
enthält Objekte der Klasse {@link aufgabe5.Fachnote aufgabe5.Fachnote}.
Nach Eingabeende wird unter der Überschrift NOTENSPIEGEL
ein tabellarischer Notenspiegel ausgeben.
In der ersten Spalte stehen linksbündig untereinander die Fachnamen,
in der zweiten Spalte linksbündig untereinander die Noten und
in der dritten Spalte linksbündig ein Vermerk, ob das jeweilige Fach
bestanden ist und wenn ja, ob mit Bestnote.
</p>
<p>
Beispiel (Benutzereingaben sind fett gedruckt):</p>
<pre>
Faecher mit Noten zwischen 10 und 50 eingeben (Ende mit Strg-D/Strg-Z):
<b>Mathematik1 37</b>
<b>Programmiertechnik1 23</b>
<b>Digitaltechnik 1,0</b>
<b>Systemmodellierung 5,0</b>

NOTENSPIEGEL
Systemmodellierung     5,0    nicht bestanden
Digitaltechnik         1,0    mit Bestnote bestanden
Programmiertechnik1    2,3    bestanden
Mathematik1            3,7    bestanden
</pre>

@see
<a href="../../Aufgabe5.html" target="_blank">Anleitungen zu Aufgabe 5</a>

@author  H.Drachenfels
@version 21.8.2025

*/

package aufgabe5;

