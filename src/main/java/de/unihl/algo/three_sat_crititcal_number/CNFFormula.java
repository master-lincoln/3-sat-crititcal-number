package de.unihl.algo.three_sat_crititcal_number;

import java.util.Random;
import java.util.Vector;

public class CNFFormula {

	
	/**
	 * a formula consists of clauses which again consist of indices of variables
	 * (which are negative if negated in the formula)
	 */
	private Vector<Vector<Integer>> formula;
	/**
	 * The values for all the variables
	 */
	private Vector<Boolean> setting;
	/**
	 * The number of variables in a clause
	 */
	private int k;
	
	
	/**
	 * Creates a new CNFformula which is different to Vector<Vector<Integer>> formula which is just a datatype of a formula
	 * 
	 * @param formula the k-cnf consists of m clauses each containing k variables
	 * @param k the number of variables in a clause
	 */
	public CNFFormula(Vector<Vector<Integer>> formula, int k) {
		this.formula = formula;
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
		// current 
		boolean satisfied = false;
		
		for ( int i=0; i<formula.size(); i++ ){
			Vector<Integer> term = formula.get(i);
			for (int g = 0; g < this.k; g++){
				satisfied = satisfied || getValue(term.get(g));
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
		boolean result = false;
		if (index > 0){
			result = setting.get(index);
		} else {
			result = !setting.get(index * -1);
		}
		return result;
	}
	
	/**
	 * 
	 * @param indexClause
	 * @param indexVar
	 */
	public void toggleValue(int indexClause, int indexVar) {
		int indexVarToChange = formula.get(indexClause).get(indexVar);
		setting.set(indexVarToChange, !setting.get(indexVarToChange));
	}
	
	public void setValues(Vector<Boolean> values) {
		setting = values;
	}


	public static CNFFormula generateRandom(int n, int m, int k) {
		// TODO riv333 please code here
		return null;
	}

}
