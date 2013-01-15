<<<<<<< HEAD:src/main/java/de/unihl/algo/three_sat_crititcal_number/CNFFormula.java
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
	 * Buffer for the construction of random CNFFormular
	 */
	private static Vector<Integer> randomVarBuffer;
	
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
			// TODO dynamisch das k nicht fest 3 wie hier
			satisfied = getValue(term.get(0)) || getValue(term.get(1)) || getValue(term.get(2));
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
	
	/**
	 * Creates a random CNF-Formula
	 * @param type Distribution type ([i])
	 * @param num_clauses Number of clauses (m)
	 * @param num_literals Number of literals of one clause (k)
	 */
	public void randomizeFormula(int type, int num_variables ,int num_clauses,int num_literals){
		initRandomVarBuffer(num_variables);		
		
		if (type != 1 && num_literals<randomVarBuffer.size()) return;
		Random r = new Random();
		Vector<Integer> removedVars = new Vector<Integer>();
		
		for (int i=0; i<num_clauses; i++){
			Vector<Integer> clause = new Vector<Integer>();
			for (int j=0; j<num_literals; j++){
				Integer index = r.nextInt(randomVarBuffer.size());
				Integer varID = randomVarBuffer.get(index);
				clause.add(-r.nextInt(2)*varID);
				if (type != 1){
					randomVarBuffer.remove(index);
					removedVars.add(varID);					
				}
			}
			formula.add(clause);
			if (type != 1) randomVarBuffer.addAll(removedVars);
		}
	}
	
	/**
	 * Must called always, when the number of different variables changes
	 * (Usually one call at start of the program).
	 * @param num_vars number of different variables
	 */
	static public void initRandomVarBuffer(int num_vars)
	{
		if ( randomVarBuffer == null) {
			CNFFormula.randomVarBuffer.clear();
			for (int i=0; i<num_vars; i++){
				CNFFormula.randomVarBuffer.add(i);
			}
		}
	}

}
