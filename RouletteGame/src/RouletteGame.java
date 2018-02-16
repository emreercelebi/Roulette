import java.util.*;

public class RouletteGame {
	
	private RouletteWheel gameWheel;
	private int playerPot;
	private HashMap<Character,String> betStrings;
	private HashMap<Character,Integer> betMultipliers;
	
	
	public RouletteGame(int playerPot){
		gameWheel = new RouletteWheel();
		this.playerPot = playerPot;
		buildBetStrings();
		buildBetMultipliers();
	}
	
	public void start(){
		System.out.println("Welcome to the virtual Roulette table!");
		System.out.println(" Below you will see a perfectly symmetrical (lol) representation of our wheel.");
		System.out.println("");
		gameWheel.printWheel();
		System.out.println("");
		System.out.println("You are entering with a starting pot of " + playerPot + ". Play wisely! Press Enter to continue.");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		printBets();
		System.out.println("");
		while (true){
			int bet;
			while (true){
				System.out.println("How much would you like to bet? Current balance = $" + playerPot);
				try {
					bet =Integer.parseInt(sc.nextLine().trim()); 
				}
				catch (NumberFormatException e){
					System.out.println("Not a valid number, please try again.");
					System.out.println("");
					continue;
				}
				if (bet > playerPot){
					System.out.println("You don't have enough money left to bet that much.");
					System.out.println("");
					continue;
				}
				playerPot -= bet;
				System.out.println("You have chosen to bet $" + bet +". You have $" + playerPot +" left.");
				System.out.println("");
				break;
			}
			char play;
			boolean isDigit = false;
			int multiplier;
			HashSet<Integer> userNumbers;
			while (true){
				System.out.print("Type the key of the bet you would like to make, or 'P' if you would like to see the bet options again, and press Enter: ");
				String s = sc.nextLine().trim().toLowerCase();
				if (s.length() != 1 || (!betStrings.containsKey(s.charAt(0)) && s.charAt(0) != 'p')){
					System.out.println("Not a valid input");
					System.out.println("");
					continue;
				}
				play = s.charAt(0);
				if (play == 'p'){
					printBets();
					System.out.println("");
					continue;
				}
				isDigit = Character.isDigit(play);
				multiplier = betMultipliers.get(play);
				break;
			}
			
			int spinResult = (int)(Math.random()*38) - 1;
			boolean won = false;
			
			if (isDigit){
				userNumbers = new HashSet<Integer>();
				if (play != '5') {
					System.out.println("You have chosen to bet on " + betStrings.get(play) + ". Press Enter to begin choosing your numbers.");
					sc.nextLine();
					int remaining = Integer.parseInt(play + "");
					while (remaining > 0){
						while (true){
							System.out.print("You have " + remaining + " number" + (remaining == 1 ? "" : "s") + " remaining. Pick a number: ");
							int choice;							
							try {
								String choiceString = sc.nextLine().trim();
								if (choiceString.equals("00")){
									choice = -1;
								}
								else choice = Integer.parseInt(choiceString);
							}
							catch (NumberFormatException e){
								System.out.println("Not a valid input. Enter either 00, 0, or a number between 1 and 36");
								System.out.println("");
								continue;
							}
							if (choice < -1 || choice > 36){
								System.out.println("That number is out of range. Enter either 00, 0, or a number between 1 and 36.");
								System.out.println("");
								continue;
							}
							if (userNumbers.contains(choice)){
								System.out.println("You have already selected that number. Enter either 00, 0, or a number between 1 and 36 that you haven't already chosen.");
								System.out.println("");
								continue;
							}
							userNumbers.add(choice);
							break;
						}
						remaining--;
					}
				}
				else {
					userNumbers.add(-1);
					userNumbers.add(0);
					userNumbers.add(1);
					userNumbers.add(2);
					userNumbers.add(3);
					System.out.println("You have chosen the Top Line! Your betting numbers are 00, 0, 1, 2, and 3");
					System.out.println("");					
				}
				System.out.println("Press Enter to spin the wheel!");
				sc.nextLine();
				System.out.println("Spinning................. (press Enter again to see the result)");
				sc.nextLine();
				won = userNumbers.contains(spinResult);
				
				if (won) System.out.println("The result of the spin is " + spinResult + ". You won!");
				else System.out.println("The result of the spin is " + spinResult + ". You lost :(");
				
				}
			else {
				System.out.println("You have chosen to bet on " + betStrings.get(play) + ". Press Enter to see the result of the spin.");
				System.out.println("");
				sc.nextLine();
				//none of the bets below win if the result is 0 or 00
				if (spinResult == 0 || spinResult == -1) won = false;
				// odd/even game handling
				else if (play == 'o' || play == 'e') {					
					won = play == 'e' ? spinResult % 2 == 0 : spinResult % 2 == 1;
				}
				// red/black game handling
				else if (play == 'b' || play == 'r'){
					won = play == 'b' ? gameWheel.getBlacks().contains(spinResult) : gameWheel.getReds().contains(spinResult);
				}
				// high/low game handling
				else if (play == 'h' || play == 'l'){
					won = play == 'h' ? spinResult > 18 : spinResult < 19;
				}
				// dozens game handling
				else {
					if (play == 'f') won = spinResult < 13;
					else if (play == 's') won = spinResult > 12 && spinResult < 25;
					else won = spinResult > 24;
				}
				
				if (won) {
					System.out.println("The result of the spin is " + spinResult + ". That's a member of the " + betStrings.get(play) + "! You won!");
				}
				else {
					System.out.println("The result of the spin is " + spinResult + ". That's not a member of the " + betStrings.get(play) + ". You lost :(");
				}
				System.out.println("");
			}
			if (won){
				int winnings = bet * multiplier;
				System.out.println("You've earned $" + winnings + "!");
				playerPot += winnings;
			}
			if (playerPot == 0){
				System.out.println("Your balance is now $0. You have no more money left to bet.");
				System.out.println("");
				break;
			}
			System.out.println("Your current balance is $" + playerPot + ". Press Enter to keep playing, or type 'q' then press enter to quit.");
			try {
				if (sc.nextLine().trim().toLowerCase().charAt(0) == 'q') {
					System.out.println("You are walking away with $" + playerPot + ".");
					break;
				}
			}
			catch (IndexOutOfBoundsException e){
				continue;
			}
			
		}
		sc.close();		
		System.out.println("Thanks for playing!");	
		
		
	}
	
	private void buildBetStrings(){
		betStrings = new HashMap<Character,String>();
		betStrings.put('o', "odds");
		betStrings.put('e', "evens");
		betStrings.put('b', "blacks");
		betStrings.put('r', "reds");
		betStrings.put('l', "lows (1-18)");
		betStrings.put('h', "highs (19-36)");
		betStrings.put('f', "the first dozen");
		betStrings.put('s', "the second dozen (13-24)");
		betStrings.put('t', "the third dozen (25-36)");
		betStrings.put('6', "six numbers of your choosing");
		betStrings.put('5', "the Top Line (0,00,1,2,3)");
		betStrings.put('4', "four numbers of your choosing");
		betStrings.put('3', "three numbers of your choosing");
		betStrings.put('2', "two numbers of your choosing");
		betStrings.put('1', "a single number of your choosing");
	}
	
	private void buildBetMultipliers(){
		betMultipliers = new HashMap<Character,Integer>();
		betMultipliers.put('o', 2);
		betMultipliers.put('e', 2);
		betMultipliers.put('b', 2);
		betMultipliers.put('r', 2);
		betMultipliers.put('l', 2);
		betMultipliers.put('h', 2);
		betMultipliers.put('f', 3);
		betMultipliers.put('s', 3);
		betMultipliers.put('t', 3);
		betMultipliers.put('6', 6);
		betMultipliers.put('5', 7);
		betMultipliers.put('4', 9);
		betMultipliers.put('3', 12);
		betMultipliers.put('2', 18);
		betMultipliers.put('1', 36);
	}
	
	private void printBets(){
		System.out.println("Here are the list of bets you can make, followed by their payout and their key:");
		System.out.println("");
		System.out.println("Odds: 1-1 (O)");
		System.out.println("Evens: 1-1 (E)");
		System.out.println("Blacks: 1-1 (B)");
		System.out.println("Reds: 1-1 (R)");
		System.out.println("Lows, 1...18: 1-1 (L)");
		System.out.println("Highs, 19...36: 1-1 (H)");
		System.out.println("First Dozen, 1...12: 2-1 (F)");
		System.out.println("Second Dozen, 13...24: 2-1 (S)");
		System.out.println("Third Dozen, 25...36: 2-1 (T)");
		System.out.println("Any six numbers: 5-1 (6)");
		System.out.println("Top Line, [0,00,1,2,3]: 6-1 (5)");
		System.out.println("Any four numbers: 8-1 (4)");
		System.out.println("Any three numbers: 11-1 (3)");
		System.out.println("Any two numbers: 17-1 (2)");
		System.out.println("Any single number: 35-1 (1)");
		
		
	}
}
