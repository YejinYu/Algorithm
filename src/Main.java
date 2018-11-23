import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		FileReader fr = null;
		FileWriter fw = null;
		
  	    BufferedReader br = null;
  	    BufferedWriter bw = null;
  	    
		try {	
		    //***** input part *****//
			
			fr = new FileReader("C:\\hw3\\input.txt");
			br = new BufferedReader(fr);
			fw = new FileWriter("C:\\hw3\\2015171001.txt");
			bw = new BufferedWriter(fw);
			
			String s = br.readLine();
			int total = Integer.parseInt(s); //total test cases 
			
			long total_weight;
			int n;
			long output1;
			long output2;
			
			//***** algorithm part *****//
			
			for (int t=0;t<total;t++) { //total 수 만큼 algorithm part와 output part 반복 
			   
				total_weight = 0;
				s = br.readLine();
				n = Integer.parseInt(s);
				
				
				String[] ar;

				Node[][] nodes = new Node[n][n];  //initialize nodes
				for (int i=0; i<n; i++) {
					s = br.readLine();
					for (int j=0; j<n; j++) {
						ar = s.split(" ");
						nodes[i][j] = new Node(Integer.parseInt(ar[j]));
					}
				}
				
				//make edges set
				Edge E;
				ArrayList<Edge> edges = new ArrayList<Edge>();
				for (int i=0; i<n; i++) {
					for (int j=0; j<n; j++) {
						try {
							E = new Edge(nodes[i][j], nodes[i-1][j]); //up
							edges.add(E);
						}catch(Exception e){}
						try {
							E = new Edge(nodes[i][j], nodes[i][j+1]); //right
							edges.add(E);
						}catch(Exception e){}
					}
				}				

				//union find
				
				//sort edges 오름차순 
				Ascending sortedE = new Ascending();
		        Collections.sort(edges, sortedE);

		
				for (int i=0; i<edges.size(); i++) {
				
					//edge를 오름차순으로 가면서  root 가 다르면 union을 한다. 
					if (edges.get(i).getA().getRoot() != edges.get(i).getB().getRoot()) {
		
						
						boolean check = (edges.get(i).getA().getSize() == edges.get(i).getB().getSize());
				
						// union method
						Node newRoot = union(edges.get(i).getA(), edges.get(i).getB());
							
						// newRoot의 child에 대하여 size update
						// 두 tree의 사이즈가 같은 경우 >> 무조건 사이즈가 1개 커짐 
						if (check) {
							newRoot.setSize(newRoot.returnSize()+1);
						}
						
						total_weight += edges.get(i).getWeight();
						
					} //end of if
					
				}
				
				output1 = total_weight;
				output2 = nodes[0][0].getSize();
			
				
				//***** output part *****//		
				
				bw.write("#"+(t+1)+" ");
				bw.write(output1+" "+output2);
				bw.newLine();
				
			} //end of for statement
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
	    if(br != null) try{br.close();}catch(IOException e){}
		if(fr != null) try{fr.close();}catch(IOException e){}
		if(bw != null) try{bw.close();}catch(IOException e){}
		if(fw != null) try{fw.close();}catch(IOException e){}
	}
	}

	
	static Node union(Node n, Node m) {
		
		if (n.getSize() < m.getSize()) {
			n.getRoot().setP(m.getRoot());
			return m.getRoot();
		}
		else if (n.getSize() == m.getSize()) {
			n.getRoot().setP(m.getRoot());
			return n.getRoot();
		}
		else {
			m.getRoot().setP(n.getRoot());
			return n.getRoot();
			
		}
	}
	
	
}

class Ascending implements Comparator<Edge>{

	@Override
	public int compare(Edge o1, Edge o2) {
		// TODO Auto-generated method stub
		if (o1.getWeight() < o2.getWeight()) {
			return -1;
		}
		else if (o1.getWeight() > o2.getWeight()){
			return 1;
		}
		else
			return 0;
	}
	
}

class Node {
	private Node parent;
	private int input_weight;
	private ArrayList<Node> neighbors;
	private Node root;
	private int size = 0;
	
	public Node (int input_weight) {
		this.input_weight = input_weight;
		this.parent = this;
		this.root = this;
	}
	
	public int getWeight() {
		return input_weight;
	}
	
	public void setP(Node parent) {
		this.parent = parent;
	}
	public Node getP() {
		return parent;
	}
	
	public Node getRoot() {
		Node temp = this;
		while(true) {
			if (temp.getP() == temp) {
				this.root = temp;
				return root;
			}
			temp = temp.getP();
		}		
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.getRoot().size;
	}
	
	public int returnSize() {
		return this.size;
	}

}

class Edge {
	private HashSet<Node> edges = new HashSet<Node>();
	private int weight;
	private Node a;
	private Node b;
	
	public Edge(Node a, Node b) {
		this.a = a;
		this.b = b;
		weight = a.getWeight() + b.getWeight();
		edges.add(a);
		edges.add(b);
	}
	
	public HashSet<Node> getEs() {
		return edges;
	}
	
	public Node getA() {
		return a;
	}
	
	public Node getB() {
		return b;
	}
	
	public int getWeight() {
		return weight;
	}
}
