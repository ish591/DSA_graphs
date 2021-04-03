import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
public class A4_2019CS10359{
	public int n; //no. of nodes/characters in the graph
	public int m; //no. of edges in the graph
	LinkedList<Edge>[]adj; // this is the adjacency list of the graph, each character is mapped to a unique integer
	HashMap<String, Integer> indexof; //maps each character to a unique integer in 0 to n-1
	HashMap<Integer, String> charof; //maps each integer from 0 to n-1 to a character
	long Cocurrence[]; //stores co-occurence count
	boolean visited[];
	int cc; //stores total number of connected components
	int curs; //stores the current size of the component while performing dfs
	Vector<Integer>ccsize;
	Vector<Vector<Integer>>compIndices;
	public A4_2019CS10359(int nv){
		this.n=nv;
		this.m=0;
		this.adj=new LinkedList[nv];
		this.indexof =new HashMap<String, Integer>();
		this.charof =new HashMap<Integer, String>();
		this.Cocurrence=new long[nv];
		for(int i=0;i<nv;i++){
			this.adj[i] = new LinkedList<>();
			this.Cocurrence[i]=0;
		}
	}
	public void Edge_add(int u, int v, long w){ //adds an edge between u and v of weight w, to both the adjacency lists as the graph is undirected
		this.adj[u].add(new Edge(v,w));
		this.adj[v].add(new Edge(u,w));
		this.m++; //no. of edges in the graph increases by m
		this.Cocurrence[u]+=w; //incrementing co-occurence count for each character as an edge is added to the graph
		this.Cocurrence[v]+=w;
	}
	public void mapcharacter(int ind, String charact){ //maps each character to an index and vice-versa too
		this.indexof.put(charact, ind);
		this.charof.put(ind,charact);
	}
	public int getIndex(String charact){  //returns the integer address corresponding to a character
		return indexof.get(charact);
	}
	public double Average(){
		int num=2*(this.m); //now, each edge is repeated twice in the total no. of associated characters, and hence, 2*m
		int denom=this.n;
		return (double)num/denom;
	}
	public void dfs(int s){
		this.visited[s]=true;
		this.curs++;
		compIndices.get(this.cc).add(s); //adding vertices corresponding to a component
		adj[s].forEach(u->{
			if(!this.visited[u.first()]){
				dfs(u.first());
			}
		});
		}
	public void Sorting(Vector<Integer>arr, int l, int r, long [] counts, HashMap<Integer, String>stringat){ //using Merge sort for all parts
		if(l>=r){return;}
		int m=(l+r-1)/2;
		Sorting(arr,l,m, counts, stringat);
		Sorting(arr,m+1,r,counts, stringat);
		Merge(arr,l,m,r, counts, stringat);
	}
	private void Merge(Vector<Integer>arr, int l, int m, int r, long[]counts, HashMap<Integer, String>stringat){
		int s1=m-l+1;
		int s2=r-m;
		int left[] =new int[s1];
		int right[]=new int[s2]; //this are auxillary arrays
		for(int p=0;p<s1;p++){left[p]=arr.get(l+p);}
		for(int y=0;y<s2;y++){right[y]=arr.get(m+1+y);}
		int i1=0;int i2=0;int i3=0; //i1 iterates over first subarray, i2 over second, and i3 over final merged array
		while(i1<s1 && i2<s2){
			if (counts[left[i1]]<counts[right[i2]]){
				arr.set(i3+l,right[i2]);       //as we are sorting in descending order
				i2++;
			}
			else if (counts[left[i1]]>counts[right[i2]]){
				arr.set(i3+l,left[i1]);
				i1++;
			}
			else{  //if counts are equal, we compare lexicographically on the basis of the associate strings
				if (stringat.get(left[i1]).compareTo(stringat.get(right[i2]))>0){
					//this means the string corresponding to left[i1] is greater
					arr.set(i3+l,left[i1]); //sorting in descending order
					i1++;
				}
				else{
					arr.set(i3+l,right[i2]);
					i2++;
				}
			}
			i3++;
		}
		while(i1<s1){arr.set(i3+l,left[i1]);i1++;i3++;} //copying the remaining elements of array
		while(i2<s2){arr.set(i3+l,right[i2]);i2++;i3++;}

	}
	public static void main(String args[]){
		String f1= args[0];
		String f2= args[1];
		String f3= args[2];
		File nodes =new File(f1);
		File edges =new File(f2);
		int count=-1;
		ArrayList<String>charlist= new ArrayList<String>();
		try{
			Scanner s1 =new Scanner(nodes);
			while(s1.hasNext()){
				String l1 = s1.nextLine();
				if (count>=0){
				 String[] spl = l1.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				 //starting and ending quotation marks yet to be removed
				 if (spl[1].charAt(0)=='"'){spl[1]=spl[1].substring(1,spl[1].length()-1);}
       			charlist.add(spl[1]);
       		}
       			count++;

			}
			s1.close();
		}
		catch(FileNotFoundException fe){
			fe.printStackTrace();
		}
		A4_2019CS10359 graph =new A4_2019CS10359(count); //making an instance of assignment4
		for(int j=0;j<charlist.size();j++){   //mapping character to unique integers
			graph.mapcharacter(j,charlist.get(j));
		}
		int m=-1;
		try{
			Scanner s2 =new Scanner(edges);
			while(s2.hasNext()){
				String l2= s2.nextLine();
				if (m>=0){
				String [] sple =l2.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				if (sple[0].charAt(0)=='"'){sple[0]=sple[0].substring(1,sple[0].length()-1);} //removing starting and ending quotation marks
				if (sple[1].charAt(0)=='"'){sple[1]=sple[1].substring(1,sple[1].length()-1);}
				long weight=Integer.parseInt(sple[2]); //typecasting the weight string to an integer weight
				//System.out.println(weight); for testing purposes
				graph.Edge_add(graph.getIndex(sple[0]),graph.getIndex(sple[1]),weight); //adding undirected edges to our graph
			}
			m++;
			}
			s2.close();
		}
		catch(FileNotFoundException fee){
			fee.printStackTrace();
		}
		if (f3.equals("average")){
			if(graph.n==0){System.out.println(String.format("%.2f",0));} //handling the edge case of no nodes in the graph
			//,as average will give divide by zero error
			System.out.println(String.format("%.2f",graph.Average()));
		}

		else if (f3.equals("rank")){
		Vector<Integer>partb=new Vector<Integer>(count);
		for(int y=0;y<count;y++){partb.add(y);} //initializing a random permutation of indices
		graph.Sorting(partb,0, count-1, graph.Cocurrence, graph.charof);
		//sorts according to co-occurence count and lexicographic order (descending)
		for(int y=0;y<count;y++){
			String partbout=graph.charof.get(partb.get(y));
			if(y<count-1){partbout+=",";}
			System.out.print(partbout);
		}
		System.out.println();
	}
	else if (f3.equals("independent_storylines_dfs")){
		graph.compIndices=new Vector<Vector<Integer>>();
		graph.visited= new boolean[count];
		graph.cc=0;
		graph.ccsize=new Vector<Integer>();
		for(int i=0;i<count;i++){graph.visited[i]=false;}
		for(int i=0;i<count;i++){
			if(!graph.visited[i]){
				Vector<Integer>v1=new Vector<Integer>();graph.compIndices.add(v1); //creating a new vector for the new component created
				graph.curs=0;
				graph.dfs(i);
				graph.cc++;
				graph.ccsize.add(graph.curs);
			}
		}
		//now, dfs has been performed and vertices are pushed into vectors of their respective components. Now we will sort each vector of components
		//internally based on lexicographic ordering in descending order (as each vertex is mapped to a character)
		// as this does not depend on any counts such as co-occurences, we will pass a count array containing all equal numbers
		long blankcount[]=new long[count];
			for(int y=0;y<count;y++){blankcount[y]=0;}
		for(int j=0;j<graph.cc;j++){
			int k=graph.ccsize.get(j);
			graph.Sorting(graph.compIndices.get(j),0,k-1,blankcount, graph.charof);
			//for(int y=0;y<k;y++){System.out.print(graph.charof.get(graph.compIndices.get(j).get(y))+",");}
				//System.out.println();
		}
		//now, I will make a vector containing the indices of connected components. This will be then sorted on the basis of
		//size of the component, and a string associated with that component. That string will be the largest string of the
		//component. Since all characters are distinct, we will get the required output
		Vector<Integer>partc=new Vector<Integer>(graph.cc);
		long comps[]=new long[graph.cc];
		HashMap<Integer,String>pc=new HashMap<Integer,String>();
		for(int y=0;y<graph.cc;y++){
			partc.add(y);
			comps[y]=graph.ccsize.get(y);
			pc.put(y,graph.charof.get(graph.compIndices.get(y).get(0)));
		} 
		//initializing a random permutation of component numbers and also transferring component sizes into an array 
		// for correct working of the sort function
		graph.Sorting(partc,0,graph.cc-1,comps,pc);
		for(int y=0;y<graph.cc;y++){
			long size=comps[partc.get(y)];
			for(int j=0;j<size;j++){
				String output=graph.charof.get(graph.compIndices.get(partc.get(y)).get(j));
				if(j<size-1){output+=",";}
				System.out.print(output);
			}
			System.out.println();
		}
	}
	else{
		System.out.println("Wrong function input");
	}

	}
}
class Edge{
	private int v; //the adjacent vertex
	private long weight; //weight of the edge
	public Edge(int v, long weight){
		this.v=v;
		this.weight=weight;
	}
	public int first(){
		return v;     //returns the adjacent vertex
	}
	public long second(){
		return weight;  //returns the weight of the corresponding edge
	}
}