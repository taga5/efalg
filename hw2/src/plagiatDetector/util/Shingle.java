package plagiatDetector.util;

import java.util.Arrays;
import java.util.List;

/**
 * A Shinlge is an n-tupel of tokens. In the Scope of the PlagiatDetector, a token is simply a String/word.
 */
public class Shingle {

	private List<String> tokens;

	public Shingle(String... tokens) {
		this.tokens = Arrays.asList(tokens);
	}

	@Override
	public String toString() {
		return tokens.stream().reduce((s1, s2) -> s1 + " " + s2).orElse("");
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (!(other instanceof Shingle)) return false;
		return toString().equals(other.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
