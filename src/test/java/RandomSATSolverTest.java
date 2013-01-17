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
		c1.add(0);
		c1.add(-1);
		c1.add(2);
		vec.add(c1);
		Vector<Integer> c2 = new Vector<Integer>();
		c2.add(-0);
		c2.add(-1);
		c2.add(2);
		vec.add(c2);
		Vector<Integer> c3 = new Vector<Integer>();
		c3.add(-0);
		c3.add(1);
		c3.add(-2);
		vec.add(c3);
				
		CNFFormula formula = new CNFFormula(vec, 3);
		
		assertTrue(RandomSATSolver.isSatisfiable(formula, 3));
	}
	
	@Test
	public void testIsFormularCorrect(){
		final int k = 3;
		
		CNFFormula cnff = new CNFFormula(k);
		
		int type = 1;
		final int num_vars = 10;
		final int num_clauses = 5;
		
		cnff.randomizeFormula(type, num_vars, num_clauses);
		Vector<Vector<Integer>> form = cnff.getFormula();
		System.out.println(form);
		
		boolean iscorrect = (form.size()==5);
		for (int i=0; i<num_clauses; i++){
			iscorrect &= (form.get(i).size()==k);
		}
		
		type = 2;
		for (int j=0; j<100; j++){
			cnff.randomizeFormula(type, num_vars, num_clauses);
			form = cnff.getFormula();
			System.out.println(form);
			
			iscorrect &= (form.size()==5);
			for (int i=0; i<num_clauses; i++){
				iscorrect &= (form.get(i).size()==k);
			}
		}
		
		assertTrue(iscorrect);
		
	}


}
