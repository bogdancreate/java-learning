package aufgabe1;

/**
 * Klasse für Wörter mit ihren Häufigkeiten.
 * @author oliverbittel
 * @since 31.07.2023
 */
public class Element<T> {
	private final T word;
	private int freqency;
	
	/**
	 * Konstruktor.
	 * @param word Wort
	 * @param f H&auml;ufgkeit
	 */
	public Element(T word, int f) {
		this.word = word;
		this.freqency = f;
	}

	/**
	 * Liefert Wort zur&uuml;ck.
	 * @return Wort
	 */
	public T getWord() {
		return word;
	}

	/**
	 * Liefert H&auml;ufgkeit zur&uuml;ck.
	 * @return H&auml;ufgkeit
	 */
	public int getFrequency() {
		return freqency;
	}
	
	/**
	 * Addiert zur H&auml;ufgkeit f dazu.
	 * @param f H&auml;ufgkeits&auml;nderung.
	 */
	public void addFrequency(int f) {
		freqency += f;
	}

	@Override
	public String toString() {
		return word + ":" + freqency;
	}
}
