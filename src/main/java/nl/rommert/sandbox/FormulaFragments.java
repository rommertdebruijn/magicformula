package nl.rommert.sandbox;

public class FormulaFragments {
	
	private String orgFormula;
	private String leftHand;
	private Operator operator;
	private String rightHand;
	
	public FormulaFragments(String orgFormula, String leftHand,
			Operator operator, String rightHand) {
		super();
		this.orgFormula = orgFormula;
		this.leftHand = leftHand;
		this.operator = operator;
		this.rightHand = rightHand;
	}

	public String getOrgFormula() {
		return orgFormula;
	}

	public String getLeftHand() {
		return leftHand;
	}

	public Operator getOperator() {
		return operator;
	}

	public String getRightHand() {
		return rightHand;
	}
}
