import java.io.*;
import java.util.*;
/*
*	The Board Class Deals with all the Things that would
	Happen to and with the Board in a real life version of the 
	Connect 4 Game 
*/
public class board {

	private static String[][] boardArray;// 2d String array to store the Counters 
	private static boardDisplay bd; // BoardDisplay 
	private static int boardColumn;// Int to store the number of Columns in the Board
	private static int boardRow;// Int to store the number of Rows in the Board
	//Constructor for the Board class
	public board(int maxRow,int maxColumn){
	
		//Sets the Number of Columns and Rows
		boardColumn = maxColumn;
		boardRow = maxRow;
		//Creates the 2d Array with Dimensions of the Given Rowsand Columns
		boardArray = new String[boardRow][boardColumn];
		//Fills the board with O to replace the null entries
		makeClearBoard();
		//creates new boardDisplay Object with the boardArray Array
		bd = new boardDisplay(boardArray);
}
	/*
	Fills the Arrray With "O"
	*/
	public static void makeClearBoard(){
	
	//Sets Each Element of the boardArray to "O"
		for(int i=0;i<boardRow;i++){
			for(int j=0;j<boardColumn;j++){
				boardArray[i][j] = "O";
			}
		} 
	}
	/*
	Adds Counter of Given Colour into the Position wanted
	*/
	public static void placeCounter(int position, String counterColour){
	
	//Cycles through the Board from the Bottom Upwards to find the first insance of "O" and sets the Element of the  Poistion equal to the Colour of the Counter
		for(int j = 0;j<boardRow;j++){
			if(boardArray[5-j][position-1] == "O"){
				boardArray[5-j][position-1] = counterColour;
				break;
			}	
		}
		//Updates Array
		updateArray();
	}
	
	public static void updateDisplay(){
	//Calls the updateDisplay Method of the boardDisplay Class with the Array
		bd.updateDisplay(boardArray);
	
	}
	
	public static void updateArray(){
	//Calls the updateArray Method of the boardDisplay Class with the Array
		bd.updateArray(boardArray);
	
	}
	/*
	Checks if the given counterColour has 4 in a row, Declaring a win
	*/
	public static boolean checkWinner(String counterColour){
	
		//Sets the isWinner to false
		boolean isWinner = false ;
		
		//Cycles Through each Row and Each Column in the Row 
		for(int i=0;i<boardRow;i++){
			int count = 0; //int to Count the Number of Concurent Elements equal to the given counterColour
			for(int j=0;j<boardColumn;j++){
				//Checks if the the Entry is not equal to the counterColour
				if(boardArray[boardRow-1-i][j] == "O" || !boardArray[boardRow-1-i][j].equals(counterColour)){
					count = 0 ;//resets the Count 
				}
				else{
					count++;//Increase the Count
				}
				//Check if Count is more than or equal to 4 to Declare winner 
				if(count >= 4){
				//Set isWinner to True
					isWinner = true ;
					//Returns isWinner
					return isWinner;
				}
			}
		}
		
		//Cycles Through each Column and Each Row in the Column 
		for(int i=0;i<boardColumn;i++){
			int count = 0;  //int to Count the Number of Concurent Elements equal to the given counterColour
			for(int j=0;j<boardRow;j++){
				//Checks if the the Entry is not equal to the counterColour
				if(boardArray[boardRow-1-j][i] == "O" || !boardArray[boardRow-1-j][i].equals(counterColour)){
					count = 0 ;//resets the Count 
				}
				else{
					count++;//Increase the Count
				}
				if(count >= 4){
					isWinner = true ;
					return isWinner;//Returns isWinner
				}
			}
		}
		//Cycles Through each Column and Each Row in the Column 
		//Alpha Increases to move across one row and Column at a time 
		for(int i = 0;i<boardColumn;i++){
			for(int j=0;j<boardRow;j++){
				int count = 0;
				int alpha = 0;//Sets int alpha =0
				//Makes sure i+alpha and j+alpha is within the bounds of the Array 
				while(i+alpha<=6 && j+alpha<=5){
				//Checks if the the Entry is not equal to the counterColour
					if(boardArray[j+alpha][i+alpha] == "O" || !boardArray[j+alpha][i+alpha].equals(counterColour)){
					count = 0 ;//resets the Count 
					}
					else{
						count++;//Increase the Count
					}
					//Check if Count is more than or equal to 4 to Declare winner 
					if(count >= 4){
						isWinner = true ;
						return isWinner;//Returns isWinner
					}
					alpha++;//Increase alpha
				}
			}
		}
		//Cycles Through each Column and Each Row in the Column 
		//Alpha Increases to move across one row and Column at a time 
		for(int i = 0;i<boardColumn;i++){
			for(int j=0;j<boardRow;j++){
				int count = 0;
				int alpha = 0;//Sets int alpha =0
				//Makes sure 6-i-alpha and j+alpha is within the bounds of the Array 
				while(6-i-alpha>=0 && j+alpha<=5){
					if(boardArray[j+alpha][6-i-alpha] == "O" || !boardArray[j+alpha][6-i-alpha].equals(counterColour)){
					count = 0 ;//resets the count 
					}
					else{
						count++;//Increase the Count
					}
					//Check if Count is more than or equal to 4 to Declare winner 
					if(count >= 4){
						isWinner = true ;
						return isWinner; //returns isWinner
					}
					alpha++;//Increases alpha
				}
			}
		}
		return isWinner;//Returns isWinner as False if all tests fail
	}
	/*
	Checks if the Given Value is Within the bounds of the Array and if the Column is full
	*/
	public static boolean checkValid(int position){
	
		if(position>7 || position<=0 || boardArray[0][position-1] != "O"){
				return false;
		}
		return true ;
	}
	/*
	Save the Board to a File
	*/
	public static void saveBoard(String Difficulty)throws IOException, FileNotFoundException
	{
		//Creates new BufferedWritter on The Save File
		BufferedWriter bw = new BufferedWriter(new FileWriter(connect4.saveFile,false));
		//Creates the a new Save File if it doesnt Exist
		if(!connect4.saveFile.exists()){
			connect4.saveFile.createNewFile();
		}
		//Writes the Difficulty of the Ai to the first line of the save File
		bw.write(Difficulty);
		bw.newLine();
		//Goes up each Column Writting Each non "O" element to the File 
		for(int i=boardColumn-1;i>=0;i--){
			for(int j=boardRow-1;j>=0;j--){
				if(!boardArray[j][i].equals("O")){
					bw.write(""+(i+1)+" "+boardArray[j][i]);
					bw.newLine();
				}
			}
		}
		//Close the BufferedWritter
		bw.close();
	}
	/*
	Undo's the Last Counter in given Position
	*/
	public static void undoCounter(int position,String replaceString){
	
		//Goes Down the Column for the row Given and finds the First non-"O" element and makes it equal to O
		for(int j = 0;j<boardRow;j++){
			if(boardArray[j][position-1] != "O"){
				boardArray[j][position-1] = replaceString;
				break;
			}	
		}
		//Update Array
		updateArray();
	}
}