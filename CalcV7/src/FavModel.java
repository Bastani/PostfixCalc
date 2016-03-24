import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class FavModel {
	
	private Stack<String> currentIN = new Stack<String>();
	private HashMap<String, double[]> favs = new HashMap<String, double[]>();
	
	public void deleteExpr()
	{
		
	}
	
	public double[] getValue(String key)
	{
		return favs.get(key);
	}
	
	public boolean addExpr(String expr, double[] y)
	{
		if(favs.containsKey(expr))
			return false;
		
		double[] values = new double[y.length];
		for(int i = 0; i < y.length; i++)
			values[i] = y[i];
		
		favs.put(expr, values);
		
		System.out.println("Hi + " + favs.toString());
		return true;
	}

	public void setValue(Stack<String> inExpr)
	{
		this.currentIN = inExpr;
	}
	
	public Stack<String> getValueIN()
	{
		return currentIN;
	}

	

	
}
