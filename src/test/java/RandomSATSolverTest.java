import static org.junit.Assert.*;

import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import de.unihl.algo.three_sat_crititcal_number.CNFFormula;
import de.unihl.algo.three_sat_crititcal_number.RandomSATSolver;


public class RandomSATSolverTest {
	
	@Before
	public void setUp() {
		Logger.getRootLogger().setLevel(Level.ALL);
	}
	

	@Test
	public void testIsSatisfiable() {
		Vector<Vector<Integer>> vec = new Vector<Vector<Integer>>();

		Vector<Integer> c1 = new Vector<Integer>();
		c1.add(1);
		c1.add(1);
		c1.add(1);
		vec.add(c1);
		Vector<Integer> c2 = new Vector<Integer>();
		c2.add(-1);
		c2.add(-1);
		c2.add(-1);
		vec.add(c2);
		Vector<Integer> c3 = new Vector<Integer>();
		c3.add(-2);
		c3.add(1);
		c3.add(-2);
		vec.add(c3);
				
		CNFFormula formula = new CNFFormula(vec, 3);
		
		assertFalse(RandomSATSolver.isSatisfiable(formula, 3));
	}
	
	@Test
	public void testIsFormularCorrect(){
		final int k = 3;
		
		CNFFormula cnff = new CNFFormula(k);
		
		int type = 1;
		final int num_vars = 10;
		final int num_clauses = 5;
		
		boolean iscorrect = true;
		
		//10000 Formeln vom Typ 1 (1. Verteilung) erstellen und testen ob korrekt
		for (int j=0; j<10000; j++){
			cnff.randomizeFormula(type, num_vars, num_clauses);
			Vector<Vector<Integer>> form = cnff.getFormula();
	
			iscorrect = (form.size()==5);
			for (int i=0; i<num_clauses; i++){
				iscorrect &= (form.get(i).size()==k);
			}
		}
		
		type = 2;
		//10000 Formeln vom Typ 2 (2. Verteilung) erstellen und testen ob korrekt
		for (int j=0; j<10000; j++){
			cnff.randomizeFormula(type, num_vars, num_clauses);
			Vector<Vector<Integer>> form = cnff.getFormula();
			
			iscorrect &= (form.size()==5);
			for (int i=0; i<num_clauses; i++){
				int clause_size = form.get(i).size();
				iscorrect &= (clause_size==k);
				for (int x=0; x<clause_size; x++){
					for (int y=x+1; y<clause_size; y++){
						if (form.get(i).get(x).equals(form.get(i).get(y))){
							iscorrect = false;
							break;
						}
					}
				}
			}
		
		}
		
		
		assertTrue(iscorrect);
		
	}


}
