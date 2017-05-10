package bbhs.appbowl2017.stack;

public class Rule {
	private int a;
	private int b;

	public Rule(int a, int b) {
		this.a = Math.min(a, b);
		this.b = Math.max(a, b);
	}

	public int getA() {
		return this.a;
	}

	public int getB() {
		return this.b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + a;
		result = prime * result + b;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Rule other = (Rule) obj;
		if (a != other.a) return false;
		if (b != other.b) return false;
		return true;
	}

	@Override
	public String toString() {
		return this.a + " with " + this.b;
	}
}