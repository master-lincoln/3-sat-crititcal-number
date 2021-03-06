package de.unihl.algo.three_sat_crititcal_number;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class CNFFormula {

	
	/**
	 * a formula consists of clauses which again consist of indices of variables
	 * (which are negative if negated in the formula)
	 */
	private Vector<Vector<Integer>> formula = new Vector<Vector<Integer>>();

	/**
	 * Buffer for the construction of random CNFFormular
	 */
	private static List<Integer> randomVarBuffer = new Vector<Integer>();

	/**
	 * The values for all the variables
	 */
	private Vector<Boolean> assignment;
	/**
	 * The number of literals in a clause
	 */
	private int k;
	
	
	/**
	 * Creates a new CNFformula which is different to Vector<Vector<Integer>> formula which is just a datatype of a formula
	 * 
	 * @param formula the k-cnf consists of m clauses each containing k literals
	 * @param k the number of literals in a clause
	 */
	public CNFFormula(Vector<Vector<Integer>> formula, int k) {
		this.formula = formula;
		this.k = k;
		
		assert(formula.get(0).size() == k);
	}
	
	public CNFFormula(int k) {
		// TODO Auto-generated constructor stub
		this.k = k;
	}
	
	public int getK(){
		return this.k;
	}


	/**
	 * Checks if the CNF formula is valid
	 * @return -1 if settings satifies the formula, or a random index of an unsatified clause
	 */
	public Integer checkValid(){
		int result = -1;
		// indices of unsatisfied clauses
		Vector<Integer> falseTerms = new Vector<Integer>();
		
		// each clause
		for ( int i=0; i<formula.size(); i++ ){
			boolean satisfied = false;
			Vector<Integer> clause = formula.get(i);
			// each literal
			for (int g = 0; g < this.k; g++){
				satisfied = satisfied || getValue(clause.get(g));
			}
			if (!satisfied){
				falseTerms.add(i);
			}
		}
		if (falseTerms.size() != 0){
			Random r = new Random();
			result = r.nextInt(falseTerms.size());
		} 
		return result;
	}
	
	
	/**
	 * Returns the value of the variable with a given index
	 * @param index
	 * @return
	 */
	private Boolean getValue(int index){
		if ( index==0 ) throw new UnsupportedOperationException("No x_0 allowed");
		Boolean result =  index > 0 ? assignment.get(index-1) : !assignment.get(Math.abs(index+1));
		return result;
	}
	
	/**
	 * 
	 * @param indexClause
	 * @param indexVar
	 */
	public void toggleValue(int indexClause, int indexVar) {
		int indexVarToChange = Math.abs(formula.get(indexClause).get(indexVar)) -1;
		assignment.set(indexVarToChange, !assignment.get(indexVarToChange));
	}
	
	public void setValues(Vector<Boolean> values) {
		assignment = values;
	}
	
	public Vector<Boolean> getValues() {
		return assignment;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		//each clause
		for (Vector<Integer> clause : formula) {
			res += "(";
			
			// each variable
			for (Integer i : clause) {
				res += i<0 ? "¬" : "";
				res += "x" + toSubscriptNumberString(Math.abs(i));
				res += "\u2228"; // or
			}
			res = res.substring(0, res.length()-1); // remove last postfix
			
			res += ")\u2227"; // and
		}
		res = res.substring(0, res.length()-1); // remove last postfix
		return res;
	}
	
	private String toSubscriptNumberString(int n) {
		char subscripts[] = new char[10];
		int i=0;
		for (char c = '\u2080'; c <= '\u2089'; c++) {
		    subscripts[i] = c;
		    i++;
		}
		String res = "";
		String num = String.valueOf(n);
		for (i=0; i<num.length(); i++) {
			res += subscripts[Integer.parseInt(num.substring(i, i+1))];
		}
		
		return res;
	}
	
	/**
	 * Creates a random CNF-Formula
	 * @param type Distribution type ([i])
	 * @param num_clauses Number of clauses (m)
	 */
	public void randomizeFormula(int type, int num_variables ,int num_clauses){
		initRandomVarBuffer(num_variables);		
		
		formula.clear();
		
		if (type != 1 && k>randomVarBuffer.size()) return;
		Random r = new Random();
		Vector<Integer> removedVars = new Vector<Integer>();
		
		for (int i=0; i<num_clauses; i++){
			Vector<Integer> clause = new Vector<Integer>();
			for (int j=0; j<k; j++){
				int index = r.nextInt(randomVarBuffer.size());
				int varID = randomVarBuffer.get(index);
				int hvarID = varID; 
				if (r.nextInt(2)==0) hvarID = -hvarID;
				clause.add(hvarID);
				if (type != 1){
					randomVarBuffer.remove(index);
					removedVars.add(varID);					
				}
			}
			formula.add(clause);
			if (type != 1) {
				randomVarBuffer.addAll(removedVars);
				removedVars.clear();
			}
		}
	}
	
	/**
	 * Return the representation of the formula
	 * @return the representation of the formula
	 */
	public Vector<Vector<Integer>> getFormula()
	{
		return formula;
	}
	
	/**
	 * Must called always, when the number of different variables changes
	 * (Usually one call at start of the program).
	 * @param num_vars number of different variables
	 */
	private static void initRandomVarBuffer(int num_vars)
	{
		if (randomVarBuffer.size() != num_vars){
				CNFFormula.randomVarBuffer.clear();
		}
		if ( randomVarBuffer.isEmpty()) {
			
			for (int i=1; i<num_vars+1; i++){
				CNFFormula.randomVarBuffer.add(i);
			}
		}
	}
}

