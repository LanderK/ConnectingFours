import java.io.*;
import java.util.*;
import java.lang.Math.*;
/*
* The Main Class In my connect4 Program
* its deals with all the User input for the program 
* and the overall running of the game
*/
public class connect4{

	private static LinkedList<Integer> Temp ; // LinkedList used to store all the moves playing in a game, Used for the Replay function
	public 	static File saveFile = new File("Connect4Save.txt"); // File the Game is saved too
	private static board board1; // Board Object from the Board Class 
	private static Scanner inputScan = new Scanner(System.in); // Scanner from System standard input
	private static int Turns ; // Int to Count the Number of turns a Game has had 
	private static String Difficulty ; // String to store the Difficulty of the AI
	private static boolean gamefinished; // Boolean to know if the game has finished

	public static void main(String[] args) throws FileNotFoundException, IOException{
			//Prints out the Entrance Screen the Game 
			System.out.print("\t"+"\033[91m**\033[0m  \033[93mWelcome to  Connect 4\033[0m  \033[91m**\033[0m"+"\n\n");
			board1 = new board(6,7);
			StartMenu();
	}
	/*
	Prints The Start Menu and Get User input to Select what the user wants to to do 
	after the the game Loads. 
	*/
	public static void StartMenu() throws IOException {
		
		System.out.print("\n\t\t"+"\033[94mNew  Game:N\033[0m "+"\n\t\t\033[95mLoad Game:L\033[0m\n\t\t\033[96mExit Game:E\033[0m\n\n");
		String command;
		int errorCount = 0; // Int to count the Number of Error In the StartMenu class, so the user cant keep on causing errors
		while(errorCount<=8){
			command = inputScan.next();
			//Compares the Entered Command to the menus  Required Inputs
			if(command.toUpperCase().equals("N")){
				selectMode(); // Loads New Menu to Select the Difficulty of the AI 
			}
			else if(command.toUpperCase().equals("L")){
				try{
					playNewGameLoaded();//Starts a new from the Save Game file 
				}
				catch(FileNotFoundException e){
					//Catches the File Not Found Exception to tell the user their is no Save File when they try to load one and the file doesnt exist
					System.err.print("Error: No Save Game to Load\n");
					errorCount++;
					
				}
			}
			else if(command.toUpperCase().equals("E")){
				System.out.println("Exiting Game.....");
				System.exit(0);
			}
			else{
				System.out.println("Error: Invalid Input");
				errorCount++;
			}
		}
		System.out.println("Too many invalid inputs in main menu. Exiting.....");
		
	}
	/*
	Method to Select the Difficulty of the AI
	*/
	public static void selectMode() throws 	IOException, FileNotFoundException {
		
		//Prints The AI Selection Menu 
		System.out.println("\t\t   \033[91mEasy:1\033[0m \n\t\t\033[92m Advanced:2\033[0m");
		//Creates new Empty LinkedList Object 
		Temp = new LinkedList<Integer>();
		int command;
		while(true){
			try{	
				command = inputScan.nextInt();
				if(command == 1){
					Difficulty = "Easy"; // Sets the Difficulty to Easy Mode 
					NewGamePVC();//Starts new Game 
					
				}
				else if(command == 2){
					Difficulty = "Adv"; //Sets the Difficulty to Advanced Mode 
					NewGamePVC();//Starts new Game 
				}
				else{
					System.err.println("Error: Invalid Input");
				}
				
			}
			catch(InputMismatchException e){
			//Catchs Error when input is not an integer 
				System.err.println("Error in selectMode: Inavlid Input");
				String commandAsString =  inputScan.next();
			}
		}
	}
	/*
	The Main Game Loop for a Player vs Computer Game 
	*/
	public static void NewGamePVC() throws 	IOException, FileNotFoundException{
		
		while(true){
			//Make Clear board using a method from the the board class 
			board1.makeClearBoard();
			board1.updateDisplay();
			int Turns = 0; // Sets the Number of Turns to turn 0
			gamefinished = false;
			playGame(Turns);
			playAgain();
		}
	}
	/*
	PlayGame Method Deals With Each Player Turns, AlternAting Between Each Player Every time the last one has played
	*/
	public static void playGame(int Turn) throws IOException, FileNotFoundException{
		
		Turns = Turn ;
		while(Turns<42 && !gamefinished){
			//When the Turn is Even, then Player 1 Takes there Go 
			if(Turns%2==0){
				playerGo("Player 1","\033[31mR\033[0m"); //Player Go method , parsing the Player 1 as the Name and its Counter Colour
				Turns++; // Increase the Turns counter by 1
				gamefinished = board.checkWinner("\033[31mR\033[0m"); // Checks if Player 1 Has won 
				if(gamefinished){
					//If Player Did win Print Message letting them Know 
					System.out.println("Player 1 Wins"); 
					break;
				}
			}
			else{
			//When the Turn is odd the computer takes it go 
			//Repeats the same order as before calling the same methods except the ComputerGo method, and checking with The computers counter colour
				computerGo();
				board1.updateDisplay();
				Turns++;
				gamefinished = board.checkWinner("\033[33mY\033[0m");
				if(gamefinished){	
					System.out.println("Computer Wins");
					break;
				}
			}
		}
	}
	/*
	playNewGameLoaded Method Reads from the save File to load the Game into the board, so players can continue playing 
	*/
	public static void playNewGameLoaded() throws FileNotFoundException, IOException{
	
		Scanner fileScannner = new Scanner(saveFile);
		Turns = 0;//Sets Turns to Zero
		//Reads the first Line of the Save File Which Contains the Difficulty of the AI Stored 
		Difficulty = fileScannner.next(); 
		while(fileScannner.hasNextInt()){
			//Places Each Counter position that is stored into the board 
			board1.placeCounter(fileScannner.nextInt(),fileScannner.next());
			//Increase the number of turns each time a new number is read from the save game file
			Turns++;
		}
		board1.updateDisplay();//Updates The display of the board on the screen 
		gamefinished = false;//Sets the gamefinished boolean to false 
		playGame(Turns); // runs the play game Method
		playAgain();
	
	
	}
	/*
	CheckNewGame, Checks the Usr if they Would like to Play thhe game Again, And Deals with the Input of the User
	*/
	public static boolean checkNewGame(String c){
	
		// Checks the User Input to see if it matchs the Requested Inputs
		if(c.toUpperCase().equals("YES") || c.toUpperCase().equals("Y")){
			System.out.println("New Game");
			return true;
		}
		//if the input is N/NO or not what the Progam wanted. the Scanner for standard input is closed and returns false
		else if(c.toUpperCase().equals("NO") || c.toUpperCase().equals("N")){
			inputScan.close();
			return false;
		}
		else{
			inputScan.close();
			return false;
		}
	
	}
	
	public static void playAgain() throws FileNotFoundException, IOException {
		
		String command;
		//Checks out how the Game Ended, wether the game finished before the board was full or not 
		if(!gamefinished && Turns<42 ){
		//Tells the User that the Game Was a Draw and the if the User user would like to play again 
			System.out.println("\n Game is a Draw: Do you want to play Again? Y/N");
			command = inputScan.next();
			//Checks the boolean reaturned from checkNewGame method if the it is false
			if(!checkNewGame(command)){
				//Tell the User the Game is Closing and Exits the Game
				System.out.println("Exiting Game.... ");
				System.exit(0);
			}
			//Runs the selectMode Menu to select the Difficulty again 
			selectMode();
		}
		else{
		//Ask the user if they want to play Again 
			System.out.println("\n Do you want to play Again? Y/N");
			//Runs through the same Methods and if statments if the game was a draw 
			command = inputScan.next();
			if(!checkNewGame(command)){
				System.out.println("Exiting Game.... ");
				System.exit(0);
			}
			selectMode();
			
		}
	}
	/*
	Save Game Method 
	*/
	public static void saveGame() throws IOException, FileNotFoundException{
	
		board.saveBoard(Difficulty); // calls the saveBoard from the board class
	
	}
	/*
	Saves Each Move Played
	*/
	public static void saveTempGame(int position){
	
		Temp.add(position);//Adds the Last Played Position to the LinkedList Temp 
	
	}
	/*
	loadReplay method is used to replay The most recently played game 
	*/
	public static void loadReplay() throws IOException, FileNotFoundException{
	
		//Clears the Board so the Counters can be replaced into the Board 
		board1.makeClearBoard();
		//Stores the Turn the current game was on
		int lastGameTurns = Turns;
		//Resets The Turn the Game was is on 
		Turns = 0;
		while(Turns<lastGameTurns){
		//When Turns is a multiple of 2
			if(Turns%2==0){
			//Adds the an Element of Temp of index equal to Turns into the Board for Player 1
				board1.placeCounter((int)Temp.get(Turns),"\033[31mR\033[0m");
				//Prints a Message Stating Who Just Played, Turn Number and what position they played
				System.out.println("\033[31mPlayer 1 Go\033[0m, Turn "+(Turns+1)+": "+Temp.get(Turns));
				board1.updateDisplay();
			}
			else{
			//Adds the an Element of Temp of index equal to Turns into the Board for the Computer
				board1.placeCounter((int)Temp.get(Turns),"\033[33mY\033[0m");
				//Prints a Message Stating Who Just Played, Turn Number and what position they played
				System.out.println("\033[33mComputer Go\033[0m, Turn "+(Turns+1)+": "+Temp.get(Turns));
				board1.updateDisplay();
			}
			Turns++;
		}
		gamefinished = false;
		playGame(Turns);//Starts new Game at Turn = Turns
	}
	/*
	playerGO method Deals With the Input for the Human player go
	*/
	public static void playerGo(String name,String colour) throws IOException, FileNotFoundException{
		
		int xinput; //Int for the input of the user 
		int errorCount = 0; //Int to Count the Error for the users Input when placing a counter
		while(true){
			try{
			//Prints Message to Screen to Prompt to the user that it's thier go 
				System.out.println("\033[31m"+name+"\033[0m"+ " Go");
					xinput = inputScan.nextInt();
					//Checks the Inputed Value of is valid 
					while(board1.checkValid(xinput) == false){
						//if not valid , Ask the user to input a new Value
						System.err.println("Invalid Input");
						System.out.println("\033[31m"+name+"\033[0m"+ " Go");
						xinput = inputScan.nextInt();
					}
					//Places the Counter in Dessignated Place with Colour of the Player 
					board1.placeCounter(xinput,colour);
					//Saves the position in the LinkedList
					saveTempGame(xinput);
					//Update the Display
					board1.updateDisplay();
					break;
			}
			catch(InputMismatchException e){
			//Catches Exception when the Inputed value is not an integer 
				String input = inputScan.next();
				if(errorCount > 5){
				//if the Error count more than Wanted, tell the user and exit the game 
					System.err.println("Reached Max Input Error Count: Too many invalid inputs");
					System.exit(0);
				}
				if(input.toUpperCase().equals("S")|| input.toUpperCase().equals("SAVE")){
					//if the String entered is equal to the S or SAVE
						errorCount--;//Decrease Error Count
						//Print Statement to the Screen Telling the User that the game is saving 
						System.out.println("Saving Game.....");
						//Saves the Game 
						saveGame();
						//Print Statement to the Screen Telling the User that the game has been saved
						System.out.println("Game Saved");

				}
				else if(input.toUpperCase().equals("R")|| input.toUpperCase().equals("REPLAY")){
				//if the String entered is equal to the R or REPLAY
					errorCount--;//Decrease Error Count
					//Print Statement Telling the User that the Replay is Starting 
					System.out.println("Replaying...");
					//Start the loadReplay Method
					loadReplay();
				}
				else{
					errorCount++;//Increase the ErrorCount 
					//Tell the User that the Given Input is invalid
					System.err.println("Error: Invalid Input Type. Please Go Again");
				}
			}
		}
	}
	/*
	ComputetGo Deals with the Generating and Place of the Computers Counter
	*/
	public static void computerGo() throws IOException, FileNotFoundException{

		computerPlayer com = new computerPlayer(Difficulty);//Creates new ComputerPlayer Object of Set Difficulty
		String Counter =  com.getCounterColour();//Sets Counter Colour to the Colour Feild in the ComputerPlayer Class
		System.out.println("\033[33mComputer\033[0m Go");//Prints Statment telling the user that its the computers go
		
		int xinputComputer = com.computerGeneratePosition();//Let xinputComputer be the number generated form the computerGeneratePosition() method in ComputerPlayer Class
		//Checks the Value generated from the computer is valid 
		while(board.checkValid(xinputComputer) == false){
			
			xinputComputer = com.computerGeneratePosition();
			
		}
		//Places the generated value into the board
		board.placeCounter(xinputComputer,Counter);
		//Save the played value to Temp LinkedList
		saveTempGame(xinputComputer);
	}
}

	
	
	
