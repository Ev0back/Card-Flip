public class Card {
    private CardStack.SUITS suit;
    private CardStack.ORIENTATION orientation;
    private char value;
    private String OSV;


    
    public Card(CardStack.ORIENTATION orientation, CardStack.SUITS suit, char value, String OSV){
        this.orientation = orientation;
        this.suit = suit;
        this.value = value;
        this.OSV = OSV;
    }

    /*
     * This method flips orientation and updates OSV
     **/
    public void flipCard(){
        orientation = (orientation == CardStack.ORIENTATION.UP) ?
                CardStack.ORIENTATION.DOWN : CardStack.ORIENTATION.UP;
        char orientationChar=(orientation==CardStack.ORIENTATION.UP) ? 
        		'U':'D';
        OSV=orientationChar + "" + OSV.charAt(1) + "" + OSV.charAt(2);
    }
    
    /*
     * This method returns the S + V value
     **/
    public String toString(){
        //char tempCOrientation = this.orientation == CardStack.ORIENTATION.UP ? 'U' : 'D';
        return (OSV.charAt(1) + "" + OSV.charAt(2));
    }


    public CardStack.SUITS getSuit() {
        return suit;
    }

    public void setSuit(CardStack.SUITS suit) {
        this.suit = suit;
    }

    public CardStack.ORIENTATION getOrientation() {
        return orientation;
    }

    public void setOrientation(CardStack.ORIENTATION orientation) {
        this.orientation = orientation;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}
