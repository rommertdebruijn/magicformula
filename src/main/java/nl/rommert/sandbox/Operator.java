package nl.rommert.sandbox;

public enum Operator {
	NO_OPERATOR(9, null),
	PLUS(1, '+'),
	MINUS(1, '-'),
	TIMES(2, '*'),
	DEVIDE_BY(2, '/'),
	TO_POWER(3, '^');
	
	private int weight;
	private Character symbol;
	
	private Operator(int weight, Character symbol) {
		this.weight = weight;
		this.symbol = symbol;
	}

	public int getWeight() {
		return weight;
	};
	
	private Character getSymbol() {
		return symbol;
	}
	
	public static Operator forSymbol(char c) {
		for (Operator operator : values()) {
			if (operator.getSymbol() != null && operator.getSymbol() == c) {
				return operator;
			}
		}
		return null;
	}
}
