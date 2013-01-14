package de.unihl.algo.three_sat_crititcal_number;

import java.util.Random;
import java.util.Vector;

public class RandomSATSolver {

	private CNFFormula formula;
	private int n;

	public void RandomSatSolver(CNFFormula _formula, int _n) {
		formula = _formula;
		n = _n;
		// set new values for all variables in the formulas setting
		Vector<Boolean> setting = new Vector<Boolean>();
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			setting.set(i, random.nextBoolean());
		}
		formula.setValues(setting);
	}
	
	public Boolean checkSatisfieable(int k){
		int clause = 0;
		Boolean result = false;
		for (int i = 0;i < k*n;i++){
			//first check if the setting satisfies the problem, else get a clause which is invalid
			clause = formula.checkValid();
			if (clause > -1){
				//some clause is invalid
				
			} else {
				result = true;
				break;
			}
		}
		return result;
	}
}