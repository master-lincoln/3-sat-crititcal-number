package de.unihl.algo.three_sat_crititcal_number;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

public class Main {
	
	private static Logger log;
	
	// k-CNF
	private static final int k = 3;
	
	public static void main(String[] args) throws Exception {
		log = Logger.getRootLogger();
		
		long t_avg = 0;
		int n = 9;
		
		// TODO für beide Verteilungen i
		while ( t_avg < 3000 ) {
			n += 1;
			// empirical probability for each m
			Map<Integer,Double> sigmas = new HashMap<Integer, Double>();
			t_avg = 0;
			
			// TODO more clever testing of m
			for (int m=40; m<80; m++) {
				StopWatch avgStopWatch = new Log4JStopWatch("full.n="+n+".m="+m);
				int satisfieable = 0;
				
				// 100 times
				for (int i=0;i<100;i++) {
					StopWatch stopWatch = new Log4JStopWatch("n="+n+".m="+m);
					
					CNFFormula formula = new CNFFormula(k);
					formula.randomizeFormula(1, n, m);
					satisfieable += RandomSATSolver.isSatisfiable(formula,n) ? 1 : 0;
					
					stopWatch.stop();
				}
				sigmas.put(m, (double)satisfieable/100);
				
				avgStopWatch.stop();
				t_avg = avgStopWatch.getElapsedTime()/100;
			}
			
			int bestM = getBestM(sigmas, n);
			
		}
		// n = n_max at this point
		
	}
	
	private static int getBestM(Map<Integer, Double> sigmas, int n) {
		int bestM = -1;
		double bestValue = -1;
		
		for ( Integer m : sigmas.keySet() ) {
			double curVal = sigmas.get(m);
			// first run or newer value is nearer to 0.5 --> take this m
			if ( bestM == -1 || Math.abs(0.5-curVal) < Math.abs(0.5-bestValue) ) {
				bestM = m;
				bestValue = curVal;
			}
		}
		
		log.info("Best m for n="+n+" is "+bestM+" with sigma_m="+bestValue);
		return bestM;
	}

}
