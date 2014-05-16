package nl.rommert.sandbox;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperatorTest {
	
	@Test
	public void testOperatorBySymbol() {
		assertEquals(Operator.PLUS, Operator.forSymbol('+'));
		assertEquals(Operator.MINUS, Operator.forSymbol('-'));
		assertEquals(Operator.TIMES, Operator.forSymbol('*'));
		assertEquals(Operator.DEVIDE_BY, Operator.forSymbol('/'));
		assertEquals(Operator.TO_POWER, Operator.forSymbol('^'));
		assertEquals(null, Operator.forSymbol('g'));
	}

}
