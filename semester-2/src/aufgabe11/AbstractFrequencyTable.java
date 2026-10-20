package aufgabe11;

public abstract class AbstractFrequencyTable<T> implements FrequencyTable<T> {
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	@Override
    public void add(T w) {
		add(w, 1);
    }

	@Override
	public void addAll(FrequencyTable<? extends T> fq) {
		// Ihr Code:
		for (int i = 0; i < fq.size(); ++i) {
			Word t = fq.get(i);
			//this.add(t.getWord(), t.getFrequency());
		}
	}

	@Override
	public void collectNMostFrequent(int n, FrequencyTable <? super T> fq) {
		// Ihr Code:
		fq.clear();
		int limit = Math.min(n, this.size());

		for (int i = 0; i < limit; ++i) {
			Word t = this.get(i); // Берем самый частый элемент из СЕБЯ (this)
		//	fq.add(t.getWord(), t.getFrequency());
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		// Ihr Code:
		for (int i = 0; i < size(); ++i) {
			Word t = this.get(i);
			s.append(t.toString());
			if (i != size() - 1) {
				s.append(", ");
			}
		}
		s.append("} size = ").append(size());
		return s.toString();
	}
}
