import java.lang.Math.*;
/*
computerPlayer class Generates Value for the ComputerPlayer 
*/
public class computerPlayer{

	private String counterColour; //String to Store the Computers COunter Colour
	private String aiLevel; //String to store the Difficulty of the AI
	
	public computerPlayer(String difficulty){

		counterColour = "\033[33mY\033[0m";// Sets the counterColour
		aiLevel = difficulty;//Sets aiLevel to given difficulty
		
	}
	/*
	Generates numbers to be placed in the board
	*/
	public int computerGeneratePosition(){
		
		
		int computerPosition;//Int to Store Generated Value 
		//if the Difficulty is "Advanced" 
		if(aiLevel.equals("Adv")){
			computerPosition = computerGeneratePositionAdv();//Sets the counterpPosition to the Returned value from computerGeneratePositionAdv() value
		}
		//if the Difficulty is "Easy" 
		else{
		//Generate Random number between 0 and 7
			computerPosition =(int) (8 * Math.random());
		}
		return computerPosition;//Returns the Generated Value 
	}
	/*
	Accessor for the CounterColour so other classes can access it
	*/
	public String getCounterColour(){
	
		return counterColour; //Retruns the counterColour
	
	}
	/*
	Generates Number when the aiLevel is set to Adv
	*/
	private int computerGeneratePositionAdv(){
		
		// Finds Win, Checks if Generated Value is Out of Bounds indicating the Computer has no winning move
		if(findBlockOrWin(counterColour) != 10){
			return findBlockOrWin(counterColour);//Returns the Generated Value
		} 
		 // Finds Block Checks if Generated Value is Out of Bounds indicating the Computer has no blocking move
		else if(findBlockOrWin("\033[31mR\033[0m") != 10){
			return findBlockOrWin("\033[31mR\033[0m");//Returns the Generated Value
		} 
		else{
			return (int)(8 * Math.random()); //Generates Random Position
		}
	}
	
	private int findBlockOrWin(String counter){
	
	//Places "Dummy" counter off Given Colour, into the board and check if the colour would win if counter is placed their so computer knows to block or place winning counter
	//When Colour is the counterColour of the Computer it is trying to find a winning move, if it is Player 1 colour it is looking for the first block it can find 
		for(int i=1;i<=7;i++){
		//Places the "Dummy" Counter
			board.placeCounter(i,counter);
			//Checks if Win occurs 
			if(board.checkWinner(counter)){
				//Undos the Placed Counter
				board.undoCounter(i,"O");
				return i;
			}
			//Undos the Placed Counter
			board.undoCounter(i,"O");
		}
		return 10; //if no win or Block fond returns Out of bounds Int so it can be catched it later 
	
	}

} 