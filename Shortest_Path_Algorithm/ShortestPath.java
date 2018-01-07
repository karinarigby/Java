
//package shorteshpath;

/* 	KARINA CANN
**	V00802605
**	ASSIGNMENT 4
**
	 ShortestPath.java
   CSC 226 - Spring 2017
      
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
	java ShortestPath
	
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
	java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
    <number of vertices>
	<adjacency matrix row 1>
	...
	<adjacency matrix row n>
	
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;



//Do not change the name of the ShortestPath class
public class ShortestPath{
        public static int[] distTo;
        public static int[] edgeTo;
        public static int[] minListSeq;
        public static int minListSize;
		
	
	
		/* ShortestPath(G)
		Given an adjacency matrix for graph G, calculates and stores the shortest paths to all the
                vertices from the source vertex.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	public static void ShortestPath(int[][] G, int source){
		
		if (G.length==0){
			return;
		}
		if (G.length==1){
			edgeTo= new int[1];
			distTo= new int[1];
			edgeTo[0]=-1;
			distTo[0]=0;
			return; 
		}
	
		
		//holds the vertex with shortest path to this index
		//i.e., edgeTo[3]=6 means (3)--(6) is the vertex with min weight
		edgeTo = new int[G.length]; 
		
		//holds the minimum weight to this index 
		distTo = new int[G.length];
		minListSeq = new int[G.length];
		 //initialize 
		for (int i = 0; i<G.length;i++){
			distTo[i]= 9999999;
			edgeTo[i]=-1;
			minListSeq[i]=-1;
		}  
		int minIndex = source; 
		
		
		distTo[source]=0; 
		minListSize=1;  
          
    	//keep going through graph while the list isnt empty
        while (minListSize!=0){
        	//remove the minIndex from minSeq array and decrease size
        	minListSeq[minIndex]=-1;
        	minListSize--;
        

        	relax(G, minIndex); 
        	
        	//find starting minIndex;
        	for (int i=0;i<minListSeq.length;i++){
        		if (minListSeq[i]==-1||minListSeq[i]==0) continue;
        		else if(minListSeq[i]>0){
        			minIndex=i;
        			break;
        		}
        		//now compare with rest of array to find minIndex
				for (int j=minIndex; j<minListSeq.length;j++){
					if (minListSeq[j]==-1||j==source){ continue;
					}else if ((minListSeq[j]<minListSeq[minIndex])&&(minListSeq[j]!=-1)){
						minIndex=j;
					}
				}	
        		
        	}	
        }         
	}
	
	/*
	*	Relaxes total distances, updates smallest edges, and updates minList
	*/
	public static void relax(int[][] G, int start) {
		//relaxes edges and total distances i
		int adjWeight;
	//	System.out.println("HASSSDASDADF");
		for (int i=0;i<G.length;i++){ //iterate through G[start]row 
			//System.out.println("START IS "+ start+ " and I IS "+ i);
			adjWeight = G[start][i];
			if (adjWeight==0) {
				//System.out.println("weight is 0, go to next");
				continue;	
			}
			else if (adjWeight>0){
				if (distTo[start]+adjWeight<distTo[i]){
					distTo[i]=distTo[start]+adjWeight;
					edgeTo[i]=start; 
					minListSeq[i]=distTo[i];
					minListSize++;
				}
			}	
		}
	}
      
    public static void PrintPaths(int source){
    	ArrayList<Integer> paths = new ArrayList<Integer>();
    	
    	//make list
    	for (int i=source;i<edgeTo.length;i++){
			int j=i;
			paths.add(j);
			while(edgeTo[j]!=-1){
				paths.add(edgeTo[j]);
				j=edgeTo[j];
			}
			int head = paths.get(0);		
			System.out.print("The path from "+source+" to "+head+" is: "); //fix this
					//print out array in rvs
					//System.out.println(paths.size());
			int size = paths.size()-1;
			while (size>0){
				System.out.print(paths.get(size)+" --> ");
				size--;
			}
			System.out.print(paths.get(0));
			System.out.println(" and the total distance is : "+distTo[head]);
			paths.clear(); 	
		
		}	       
    }
        
		
	/* main()
	   Contains code to test the ShortestPath function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	public static void main(String[] args) throws FileNotFoundException{
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for (int i = 0; i < n && s.hasNextInt(); i++){
				for (int j = 0; j < n && s.hasNextInt(); j++){
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < n*n){
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			ShortestPath(G, 0);
            	PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}



