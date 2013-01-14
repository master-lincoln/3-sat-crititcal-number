package de.unihl.algo.three_sat_crititcal_number;

public class RandomSATSolver {

Private CNFFormula formula;
private int n;

Public void RandomSatSolver(CNFFormula _formula, int _n){
Formula = _formula;
n = _n;
//set new values for all variables in the formulas setting
Vector<Boolean> setting = new Vector<Boolean>();
Random random =new Random();
for (int i = 0;i < n;i++){
setting.set(i,random.nextBoolean());
}
formula.setValues(setting);
}
