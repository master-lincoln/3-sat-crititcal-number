package de.unihl.algo.three_sat_crititcal_number;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

import au.com.bytecode.opencsv.CSVWriter;

public class Main {
	
	private static Logger log;
	
	// k-CNF
	private static final int k = 3;
	
	public static void main(String[] args) throws Exception {
		log = Logger.getRootLogger();
		log.setLevel(Level.INFO);
		
		for (int i=2; i<3; i++) {
			log.info("Starting tests for distribution i="+i);
			Map<Integer, Integer> results = new TreeMap<Integer, Integer>();
			// start parameter for the number of variables
			int n = 9;
			// lower bound for first n
			int bestM  = 40;
			long t_avg = 0;
			
			while ( t_avg < 3000 ) {
				n += 1;
				// empirical probability for each m
				Map<Integer,Double> sigmas = new TreeMap<Integer, Double>();
				t_avg = 0;
				double currentSigma = 0;
				
				// the new bestM will not be smaller than the previous
				int m = bestM;
				// we search until we are within the error epsilon
				double epsilon = 0.02;

				while ( Math.abs(currentSigma-0.5) > epsilon && m<5*n) {
					StopWatch avgStopWatch = new Log4JStopWatch("full.n"+n+".m"+m);
					// number of experimentally satisfied clauses
					int satisfieable = 0;
					
					// 100 times
					for (int j=0;j<100;j++) {
						
						CNFFormula formula = new CNFFormula(k);
						formula.randomizeFormula(1, n, m);
						satisfieable += RandomSATSolver.isSatisfiable(formula,n) ? 1 : 0;
						
					}
					currentSigma = (double)satisfieable/100;
					sigmas.put(m, currentSigma);
					
					avgStopWatch.stop();
					long this_avg = avgStopWatch.getElapsedTime()/100;
					t_avg = t_avg < this_avg ? this_avg : t_avg;
					
					m += 1;
				}
				
				log.info("Current average time for a run: "+t_avg);
				log.debug(sigmas);
				bestM = getBestM(sigmas, n);
				results.put(n, bestM);
			}
			createCSV("M values for i="+i, results);
			
			// Task B
			// n = n_max at this point with corresponding bestM
			
			Map<Integer,Double> sigmas = new TreeMap<Integer, Double>();
			int start = (int) Math.floor(0.9*bestM);
			int end = (int) Math.ceil(1.1*bestM);
			
			for (int m=start; m<end; m++) {
				int satisfieable = 0;
				
				// 100 times
				for (int j=0;j<100;j++) {
					
					CNFFormula formula = new CNFFormula(k);
					formula.randomizeFormula(1, n, m);
					satisfieable += RandomSATSolver.isSatisfiable(formula,n) ? 1 : 0;
					
				}
				sigmas.put(m, (double)satisfieable/100);
			}
			log.info("Task B solution:");
			log.info(sigmas);
			createCSV("Sigmas for i="+i+",nmax="+n+" and m="+bestM, sigmas);
		}
		
	}
	
	private static void createCSV(String filename, Map<Integer, ? extends Number> sigmas) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(filename+".csv"), '\t');
		for ( int m : sigmas.keySet() ) {
			String[] entries = { String.valueOf(m), String.valueOf(sigmas.get(m)) };
			writer.writeNext(entries);
		}
		writer.close();
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
