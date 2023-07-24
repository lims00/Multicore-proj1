package problem2;

import java.util.*;
import java.lang.*;

public class MatmultD{
  private static Scanner sc = new Scanner(System.in);
  // private static int BLOCK_SIZE;
  public static void main(String [] args)  {
    int thread_no=0;
    if (args.length==1) thread_no = Integer.valueOf(args[0]);
    else thread_no = 32;
       
    
    int a[][]=readMatrix();
    int b[][]=readMatrix();
   
    long startTime = System.currentTimeMillis();
    
    MatmultThread[] thread=new MatmultThread[thread_no];
        
    for (int i=0;i<thread_no;i++) {
    	thread[i]=new MatmultThread(i,thread_no,a,b);
    }
    
    for (int i=0;i<thread_no;i++) {
    	thread[i].start();
    }
    
    for(int i=0;i<thread_no;i++) {
    	try {
    		thread[i].join();
    		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
    		}
    	
    }
    long endTime = System.currentTimeMillis();

    
    for (int i=0;i<thread_no;i++) {
    	
    	 System.out.println("Thread " + i+ " execution time: " + thread[i].timeDiff + "ms");
    }
    
    System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime-startTime);
  
  for (int t = 0; t < thread_no; t++) {
      System.out.println("Thread-" + t + " Sum : " + thread[t].temp_sum );
  }

  System.out.println("Matrix Sum = " + MatmultThread.sum + "\n");
}
   public static int[][] readMatrix() {
       int rows = sc.nextInt();
       int cols = sc.nextInt();
       int[][] result = new int[rows][cols];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {
              result[i][j] = sc.nextInt();
           }
       }
       return result;
   }

  public static void printMatrix(int[][] mat) {
  System.out.println("Matrix["+mat.length+"]["+mat[0].length+"]");
    int rows = mat.length;
    int columns = mat[0].length;
    int sum = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        System.out.printf("%4d " , mat[i][j]);
        sum+=mat[i][j];
      }
      System.out.println();
    }
    System.out.println();
    System.out.println("Matrix Sum = " + sum + "\n");
  }
  
  static class MatmultThread extends Thread {
      int start;
      int next;
      int[][] a;
      int[][] b;
      
      static int sum; 
      int temp_sum=0;
      long StartTime;
      long endTime;
      long timeDiff;
      
      int n;
      int m;
      int p;
      
      public MatmultThread(int start, int next, int[][] a, int[][] b) {
    	  this.start=start;
    	  this.next=next;
    	  this.a=a;
    	  this.b=b;
    	  
    	  }
      public void run() {
    	  StartTime=System.currentTimeMillis();
    	 
    	  n= a[0].length;
    	  m= a.length;
    	  p= b[0].length;
    	  
    	  for(int i = start;i < m;i+=next){
    	      for(int j = 0;j < p;j++){
    	    	 // temp_sum=0;
    	    	  
    	    	  for(int k = 0;k < n;k++){
    	    		  temp_sum += a[i][k] * b[k][j];
    	    	  }
    	    	 
    	      }
    	  }
    	  
    	  sum=sum+temp_sum;
    	  
    	  
    	  endTime=System.currentTimeMillis();
    	  timeDiff=endTime-StartTime;
    	  
    	  
   
     
      }
  }

}
