

/* 
Karina Cann
V00802605
2 April 2017
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class  KMP 
{
   private static String pattern;
   private static int[][] dfa; 
   
   public KMP(String pattern){  
   		
   		//make the dfa 
   		//takes a string representing the pattern we're looking for and makes the DFA
   		this.pattern = pattern;
   		int M = pattern.length();
   		int R = 256; //why 256
   		dfa = new int[R][M];
   		dfa[pattern.charAt(0)][0] = 1;
   		for (int X = 0, j = 1; j < M; j++){
   			//make dfa[][j]
   			for (int c=0; c< R; c++)
   				dfa[c][j] = dfa[c][X];	//copying mismatch case
   			dfa[pattern.charAt(j)][j] = j+1; //setting the match case
   			X = dfa[pattern.charAt(j)][X];	// update restart states
   		}
   
   }
   
   /*
   		input takes string containing whole text from file
   		output returns index of first occurence of the pattern
   */
   public static int search(String txt){  
   	int i, j, N = txt.length(), M = pattern.length();
   	for (i = 0, j= 0; i < N && j< M; i++)
   		j = dfa[txt.charAt(i)][j];
   	if (j == M) return i-M; 	//found the end of pattern - found match
   	else	return N;	   		//hit end of text so no match, returning length 
 
   	   
   }

        
  	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.println("Unable to open "+args[0]+ ".");
				return;
			}
			System.out.println("Opened file "+args[0] + ".");
			String text = "";
			while(s.hasNext()){
				text+=s.next()+" ";
			}
			
			for(int i=1; i<args.length ;i++){
				KMP k = new KMP(args[i]);
				int index = search(text);
				if(index >= text.length())System.out.println(args[i]+ " was not found.");
				else System.out.println("The string \""+args[i]+ "\" was found at index "+index + ".");
			}
			
			//System.out.println(text);
			
		}else{
			System.out.println("usage: java SubstringSearch <filename> <pattern_1> <pattern_2> ... <pattern_n>.");
		}
		
		
	}
}




