package nl.rommert.sandbox;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Hello Parser!
 *
 */
public class Parser {
	
	private static String operators = "+-*/^";
	
    public static void main( String[] args ) {
        System.out.print("Please type a formula: ");
        Scanner scanner = new Scanner(System.in);
        String formula = scanner.nextLine();
        double result = Parser.parse(formula);
        System.out.println("Result: " + result);
        scanner.close();
    }

	public static double parse(final String orgFormula) {
		String formula = stripWhiteSpace(orgFormula); //remove whitespace
		formula = removeOuterBrackets(formula); // remove outer brackets
		formula = stripWhiteSpace(formula); // second pass to remove whitespace after brackets have been stripped
		
		//if formula starts with -, multiply the left hand side by -1. Otherwise, multiply by 1.
		int leftHandMultiplier = 1;
		if (formula.startsWith("-")){
			leftHandMultiplier = -1;
			formula = formula.substring(1);
		}
		
		FormulaFragments fragments = findFragments(formula);
		
		Operator operator = fragments.getOperator();
		if (operator == Operator.NO_OPERATOR) {
			return leftHandMultiplier * Double.valueOf(formula);
		}
		
		String leftHand = fragments.getLeftHand();
		String rightHand = fragments.getRightHand();
		switch (operator) {
			case PLUS:
				return leftHandMultiplier * parse(leftHand) + parse(rightHand);
			case MINUS:
				return leftHandMultiplier * parse(leftHand) - parse(rightHand);
			case TIMES:
				return leftHandMultiplier * parse(leftHand) * parse(rightHand);
			case DEVIDE_BY:
				double right = parse(rightHand);
				if (right == 0) {
					throw new IllegalArgumentException("Divider is 0: " + rightHand);
				}
				return leftHandMultiplier * parse(leftHand) / right;
			case TO_POWER:
				return Math.pow(leftHandMultiplier * parse(leftHand),  parse(rightHand));
			default:
				throw new IllegalArgumentException("Unexpected operator: " + operator);
		}
	}

	private static FormulaFragments findFragments(String formula) {
		// find all operators that exist on bracket level 0
		Map<Integer, Map<Operator, Integer>> operatorByWeightMap = new HashMap<Integer, Map<Operator, Integer>>();
		
		int bracketLevel = 0;
		int maxWeight = -1;
		for (int i=0;i<formula.length();i++) {
			char c = formula.charAt(i);
			if (c == '(') {
				bracketLevel++;
			}
			if (c == ')') {
				bracketLevel--;
			}
			
			if (bracketLevel == 0 && Operator.forSymbol(c) != null) {
				Operator operator = Operator.forSymbol(c);
				maxWeight = operator.getWeight() > maxWeight ? operator.getWeight() : maxWeight;
				
				if (operatorByWeightMap.get(operator.getWeight()) == null) {
					operatorByWeightMap.put(operator.getWeight(), new HashMap<Operator, Integer>());
				}
				operatorByWeightMap.get(operator.getWeight()).put(Operator.forSymbol(c), i);
			}
		}
		if (operatorByWeightMap.size() == 0) {
			return new FormulaFragments(formula, formula, Operator.NO_OPERATOR, "");
		}
		
		// pick the first (by index) of the operators with the highest weight
		Operator current = null;
		int operatorIndex = formula.length(); // biggest index
		for (Operator operator : operatorByWeightMap.get(maxWeight).keySet()) {
			if (operatorByWeightMap.get(maxWeight).get(operator) < operatorIndex) {
				operatorIndex = operatorByWeightMap.get(maxWeight).get(operator);
				current = operator;
			}
		}
		String leftHand = formula.substring(0, operatorIndex);
		String rightHand = formula.substring(operatorIndex+1, formula.length());
		FormulaFragments fragments = new FormulaFragments(formula, leftHand, current, rightHand);
		return fragments;
	}

	private static String stripWhiteSpace(final String orgFormula) {
		String formula = orgFormula;
		while (formula.startsWith(" ")) {
			formula = formula.substring(1, formula.length());
		}
		while (formula.endsWith(" ")) {
			formula = formula.substring(0, formula.length()-1);
		}
		return formula;
	}

	private static String removeOuterBrackets(String formula) {
		if (formula.startsWith("(") && formula.endsWith(")")) {
			return formula.substring(1, formula.length()-1);
		}
		return formula;
	}
}
