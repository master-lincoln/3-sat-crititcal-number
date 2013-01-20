package de.unihl.algo.three_sat_crititcal_number;

import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

public class RandomSATSolver {
	
	private static final Logger log = Logger.getLogger(RandomSATSolver.class);

	private static void init(CNFFormula formula, int n) {
		// set new values for all variables in the formulas setting
		Vector<Boolean> assignment = new Vector<Boolean>();
		Random random = new Random();
		
		for (int i = 0; i < n; i++) {
			assignment.add(random.nextBoolean());
		}
		formula.setValues(assignment);
	}
	
	public static Boolean isSatisfiable(CNFFormula formula, int n){
		
		Random random = new Random();
		Boolean result = false;
		int restarts = 0;
		double k = formula.getK();
		
		// siehe Lemma 2.4 im Skript
		double p = 0.5 * Math.pow( ( 0.5*( k/(k-1)) ) , n );
		int maxRestarts = (int)(1f/p);
		log.trace("Max restarts for n="+n+": "+maxRestarts);
		
		while ( !result && restarts < maxRestarts ) {
			init(formula, n);
			for (int i = 0;i < 3*n;i++){
				// check if the assignment satisfies the problem, else get a random clause which is invalid
				int clause = formula.checkValid();
				//some clause is invalid
				if (clause > -1){
					//change a random variable from a random invalid clause
					formula.toggleValue(clause, random.nextInt((int)k));
				} else {
					log.trace( formula+" is satisfied with assignment "+formula.getValues() );
					result = true;
					break;
				}
			}
			restarts++;
		}
		log.trace("Actual restarts for n="+n+": "+restarts);
		if (!result)
			log.trace( formula+" doesn't seem to be satisfieable" );
		return result;
	}
}
