package de.unihl.algo.three_sat_crititcal_number;

import java.util.Random;
import java.util.Vector;

public class RandomSATSolver {

	private static CNFFormula formula;
	private static int n;

	public static void init(CNFFormula _formula, int _n) {
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
	
	public static Boolean isSatisfiable(CNFFormula _formula, int _n){
		init(formula, n);
		int k = formula.getK();
		int clause = 0;
		Random random = new Random();
		Boolean result = false;
		for (int i = 0;i < k*n;i++){
			//first check if the setting satisfies the problem, else get a random clause which is invalid
			clause = formula.checkValid();
			if (clause > -1){
				//some clause is invalid, so change a random variable in a random clause (clause is given by the CNFFormula as a return value)
				formula.toggleValue(clause, random.nextInt(k));
			} else {
				result = true;
				break;
			}
		}
		return result;
	}
}
