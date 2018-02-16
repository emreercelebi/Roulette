import java.util.*;

public class RouletteWheel {

	private HashSet<Integer> reds;
	private HashSet<Integer> blacks;
	private int[] wheelValues = {-1, 27,10,25,29,12,8,19,31,18,6,21,33,16,4,23,35,14,2,0,28,9,26,30,11,7,20,32,17,5,22,34,15,3,24,36,13,1};
	
	public RouletteWheel(){
		reds = new HashSet<Integer>();
		blacks = new HashSet<Integer>();		
		fillReds();
		fillBlacks();
	}
	
	private void fillReds(){
		for (int i = 1; i <= 37; i += 2){
			if (i == 19) continue;
			reds.add(wheelValues[i]);
		}		
	}
	private void fillBlacks(){
		for (int i = 2; i <= 36; i += 2){
			blacks.add(wheelValues[i]);
		}
	}
	public HashSet<Integer> getReds(){
		return reds;
	}
	public HashSet<Integer> getBlacks(){
		return blacks;
	}
	public int[] getWheelArray(){
		return wheelValues;
	}
	
	public void printWheel(){
		System.out.println("       36 13 1 00 27 10 25");
		System.out.println("     24                   29 ");
		System.out.println("    3                       12");
		System.out.println("   15                         8");
		System.out.println("  34                          19");
		System.out.println(" 22                            31");
		System.out.println(" 5                             18");
		System.out.println(" 17                             6");
		System.out.println(" 32                            21");
		System.out.println("  20                         33");
		System.out.println("   7                        16");
		System.out.println("   11                      4");
		System.out.println("     30                  23 ");
		System.out.println("       26 9 28 0 2 14 35");
		System.out.println("");
		System.out.println("Due to color limitations in this console:");
		System.out.println("Red Numbers: 27, 25, 12, 19, 18, 21, 16, 23, 14, 9, 30, 7, 32, 5, 34, 3, 36, 1");
		System.out.println("Black Numbers: 10, 29, 8, 31, 6, 33, 4, 35, 2, 28, 26, 11, 20, 17, 22, 15, 24, 13");
	}

}
