package JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONParser {

static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
static String input;
static int index =0;
static boolean status;


public static void main(String zz[])throws IOException {
	System.out.println("Enter the text to be parsed");
	
	input = reader.readLine();	
	input.trim();
	input = input.replaceAll("\\s", "");
	object();
	if(status)
		System.out.println("The text entered is parsed successfully!!!");
	else
		System.out.println("Entered String is not a JSON text");
	
}


private static void object() {
	if(input.charAt(index) == '{')
	{
		index++;
		pair();
		index++;
		if(input.charAt(index) == ',')
		{
			index++;
			if(input.charAt(index) == '"') pair();
			else if(input.charAt(index) == '{') object();
			else status = false;
		}
		else
			return;
	}
	else
		status = false;
}


private static void pair() {
	if(input.charAt(index) == '"')
	{	
		index++;// go to next character of "
		index = input.indexOf('"' ,index);
		index++; // go to next character of last "
		if(input.charAt(index)== ':')
		{
			index++; // go to value
			value();
		}
		else
			status = false;
	}
}


private static void value() {
	
	if(input.charAt(index) == '"')
	{
		index++;// go to next character of "
		index = input.indexOf('"' ,index);
		if(index != -1)
		{
			index++; // go to next character of last "
			if(input.charAt(index)== '}')
				status = true;
			else
				status = false;
		}
		else
			status = false;
	}
	
	else if(input.charAt(index)== '[')
	{	
		index++;
		int	last = input.lastIndexOf(']');
		if(last != -1)
		{
			input = input.substring(index,last);
			index = 0;
			object();
		}

	}
	else
	{
		int	last = input.indexOf(',');
		if( last != -1)
		{	if(input.charAt(last-1) != '}') last = last+1;
			String value = input.substring(index, last-1);
			if(value.equals("null") || value.equals("true") || value.equals("false") || value.matches("[0-9]+"))
				{
					status = true;
					index = last;
				}
			else
				status = false;
		}
		else 
		{
			last = input.indexOf('}');
			if( last != -1)
			{
				String value = input.substring(index, last);
				if(value.equals("null") || value.equals("true") || value.equals("false") || value.matches("[0-9]+"))
					{
						status = true;
					}
				else
					status = false;
			}
		}
	}
}
}
