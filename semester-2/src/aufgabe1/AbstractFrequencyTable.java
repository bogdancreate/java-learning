package aufgabe1;

/**
 *
 * @author oliverbittel
 */
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
		for (var x : fq) {
				this.add(x.getWord(), x.getFrequency());
		}
	}

	@Override
	public void collectNMostFrequent(int n, FrequencyTable<? super T> fq) {
		fq.clear();
		int count = 0;
		for (var x : this) {
			if (count >= n) break;
			fq.add(x.getWord(), x.getFrequency());
			count++;
		}

	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{");
		for (var x : this) {
			s.append(x).append(", ");
		}
		s.append("} size = ").append(size());
		return s.toString();
	}
}
