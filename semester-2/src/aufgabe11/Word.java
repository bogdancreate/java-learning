package aufgabe11;

public class Word<T> {
	final private T word;
	private int freqency;
	

	public Word(T word, int f) {
		this.word = word;
		this.freqency = f;
	}

	public T getWord() {
		return word;
	}

	public int getFrequency() {
		return freqency;
	}

	public void addFrequency(int f) {
		freqency += f;
	}

	@Override
	public String toString() {
		return word + ":" + freqency;
	}
}
