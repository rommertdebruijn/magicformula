package nl.rommert.sandbox;

import static org.junit.Assert.*;

import org.junit.Test;



/**
 * Unit test for simple parser.
 */
public class ParserTest {

	@Test
	public void testAddition() {
		assertEquals(3, Parser.parse("1+2"), 0.0001);
		assertEquals(2, Parser.parse("0+2"), 0.0001);
		assertEquals(0, Parser.parse("0+0"), 0.0001);
		assertEquals(27.34, Parser.parse("16.88+10.46"), 0.00001);
	}
	
	@Test
	public void testSubstraction() {
		assertEquals(1, Parser.parse("2-1"), 0.0);
		assertEquals(2, Parser.parse("2-0"), 0.0);
		assertEquals(-2, Parser.parse("1-3"), 0.0);
		assertEquals(6.42, Parser.parse("16.88-10.46"), 0.0001);
	}
	
	@Test
	public void testMultiplication() {
		assertEquals(2, Parser.parse("2*1"), 0.0001);
		assertEquals(0, Parser.parse("2*0"), 0.0001);
		assertEquals(3, Parser.parse("1*3"), 0.0001);
		assertEquals(176.5648, Parser.parse("16.88*10.46"), 0.0001);
	}
	
	@Test
	public void testDivision() {
		assertEquals(2, Parser.parse("2/1"), 0.0001);
		assertEquals(0.333333, Parser.parse("1/3"), 0.0001);
		assertEquals(1.6137, Parser.parse("16.88/10.46"), 0.0001);
	}
	
	@Test
	public void testPower() {
		assertEquals(1, Parser.parse("3^0"), 0.0001);
		assertEquals(3, Parser.parse("3^1"), 0.0001);
		assertEquals(9, Parser.parse("3^2"), 0.0001);
		assertEquals(27, Parser.parse("3^3"), 0.0001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDivisionByZero() {
		Parser.parse("2/0");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDivisionByZeroAfterManyOperation() {
		String zeroFormula = "((((3*4)/2)/2)/3)*(17/3)/(3/17))-1";
		assertEquals(0, Parser.parse(zeroFormula), 0.000000001);
		Parser.parse("2/"+zeroFormula);
	}
	
	@Test
	public void testBracketStripping() {	
		assertEquals(3, Parser.parse("(2+1)"), 0.0001);
		assertEquals(-3, Parser.parse("(2-5)"), 0.0001);
		assertEquals(4, Parser.parse("(2*5) - (3*2)"), 0.0001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidClosingBracket() {	
		Parser.parse("(2+1))");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidOpeningBracket() {	
		Parser.parse("((2+1))");
	}
	
	@Test
	public void testSpaceStripping() {
		assertEquals(3, Parser.parse(" ( 3 ) "), 0.0001);
		assertEquals(3, Parser.parse(" ( 2 + 1 ) "), 0.0001);
		assertEquals(-3, Parser.parse(" ( 2 - 5 ) "), 0.0001);
	}
	
	@Test
	public void testBracketParsing() {	
		assertEquals(13, Parser.parse("((2+1)*3)+4"), 0.0001);
		assertEquals(-1, Parser.parse("(2-5)/3"), 0.0001);
	}
	
	@Test
	public void testOperatorPriority() {	
		assertEquals(22, Parser.parse("4+3*6"), 0.0001);
		assertEquals(9, Parser.parse("4+10/2"), 0.0001);
	}
	
	@Test
	public void testNegatives() {	
		assertEquals(-3, Parser.parse("-3"), 0.0001);		
		assertEquals(12, Parser.parse("-3*-4"), 0.0001);
		assertEquals(-13, Parser.parse("-5+-8"), 0.0001);
		
	}
}
