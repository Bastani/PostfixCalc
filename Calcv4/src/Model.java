import java.util.ArrayList;
import java.util.Stack;


public class Model
{
	private Stack<String> button_history = new Stack<String>();
	private Stack<Double> stored_values = new Stack<Double>();
	
	/*private static final String CLEAR = "Clear";
	private static final String ENTER = "Enter";
	private static final String PLUS = "+";
	private static final String MULT = "*";
	private static final String DIV = "/";
	private static final String MINUS = "-";*/
	private static final String EQUALS = "=";
	
	private StringBuilder sb = new StringBuilder();
	private StringBuilder sb_input_history = new StringBuilder();
	private ArrayList<String> running_history = new ArrayList<String>();
	private StringBuilder sb_completed_operations = new StringBuilder();
	
	private boolean from_memory = false;
	
	public Model()
	{
		reset();
	}
	
	/*public String getHistory(String button)
	{
		
		if(button.equals(ENTER))
		{
			if(button_history.empty())
			{
				//if no entries in stack, add to it
				//Then add to 'running display'
				
				button_history.push(sb_input_history.toString());
				running_history.add(sb_input_history.toString());
				sb_input_history.delete(0, sb_input_history.length());
				return printHistory(running_history, running_history.size());
			}
			else
			{
				// else, adjust history to be displayed and add new button press to stack
				running_history.add(sb_input_history.toString());
				button_history.push(sb_input_history.toString());
				sb_input_history.delete(0, sb_input_history.length());
				return printHistory(running_history, running_history.size());
			}
		}
		else if(button.equals(PLUS))
		{
			String first;
			String second;
			
			second = button_history.pop();
			if(button_history.empty() || !from_memory )
			{
				//If the stack is empty or operation is completed using a button press, 
				//use the button press to update to the stack
				
				sb_completed_operations.append(second + " " + sb_input_history.toString() + " + ");
				
				//Reset button-press string for next use
				sb_input_history.delete(0, sb_input_history.length());
				
				//Add updated history to the stack (First and only element)
				button_history.push(sb_completed_operations.toString());
				
				// Reset completed operations string_builder for next entry
				sb_completed_operations.delete(0, sb_completed_operations.length());
				
				//remove last element in running_history
				//for(int i = 0; i < 2; i++)
				//{
					//if(!(running_history.isEmpty()))
					//{
				running_history.remove(running_history.size() - 1);
					//}
				//}
				//replace them with updated computation
				running_history.add(button_history.peek());
				
				return printHistory(running_history, running_history.size()) + EQUALS;
			}
			//Else, use the last two entries in the stack and anything else as necessary
			
			first = button_history.pop();
			
			
			sb_completed_operations.append(first + " " + second + " " + sb_input_history.toString() + " + ");
			sb_input_history.delete(0, sb_input_history.length());
			// Add updated history to the stack
			button_history.push(sb_completed_operations.toString());
			
			// Reset completed operations string_builder for next use.
			// This prevents duplication of previous entries
			sb_completed_operations.delete(0, sb_completed_operations.length());
			
			//remove last two elements in running_history
			for(int i = 0; i < 2; i++)
			{
				if(!(running_history.isEmpty()))
				{
					running_history.remove(running_history.size() - 1);
				}
				
			}
			//replace them with updated computation
			running_history.add(button_history.peek());
			
			
			//print updated history
			return printHistory(running_history, running_history.size()) + EQUALS;
		}
		
		else if(button.equals(CLEAR))
		{
			sb_input_history.delete(0, sb_input_history.length());
			sb_completed_operations.delete(0, sb_completed_operations.length());
			running_history.clear();
			return "Start new Calculation";
		}
		sb_input_history.append(button);
		return "";
	}*/
	
	private String printHistory(ArrayList<String> arraylist, int size)
	{
		if(size == 1)
		{
			return arraylist.get(0);
		}
		return printHistory(arraylist, size - 1) + ", " + arraylist.get(size - 1);
	}
	
	/*public String getNewValue(String button)
	{
		if(button.equals(CLEAR))
		{
			sb.delete(0, sb.length());
			
			reset();
			return "0";
		}
		else if(button.equals(ENTER))
		{
			String placeholder = sb.toString();
			//button_history.push(sb.toString());
			stored_values.push(Double.parseDouble(sb.toString()));
			sb.delete(0, sb.length());
			//System.out.println(button_history.peek());
			return placeholder;
		}
		else if(button.equals(PLUS))
		{
			if(sb.toString().equals(""))
			{
				from_memory = true;
				double value = addStoredValues();
				sb.delete(0, sb.length());
				return "" + value;
				//sb.append(" " + button);
			}
			else
			{
				from_memory = false;
				double value = addStoredWithHistory();
				sb.delete(0, sb.length());
				//getNewHistory(button);
				return "" + value;
			}
			
		}
		else
		{
			sb.append(button);
			return sb.toString();
		}
		
		
		
	}*/
	
	public void addToEntry(String button)
	{
		sb.append(button);
		sb_input_history.append(button);
	}
	
	public String updateValue()
	{
		return sb.toString();
	}
	
	public void reset()
	{
		stored_values.clear();
		//stored_values.push((double) 0);
		button_history.clear();
		
		sb_input_history.delete(0, sb_input_history.length());
		sb_completed_operations.delete(0, sb_completed_operations.length());
		running_history.clear();
		sb.delete(0, sb.length());
	}
	
	public String sum()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = addStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
			//sb.append(" " + button);
		}
		else
		{
			from_memory = false;
			double value = addStoredWithHistory();
			sb.delete(0, sb.length());
			//getNewHistory(button);
			return "" + value;
		}
	}
	
	public String subtract()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = subStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
			//sb.append(" " + button);
		}
		else
		{
			from_memory = false;
			double value = subStoredWithHistory();
			sb.delete(0, sb.length());
			//getNewHistory(button);
			return "" + value;
		}
	}
	
	public String multiply()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = multStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
			//sb.append(" " + button);
		}
		else
		{
			from_memory = false;
			double value = multStoredWithHistory();
			sb.delete(0, sb.length());
			//getNewHistory(button);
			return "" + value;
		}
	}
	
	public String divide()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = divStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
			//sb.append(" " + button);
		}
		else
		{
			from_memory = false;
			double value = divStoredWithHistory();
			sb.delete(0, sb.length());
			//getNewHistory(button);
			return "" + value;
		}
	}
	
	
	
	public String operandHistory(String operand)
	{
		String first;
		String second;
		
		second = button_history.pop();
		if(button_history.empty() || !from_memory )
		{
			//If the stack is empty or operation is completed using a button press, 
			//use the button press to update to the stack
			
			sb_completed_operations.append(second + " " + sb_input_history.toString() + " " + operand + " ");
			
			//Reset button-press string for next use
			sb_input_history.delete(0, sb_input_history.length());
			
			//Add updated history to the stack (First and only element)
			button_history.push(sb_completed_operations.toString());
			
			// Reset completed operations string_builder for next entry
			sb_completed_operations.delete(0, sb_completed_operations.length());
			
			//remove last element in running_history
			//for(int i = 0; i < 2; i++)
			//{
				//if(!(running_history.isEmpty()))
				//{
			running_history.remove(running_history.size() - 1);
				//}
			//}
			//replace them with updated computation
			running_history.add(button_history.peek());
			
			return printHistory(running_history, running_history.size()) + EQUALS;
		}
		//Else, use the last two entries in the stack and anything else as necessary
		
		first = button_history.pop();
		
		
		sb_completed_operations.append(first + " " + second + " " + sb_input_history.toString() + " " + operand + " ");
		sb_input_history.delete(0, sb_input_history.length());
		// Add updated history to the stack
		button_history.push(sb_completed_operations.toString());
		
		// Reset completed operations string_builder for next use.
		// This prevents duplication of previous entries
		sb_completed_operations.delete(0, sb_completed_operations.length());
		
		//remove last two elements in running_history
		for(int i = 0; i < 2; i++)
		{
			if(!(running_history.isEmpty()))
			{
				running_history.remove(running_history.size() - 1);
			}
			
		}
		//replace them with updated computation
		running_history.add(button_history.peek());
		
		
		//print updated history
		return printHistory(running_history, running_history.size()) + EQUALS;
	}
	
	public String enterHistory()
	{
		
		if(button_history.empty())
		{
			//if no entries in stack, add to it
			//Then add to 'running display'
			
			button_history.push(sb_input_history.toString());
			running_history.add(sb_input_history.toString());
			sb_input_history.delete(0, sb_input_history.length());
			return printHistory(running_history, running_history.size());
		}
		else
		{
			// else, adjust history to be displayed and add new button press to stack
			running_history.add(sb_input_history.toString());
			button_history.push(sb_input_history.toString());
			sb_input_history.delete(0, sb_input_history.length());
			return printHistory(running_history, running_history.size());
		}
		
	}
	
	public String enterValue()
	{
		String placeholder = sb.toString();
		//button_history.push(sb.toString());
		stored_values.push(Double.parseDouble(sb.toString()));
		sb.delete(0, sb.length());
		//System.out.println(button_history.peek());
		return placeholder;
	}
	
	private double addStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
		//System.out.println(second_number);
		double result = first_number + second_number;
		
		stored_values.push(result);
		
		return result;
	}
	
	private double addStoredWithHistory()
	{
		double stored = stored_values.pop();
		double history = Double.parseDouble(sb.toString());
		double result = stored + history;
		
		stored_values.push(result);
		
		return result;
		
	}
	
	private double subStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
		//System.out.println(second_number);
		double result = second_number - first_number;
		
		stored_values.push(result);
		
		return result;
	}
	
	private double subStoredWithHistory()
	{
		double stored = stored_values.pop();
		double history = Double.parseDouble(sb.toString());
		double result = stored - history;
		
		stored_values.push(result);
		
		return result;
		
	}
	
	private double multStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
		//System.out.println(second_number);
		double result = second_number * first_number;
		
		stored_values.push(result);
		
		return result;
	}
	
	private double multStoredWithHistory()
	{
		double stored = stored_values.pop();
		double history = Double.parseDouble(sb.toString());
		double result = stored * history;
		
		stored_values.push(result);
		
		return result;
		
	}
	
	private double divStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
		//System.out.println(second_number);
		double result = second_number / first_number;
		
		stored_values.push(result);
		
		return result;
	}
	
	private double divStoredWithHistory()
	{
		double stored = stored_values.pop();
		double history = Double.parseDouble(sb.toString());
		double result = stored / history;
		
		stored_values.push(result);
		
		return result;
		
	}
	
	
	
}
