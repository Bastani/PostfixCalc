import java.util.ArrayList;
import java.util.Stack;


public class Model
{
	private Stack<String> button_history = new Stack<String>();
	private Stack<String> prev_history = new Stack<String>();
	private Stack<Double> stored_values = new Stack<Double>();
	private Stack<String> precedence= new Stack<String>();
	
	
	
	private static final String EQUALS = "=";
	private static final String START = "Start new Calculation";
	private static final String ENTER = "Enter";
	private static final String FACT = "!";
	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String MULT = "x";
	private static final String DIV = "/";
	
	private ArrayList<String> highest_precedence = new ArrayList<String>();
	private ArrayList<String> lowest_precedence = new ArrayList<String>();
	
	private StringBuilder sb = new StringBuilder();
	private StringBuilder sb_input_history = new StringBuilder();
	private ArrayList<String> running_history = new ArrayList<String>();
	private StringBuilder sb_completed_operations = new StringBuilder();
	
	private boolean from_memory = false;
	//private boolean bin_op = false;
	
	public Model()
	{
		reset();
		highest_precedence.add(MULT);
		highest_precedence.add(DIV);
		lowest_precedence.add(PLUS);
		lowest_precedence.add(MINUS);
		
	}
	
	
	
	private String printHistory(ArrayList<String> arraylist, int size)
	{
		if(size == 1)
		{
			return arraylist.get(0);
		}
		return printHistory(arraylist, size - 1) + ", " + arraylist.get(size - 1);
	}
	
	
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
		button_history.clear();
		prev_history.clear();
		precedence.clear();
		
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
		}
		else
		{
			from_memory = false;
			double value = addStoredWithHistory();
			sb.delete(0, sb.length());
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
		}
		else
		{
			from_memory = false;
			double value = subStoredWithHistory();
			sb.delete(0, sb.length());
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
		}
		else
		{
			from_memory = false;
			double value = divStoredWithHistory();
			sb.delete(0, sb.length());
			return "" + value;
		}
	}
	
	public String negate()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = negateStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
		}
		else
		{
			from_memory = false;
			double value = negateStoredWithHistory();
			sb.delete(0, sb.length());
			return "" + value;
		}

	}
	
	public String factorial()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = factStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
		}
		else
		{
			from_memory = false;
			double value = factStoredWithHistory();
			sb.delete(0, sb.length());
			return "" + value;
		}
		
	}
	
	public String sin()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = sinStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
		}
		else
		{
			from_memory = false;
			double value = sinStoredWithHistory();
			sb.delete(0, sb.length());
			return "" + value;
		}

	}

	public String cos()
	{
		if(sb.toString().equals(""))
		{
			from_memory = true;
			double value = cosStoredValues();
			sb.delete(0, sb.length());
			return "" + value;
		}
		else
		{
			from_memory = false;
			double value = cosStoredWithHistory();
			sb.delete(0, sb.length());
			return "" + value;
		}

	}
	
	public String operandHistory(String operand)
	{
		String first;
		String second;
		
		//Add last action
		prev_history.push(button_history.peek());
		
		
		second = button_history.pop();
		
		if(!from_memory)
		{
			//If the stack is empty or operation is completed using a button press, 
			//use the button press to update to the stack
			if(!checkBrackets(second, operand))
			{
				sb_completed_operations.append(second + " " + operand + " " + sb_input_history.toString() + " ");
			}
			else
			{
				sb_completed_operations.append("(" + second + ")" + " " + operand + " " +  sb_input_history.toString()  + " ");
			}
			
			//Update precedence list
			precedence.push(operand);
			
			System.out.println(precedence.toString());
			
			//Reset button-press string for next use
			sb_input_history.delete(0, sb_input_history.length());
			
			//Add updated history to the stack (First and only element)
			button_history.push(sb_completed_operations.toString());
			
			// Reset completed operations string_builder for next entry
			sb_completed_operations.delete(0, sb_completed_operations.length());
			
			
			running_history.remove(running_history.size() - 1);
			running_history.add(button_history.peek());
			prev_history.push(operand);
			
			return printHistory(running_history, running_history.size()) + EQUALS;
		}
		//Else, use the last two entries in the stack and anything else as necessary
		
		first = button_history.pop();
		
		
		if(checkBrackets(second, operand))	
		{
			System.out.println("second is true");
			if(checkBrackets(first, operand))
				sb_completed_operations.append("(" + first +  ") " + operand + " " + "(" + second + ")" + " ");
			else
			{
				sb_completed_operations.append(first +  " " + operand + " " + "(" + second + ")" + " ");
			}
				
		}
		else if(checkBrackets(first, operand))
		{
			sb_completed_operations.append("(" + first +  ") " + operand + " " + second + " ");
		}
		else
		{
			sb_completed_operations.append(first + " " + operand + " " /*+ sb_input_history.toString() + " "*/ + second + " ");
		}
		
		
		
		//Update precedence list
		precedence.push(operand);
		
		System.out.println(precedence.toString());
		
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
		
		prev_history.push(operand);
		//print updated history
		return printHistory(running_history, running_history.size()) + EQUALS;
	}
	
	public String singleOperandHistory(String operand)
	{
		//Add last action
		//prev_history.push(button_history.peek());
		
		if(!from_memory)
		{
			//set the string
			sb_completed_operations.append(sb_input_history.toString() + operand + " ");
			
			//Reset button-press string for next use
			sb_input_history.delete(0, sb_input_history.length());
			
			//Add updated history to the stack (First and only element)
			button_history.push(sb_completed_operations.toString());
			
			// Reset completed operations string_builder for next entry
			sb_completed_operations.delete(0, sb_completed_operations.length());
			
			
				if(!(running_history.isEmpty()))
				{
					running_history.remove(running_history.size()- 1);
				}
		
			//replace them with updated computation
			running_history.add(button_history.peek());
			prev_history.push(operand);
			
			//Update precedence list
			precedence.push(operand);
			
			return printHistory(running_history, running_history.size()) + EQUALS;
			
		}
		else 
		{
			String first = button_history.pop();
			
			if(!checkBrackets(first, operand))
				sb_completed_operations.append(first + operand + " ");
			else
				sb_completed_operations.append("(" + first + ")" + operand + " ");
			
			//Update precedence list
			precedence.push(operand);
		
			sb_input_history.delete(0, sb_input_history.length());
			// Add updated history to the stack
			button_history.push(sb_completed_operations.toString());
			
			// Reset completed operations string_builder for next use.
			// This prevents duplication of previous entries
			sb_completed_operations.delete(0, sb_completed_operations.length());
		
			//remove last two elements in running_history
			for(int i = 0; i < 1; i++)
			{
				if(!(running_history.isEmpty()))
				{
					running_history.remove(running_history.size() - 1);
				}
			
			}
			//replace them with updated computation
			running_history.add(button_history.peek());
		
			prev_history.push(operand);
			
			//print updated history
			return printHistory(running_history, running_history.size()) + EQUALS;
		}
				
		
	}
	
	public String enterHistory()
	{
			
			//Update prev_history
			if(button_history.empty())
			{
				//If machine started fresh, push default start message
				prev_history.push(START);
			}
			else
			{
				//Else, push last entry
				prev_history.push(button_history.peek());
				prev_history.push(ENTER);
			}
			
			//System.out.println(prev_history.toString());
			
			button_history.push(sb_input_history.toString());
			running_history.add(sb_input_history.toString());
			sb_input_history.delete(0, sb_input_history.length());
			return printHistory(running_history, running_history.size());
		
	}
	
	public String undoHistory()
	{
		//Remove top elements
		button_history.pop();
		running_history.remove(running_history.size() - 1);
		
		if(isBinOp())
		{
			//If last action i s Binary operation,
			//replace removed element with
			//top two elements in previous history
			
			String second = prev_history.pop();
			prev_history.pop();
			String one = prev_history.pop();
			
			button_history.push(one);
			running_history.add(one);
			button_history.push(second);
			running_history.add(second);
		}
		else if(isSingleOp())
		{
			running_history.add(prev_history.pop());
		}
		else
		{
			prev_history.pop();
		}
		
		System.out.println(prev_history.toString());
		
		return printHistory(running_history, running_history.size());
		
		
		
	}
	
	public String enterValue()
	{
		String placeholder = sb.toString();
		stored_values.push(Double.parseDouble(sb.toString()));
		sb.delete(0, sb.length());
		return placeholder;
	}
	
	private double addStoredValues()
	{
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
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
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
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
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
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
		double first_number = stored_values.pop();
		double second_number = stored_values.pop();
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
	
	private double factStoredValues()
	{
		double first_number = stored_values.pop();
		double result = 1;
		
		for(int i = 2; first_number >= i; i++)
		{
			result = result * i;
		}
		
		
		stored_values.push(result);
		
		return result;
	}
	
	private double factStoredWithHistory()
	{
		double history = Double.parseDouble(sb.toString());
		double result = 1;
		
		for(int i = 1; history >= i; ++i)
		{
			result *= i;
		}
		
		stored_values.push(result);
		
		return result;
		
	}
	
	private double sinStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		//System.out.println(second_number);

		double result = Math.sin(first_number);

		stored_values.push(result);

		return result;
	}

	private double sinStoredWithHistory()
	{
		double history = Double.parseDouble(sb.toString());

		double result = Math.sin(history);

		stored_values.push(result);

		return result;

	}

	private double cosStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		//System.out.println(second_number);

		double result = Math.cos(first_number);

		stored_values.push(result);

		return result;
	}

	private double cosStoredWithHistory()
	{
		double history = Double.parseDouble(sb.toString());
		double result = Math.cos(history);

		stored_values.push(result);

		return result;

	}

	private double negateStoredValues()
	{
		//System.out.println("wee");
		double first_number = stored_values.pop();
		//System.out.println(second_number);

		double result = first_number * (-1);

		stored_values.push(result);

		return result;
	}

	private double negateStoredWithHistory()
	{
		double history = Double.parseDouble(sb.toString());
		double result = history * (-1);

		stored_values.push(result);

		return result;

	}
	
	//Changes made here
	private boolean checkBrackets(String last_entry, String operand)
	{
		
		if(precedence.empty())
		{
			return false;
		}
		
		
		//Check if last_entry was an operation
		//or a number.
		try
		{
			Double.parseDouble(last_entry);
		}
		catch (NumberFormatException e)
		{
			//If the two last operands are the same,
			//no need for brackets
			if(precedence.peek().equals(operand))
			{
				precedence.pop();
				return false;
			}
			//If the last operand is low precedence,
			//no brackets needed
			if(lowest_precedence.contains(operand))
			{
				
				return false;
			}
			
			//If both operands are high precedence,
			//additional checks needed
			if(highest_precedence.contains(precedence.peek()))
			{
				//If multiplication was first,
				//brackets needed
				if(operand == DIV && precedence.pop() == MULT)
					return true;
				
				precedence.pop();
				System.out.println("Mult First");
				//Otherwise, they are not
				return false;
				
			}
			
			
			//Low precedence followed by High.
			//Thus brackets needed.
			precedence.pop();
			System.out.println("Brackets");
			
			return true;
		}
		
		//Last action involved numbers
		return false;
		
	}
	
	private boolean isBinOp()
	{
		String last_action = prev_history.peek();
		if(last_action.equals(ENTER) || last_action.equals(FACT))
		{
			return false;
		}
		prev_history.pop();
		return true;
		
	}
	
	private boolean isSingleOp()
	{
		String last_action = prev_history.pop();
		if(last_action.equals(ENTER))
		{
			return false;
		}
		return true;
		
		
	}
	
	
	
}