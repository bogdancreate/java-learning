/**

Paket mit der ersten Übungsaufgabe zu Programmiertechnik 1 für AIN/1.

<p>
Die Klasse {@link aufgabe1.IntVar aufgabe1.IntVar} geht in der Methode
{@link aufgabe1.IntVar#main main} wie folgt mit ganzen Zahlen um:</p>
<ul>
<li>
Der Benutzer wird aufgefordert zwei ganze Zahlen zwischen der kleinsten und
größten als <code>int</code> darstellbaren Zahl einzugeben.</li>
<li>
Die beiden eingegebenen Zahlen werden anschließend im
dezimalen, oktalen und hexadezimalen Format ausgegeben.</li>
<li>
Dann werden die Summe, die Differenz, das Produkt, der Quotient
und der Rest der ganzzahligen Division ausgegeben.</li>
<li>
Abschließend werden die Ergebnisse aller in Java möglichen
Vergleiche der beiden Zahlen ausgegeben.</li>
</ul>
<p>
Beispiel (Eingaben des Benutzers sind fett gedruckt:
<code><b>123 456</b></code>):</p>
<pre>
  Zwei ganze Zahlen zwischen -2147483648 und 2147483647 eingeben:
  <b>123 456</b>
  123 ist oktal 173 und hexadezimal 7b
  456 ist oktal 710 und hexadezimal 1c8
  123 + 456 ist 579
  123 - 456 ist -333
  123 * 456 ist 56088
  123 / 456 ist 0
  123 % 456 ist 123
  123 == 456 ist false
  123 != 456 ist true
  123 &lt; 456 ist true
  123 &lt;= 456 ist true
  123 &gt; 456 ist false
  123 &gt;= 456 ist false
</pre>

@see
<a href="../../Aufgabe1.html" target="_blank">Anleitungen zu Aufgabe1</a>

@author  H.Drachenfels
@version 3.8.2025

*/

package aufgabe1;

