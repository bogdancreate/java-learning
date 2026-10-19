/**

Paket mit der zweiten Übungsaufgabe zu Programmiertechnik 1 für AIN/1.
<p>
Innerhalb der Methode
{@link aufgabe2.Histogramm#main aufgabe2.Histogramm.main}
wird dazu aufgefordert, Zahlen zwischen 1 und 6 einzugeben.
Die Zahlen werden in einer Schleife einzeln eingelesen,
bis das Eingabeende erkannt wird.
</p>
<p>
Beispiel (Eingaben sind fett gedruckt:
<code><b>1 2 3</b> ...</code>):</p>
<pre>
    Ganze Zahlen zwischen 1 und 6 eingeben (Ende mit Strg-D):
    <b>1 1 2 2 2 3 3 3 3 4 4 5 5 5 6 7</b>
    Falsche Eingabe wird ignoriert: 7
    <b>3 5 5 5 5 5 5 5 5 6</b>
    <b>Strg-D</b>
    Histogramm:
    11 (2)
    222 (3)
    3333$ (5)
    44 (2)
    5555$5555$5 (11)
    66 (2)
</pre>
<p>
Das Programm z&auml;hlt also, wie oft jede der Zahlen zwischen 1 und 6
eingegeben hat, und gibt zum Schluss die Z&auml;hlerst&auml;nde
als Histogramm aus. Bei Zahlen au&szlig;erhalb des Bereichs von 1 bis 6
gibt es die gezeigte Fehlermeldung aus und ignoriert die Zahl.
</p>
<p>
Im obigen Beispiel wurde die Eins zweimal eingegeben
(deshalb zweimal die 1 und die Anzahl 2 in Klammern), die Zwei dreimal
(deshalb dreimal die 2 und die Anzahl 3 in Klammern) usw.
An jeder 5. Stelle der Histogrammbalken wird statt der Ziffer ein $
ausgegeben (siehe die Zahlen 3 und 5). Die Sieben wurde ignoriert.
</p>

@see
<a href="../../Aufgabe2.html" target="_blank">Anleitungen zu Aufgabe 2</a>

@author  H.Drachenfels
@version 4.8.2025

*/

package aufgabe2;

