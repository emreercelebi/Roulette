import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int ante;
		while (true){
			System.out.println("Enter the amount of money you want to buy in with. Minimum of $50 and maximum of $1000");
			try {
				ante = Integer.parseInt(s.nextLine());
			}
			catch (NumberFormatException e){
				System.out.println("Not a valid input. Please try again");
				System.out.println("");
				continue;
			}
			if (ante < 50 || ante > 1000){
				System.out.println("Input is out of range. Please try again");
				System.out.println("");
				continue;
			}
			break;
		}
		RouletteGame game = new RouletteGame(ante);
		game.start();
		s.close();
	}

}
