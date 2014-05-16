package nl.rommert.sandbox;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class Parser 
{
    public static void main( String[] args )
    {
        System.out.print("Please type a formula: ");
        Scanner scanner = new Scanner(System.in);
        String formula = scanner.nextLine();
        double result = Parser.parse(formula);
        System.out.println("Result: " + result);
        scanner.close();
    }

	public static double parse(String formula) {
		Character operator = findOperator(formula);
		if (operator == null) {
			return Double.valueOf(formula);
		}
		
		String leftHand = findLeftHand(formula, operator);
		String rightHand = findRightHand(formula, operator);
		if ('+' == operator.charValue()) {
			return parse(leftHand) + parse(rightHand);
		}
		if ('-' == operator.charValue()) {
			return parse(leftHand) - parse(rightHand);
		}
		if ('*' == operator.charValue()) {
			return parse(leftHand) * parse(rightHand);
		}
		if ('/' == operator.charValue()) {
			double right = parse(rightHand);
			if (right == 0.0) {
				throw new IllegalArgumentException();
			}
			return parse(leftHand) / right;
		}
		return 0;
	}

	private static String findLeftHand(String formula, Character operator) {
		return formula.substring(0, formula.indexOf(operator));
	}

	private static String findRightHand(String formula, Character operator) {
		return formula.substring(formula.indexOf(operator)+1, formula.length());
	}

	private static Character findOperator(String formula) {
		if (formula.contains("+")) {
			return new Character('+');
		}
		if (formula.contains("-")) {
			return new Character('-');
		}
		if (formula.contains("*")) {
			return new Character('*');
		}
		if (formula.contains("/")) {
			return new Character('/');
		}
		return null;
	}
}
