/*
* boardDisplay Print the board to the screen
*/
public class boardDisplay{

	private String[][] displayArray;

	//boardDisplay Constructor 
	public boardDisplay(String[][] boardArray){
		
		updateDisplay(boardArray);//Updates the Array
	
	}
	/*
	Prints the Array to the Screen 
	*/
	public void updateDisplay(String[][] boardArray){
	
		updateArray(boardArray);//Updates the Array
		System.out.print("\t");
		//Prints the Column Numbers 
		for(int j =1; j<=displayArray.length+1;j++){
			System.out.print("  "+j+" ");
		}
		System.out.print("\n");
		//Prints The Columns and Rows of the Board and Fill in the Space Between with the Corresponding Element of the Array
		for(int i = 0;i<displayArray.length;i++){
			System.out.print("\t\033[34;100m|\033[0m");
			for(int j =0; j<displayArray[i].length;j++){
				System.out.print(" " +displayArray[i][j]+" \033[34;100m|\033[0m");
			}
			System.out.print("\n");
		}
		//Prints the Bbottom of the floor
		System.out.println("\t  \033[1m- - - - - - - - - - - - -\033[0m ");
	
	} 
	public void updateArray(String[][] boardArray){
		
		displayArray = boardArray;
	
	}
}