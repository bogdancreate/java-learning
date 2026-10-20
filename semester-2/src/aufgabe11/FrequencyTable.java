package aufgabe11;

public interface FrequencyTable<T> {

	int size();

	boolean isEmpty();

	void clear();

	void add(T w, int f);

	void add(T w);
	
	/**
	 * F&uuml;gt alle W&ouml;rter mit ihren H&auml;ufigkeiten aus fq zu dieser Tabelle dazu.
	 * H&auml;ufigkeiten für gleiche W&ouml;rter werden addiert.
	 * fq bleibt unver&auml;ndert.
	 * @param fq H&auml;ufigkeitstabelle.
	 */
	void addAll(FrequencyTable <? extends T> fq);

	/**
	 * Liefert das Wort mit seiner Häufigkeit zur&uuml;ck, das mit seiner H&auml;ufigkeit an Position pos steht.
	 * get(0) liefert das häufigste Wort zurück, 
	 * get(1) liefert das zweithäufigste Wort zurück, usw.
	 * @param pos Position.
	 * @return Wort mit H&auml;ufigkeit oder null, 
	 * falls die Tabelle weniger als pos-1 Elemente  enth&auml;lt.  
	 */
	Word get(int pos);

	/**
	 * Liefert die H&auml;ufigkeit des Worts w zur&uuml;ck.
	 * Falls das Wort nicht vorkommt, wird 0 zur&uuml;ckgeliefert. 
	 * @param w Wort
	 * @return H&auml;ufigkeit. 
	 */
	int get(T w);
	
	/**
	 * Speichert die n h&auml;ufigsten W&ouml;rter in fq.
	 * Falls die Tabelle weniger als n Einträge hat, werden alle W&ouml;rter in fq gespeichert.
	 * Beispiel: 
	 * Falls tab1 = {"ein":3, "das":3, "ist":2, "der:1", "die":1}, dann 
	 * gilt nach Aufruf von tab1.collectMostFrequent(3,tab2):
	 * tab2 = {"ein":3, "das":3, "ist":2}.
	 * @param fq H&auml;ufigkeitstabelle.
	 */
	void collectNMostFrequent(int n, FrequencyTable <? super T> fq);
}
