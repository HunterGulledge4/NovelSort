package Lab5_HW5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class NovelSortDriver {

	
	public static void main(String[] args) throws FileNotFoundException {
		
		//Int arrays for testing the novelSort() method.
		int[] hundredArr = new int[100];
		int[] thousandArr = new int[1000];
		
		//String array that will be filled with the transactions from the log provided.
		String[] transactionInputArr = new String[17];
		
		//Reading files into arrays according to their data type (i.e. int[] and String[]).
		try {
            File hundredFile = new File("input_100.txt");
            Scanner fileScanner1 = new Scanner(hundredFile);
            int p = 0;
            while(fileScanner1.hasNextInt()){
                hundredArr[p++] = fileScanner1.nextInt();
            }
            fileScanner1.close();
            
            File thousandFile = new File("input_1000.txt");
            Scanner fileScanner2 = new Scanner(thousandFile);
            int q = 0;
            while(fileScanner2.hasNextInt()){
                thousandArr[q++] = fileScanner2.nextInt();
            }
            fileScanner2.close();
            
            
            /*Despite the misleading file name, this is the transaction log being read into a String array for sorting using insertionSort(), 
             *which is found on line 126. I noticed that the original log has a blank line between each transaction. See line 52 for my counter action to this. 
            */
            File transactionInput = new File("NovelSortInput.txt");
			Scanner fileScanner3 = new Scanner(transactionInput);
			int h = 0;
			while (fileScanner3.hasNextLine()) {
				transactionInputArr[h++] = fileScanner3.nextLine();
                
				/*For each of the blank lines it recognizes, it reduces the index by one 
				 *so that it wouldn't just read in half of the transactions in the log. 
				*/
				if(transactionInputArr[h - 1].isEmpty()) {
                     h--;
                 }
			}
			fileScanner3.close();
            
            
		
		} catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
		
		//Sorting both arrays using the novelSort() method I created.
		novelSort(hundredArr);
		novelSort(thousandArr);
		
		//Sorting the cities/times alphabetically (because the time stamps in the transaction log already fall in chronological order).
		insertionSort(transactionInputArr);
		
		//Printing the sorted arrays and sorted transaction log in an aesthetically-pleasing format.
		System.out.println("Sorted Arrays using novelSort:");
		System.out.println();
		System.out.println("Input File 100");
		System.out.println(Arrays.toString(hundredArr));
		System.out.println("                         Sorted--->                         --->                         --->                         --->                         --->");
		System.out.println();
		System.out.println("Input File 1000");
		System.out.println(Arrays.toString(thousandArr));
		System.out.println("                         Sorted--->                         --->                         --->                         --->                         --->");
		System.out.println();
		System.out.println("________________________");
		System.out.println("Updated Transaction Log:");
		System.out.println();
		
		//Printing a formatted version of the alphabetically sorted cities and the corresponding time stamps of their transactions.
		for(int i = 0; i < transactionInputArr.length; i++) {
			System.out.printf("%-25s%n", transactionInputArr[i]);
		}
		
	}			
	
	
	/*Finds and returns the index of the largest value in an array, because 
	 *we need the actual index for novelSort(), not just the largest value.
	*/
	public static int findLargestInd(int[] array, int low, int high) {
		int largest = array[low];
		int maxInd = low;		
		for(int i = low + 1; i <= high; i++) {
			if(array[i] > largest) {
				largest = array[i];
                maxInd = i;
			}
		}
		return maxInd;
	}
	
	
	/*Finds and returns the index of the smallest value in an array, because 
	 *we need the actual index for novelSort(), not just the smallest value.
	*/
	public static int findSmallestInd(int[] array, int low, int high) {
		int smallest = array[low];
		int minInd = low;		
		for(int i = low + 1; i <= high; i++) {
			if(array[i] < smallest) {
				smallest = array[i];
                minInd = i;
			}
		}
		return minInd;	
	}
	
	//Method for swapping two integers in the novelSort() method.
	public static void swap(int[] array, int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;
	}
	
	
	/*Algorithm I will need for sorting through the Strings alphabetically: Chicago, Houston, Phoenix, and Seattle. 
	**Note** The times in the given input are already sorted, I will just need to sort the cities.
	  I adapted my preexisting insertionSort method to take in a String[] array, and used compareTo in the parameters of my while loop
	  so that it will compare each city's String with that of the key, which is every element in the array. 
	*/
	public static void insertionSort(String[] array) {
		int n = array.length;
		for(int i = 1; i < n; ++i) {
			String key = array[i];
			int j = i -1;
					
			while(j >= 0 && array[j].compareTo(key) > 0) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = key;
		}
	}

	
    /*Sorting method that uses start++ and end-- to increase the starting range and decrease the end of the range of values as it iterates.
	  This forces the range of values that are being searched for (largest and smallest indices) to gradually shrink 
	  as the largest and smallest values are sorted by being placed "outside" (on both ends) of the next range of values that are searched & sorted. 
	*/
    public static void novelSort(int[] array) {
		int start = 0;		
        int end = array.length - 1;

        for(int i = 0; i < array.length / 2; i++, start++, end--){
            int minInd = findSmallestInd(array, start, end);
            int maxInd = findLargestInd(array, start, end);
            
            /*If the max index is the same as the current "start", it needs to be assigned to 
            the minimum index variable since the "start" is at the beginning. (Fixes potential edge case of maxInd also being the start)
            */
            if(maxInd == start){
                maxInd = minInd;
            }
            
            //If the minimum index isn't equal to the current "start", it will be swapped with the start value.
            if(minInd != start){
                swap(array, minInd, start);
            }
            
            //If the maximum index isn't equal to the current "end", it will be swapped with the end value.
            if(maxInd != end){
                swap(array, maxInd, end);
            }
        }
	}
}