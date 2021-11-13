/*
 * By: Johney Domas
 */
import java.util.Scanner;

public class CardStack {
    private static int n, m, TOP, LEFT, BOTTOM, RIGHT;
    private static Stack<Card>[][] grid;

    public enum SUITS {
        HEART, DIAMOND, SPADES, CLUBS
    }
    public enum ORIENTATION {
        UP, DOWN
    }
    
    public static void main(String [] args){
    	Scanner input = new Scanner(System.in);
    	int testNum=1;
        try {
		    while(true) {
		        String sizeInputLine = input.nextLine();
		        var sizes = sizeInputLine.split(" ");
		        n = Integer.parseInt(sizes[0]);
		        m = Integer.parseInt(sizes[1]);
		        if (n == 0 && m == 0) break;
		        TOP = 0;
		        BOTTOM = n - 1;
		        LEFT = 0;
		        RIGHT = m - 1;
		
		        grid = createAndGetGrid(n, m);
		
		        // Now lets grab the grid (OSVs) from the user
		        for (int i = 0; i < n; i++) {
		            String line = input.nextLine();             // "UC4 DD3 DC8"
		            String[] lineOSV = line.split(" "); // ["UC4", "DD3", "DC8"]
		            for (int j = 0; j < m; j++) {
		                String OSV = lineOSV[j].toUpperCase();  // "Uc4" -> "UC4"'
		                if (!isValidOSV(OSV))                   // If the OSV is not valid, throw an error
		                    throw new Exception("Invalid OSV `" + OSV + "`");
		
		                ORIENTATION orientation = (OSV.charAt(0) == 'U') ? ORIENTATION.UP : ORIENTATION.DOWN;   // 'U'
		                SUITS suit = getSuit(OSV.charAt(1));  // 'C'
		                char value = OSV.charAt(2);          // '4'
		                Card tempCard = new Card(orientation, suit, value, OSV);
		                grid[i][j].push(tempCard);
		            }
		        }
		        String nextLine=input.nextLine();
		        if(nextLine==""){
		        	printFinalGrid(testNum);
		        	testNum++;
		        	System.out.println("\n");
		        	continue;
		        }
		        
		        String[] flips = nextLine.split(""); // "LRB" -> ['L', 'R', 'B']
		        
		        performFlips(flips);
		        printFinalGrid(testNum);      
		        testNum++;
		        System.out.println("\n");
		        
		    }
		    input.close();
        }catch(Exception e) {
        	//You can print the exception if you want nice output error
        }
    }
    
    private static void performFlips(String[] flips) throws Exception {
        // We are done filling up the grid/cards, now lets grab flip cmds?
        for (String flip: flips){
        	flip = flip.toUpperCase();
            char flipCmd = flip.charAt(0);
            switch (flipCmd){
                case 'T':
                    TopFlip();
                    break;
                case 'B':
                    BottomFlip();
                    break;
                case 'R':
                    RightFlip();
                    break;
                case 'L':
                    LeftFlip();
                    break;
                default:
                    throw new Exception("Invalid flip command `" + flipCmd + "`");
            }
           
        }
    }

    //TOP flip (Here the cards in the TOP row are flipped over onto
    //the row beneath them)
    public static void TopFlip() {
    	// First lets flip all the cards in every stack of the top row
        for (int i = 0; i < m; i ++){
            if (grid[TOP][i].isEmpty())
                continue;
            grid[TOP][i] = flipCards(grid[TOP][i]); // Flip the cards for this stack
            while(!grid[TOP][i].isEmpty()){
            	
            	grid[TOP + 1][i].push(grid[TOP][i].pop());
            }
         }
        TOP++;
    }
    
    //This method performs bottom flip
    public static void BottomFlip() {
        for (int i = 0; i < m; i++){
            if (grid[BOTTOM][i].isEmpty())
                continue;
            grid[BOTTOM][i] = flipCards(grid[BOTTOM][i]); // Flip the cards for this stack
            while(!grid[BOTTOM][i].isEmpty()){
            	
            	grid[BOTTOM - 1][i].push(grid[BOTTOM][i].pop());
            }
        }
        BOTTOM--;
    }

    //This method performs right flip
    private static void RightFlip(){
        for (int i = 0; i < n; i++){
            if (grid[i][RIGHT].isEmpty())
                continue;
            grid[i][RIGHT] = flipCards(grid[i][RIGHT]); // Flip the cards for this stack
            while(!grid[i][RIGHT].isEmpty()){
            	
                grid[i][RIGHT - 1].push(grid[i][RIGHT].pop());
            }
         }
        RIGHT--;
    }

    //This method performs left flip
    private static void LeftFlip(){
        for (int i = 0; i < n; i++){
            if (grid[i][LEFT].isEmpty())
                continue;
            grid[i][LEFT] = flipCards(grid[i][LEFT]); // Flip the cards for this stack
            while(!grid[i][LEFT].isEmpty()){
            	grid[i][LEFT + 1].push(grid[i][LEFT].pop());
            }
        }
        LEFT++;
    }

    //This method prints the final grid of cards face DOWN
    private static void printFinalGrid(int testNum){
        System.out.print("Test " + testNum + ": ");
    	for (int i = 0; i < n; i ++){
            for (int j = 0; j < m; j++){
                if (grid[i][j].isEmpty())
                    continue;
                
                Stack<Card> stack = grid[i][j];
                stack.reverseStack();
                while (!stack.isEmpty()){
                    Card tempCard = stack.pop();
                    if (tempCard.getOrientation() == ORIENTATION.DOWN){
                        System.out.print(tempCard + " ");
                    }
                }
            }
        }
    }

    //This method flips all cards in the cardStack and returns flipped card stack
    private static Stack<Card> flipCards(Stack<Card> cardStack){
        Stack<Card> flippedCards = new LinkedList<>();
        while (!cardStack.isEmpty()){
            Card card = cardStack.pop();
            card.flipCard();
            flippedCards.push(card);
        }
        flippedCards.reverseStack();        // Reverse back to maintain the original order
        return flippedCards;
    }

    //Checks validity of OSV input
    private static boolean isValidOSV(String OSV){
        // Check the length
        if (OSV.length() != 3)
            return false;
        // Check that the first letter can only be 'U' or 'D'
        if (OSV.charAt(0) != 'D' && OSV.charAt(0) != 'U')
            return false;

        // We'll get and validate for an invalid 'suit', aka charAt1, inside the `getSuits` -> No need to do here
        // Lets check the value at char 2
        var V = OSV.charAt(2);
        if( V!='2' && V!='3'&& V!='4' && V!='5'&& V!='6' && V!='7'&& V!='8' && V!='9'&& V!='T'&& V!='J' && V!='Q'&& V!='K' && V!='A')
            return false;
        return true;
    }

    
    private static SUITS getSuit(char letterSuit) throws Exception{
        SUITS valueToReturn = null;
        switch (letterSuit){
            case 'H':
                valueToReturn = SUITS.HEART;
                break;
            case 'D':
                valueToReturn = SUITS.DIAMOND;
                break;
            case 'S':
                valueToReturn = SUITS.SPADES;
                break;
            case 'C':
                valueToReturn = SUITS.CLUBS;
                break;
            default:
                throw new Exception("The letter `" + letterSuit + "` does not correspond to any suit" );
        }
        return valueToReturn;
    }

    //This method creates grid of n x m of Stacks
    private static Stack<Card>[][] createAndGetGrid(int n, int m){
        Stack<Card>[][] grid = new Stack[n][m];

        // Now lets fill-up the grid
        for (int i = 0; i < n; i ++ )
            for (int j = 0; j < m; j++)
                grid[i][j] = new LinkedList<>();
        return grid;
    }
}
