/**
 * All valid command words for the game
 * @author Brent Van Hese
 */
public enum CommandWord {
    GO("go", "Type 'go' + the direction you want to go too"),
    QUIT("quit", "To end the game"),
    HELP("help", "To get all command and their explenation"),
    UNKNOWN("", ""),
    TAKE("take", "Type this + the item you want to get from the planet and put in your bag"),
    DROP("drop", "Type this + the item you want to get out your bag and put on the planet"),
    LOOK("look", "Type 'look' to get all info | Type 'look planet' to get the planet's info | Type 'look player' to get the player's info"),
    EAT("eat", "To eat"),
    USE("use", "Type this + the item in your bag that you want to use"),
    UNLOCK("unlock", "Type this + the item that you want to unlock"),
    TALK("talk", "Type this + the person you want to talk to"),
    DESTROY("destroy", "Type this + the item on the planet you want to destroy"),
    GIVE("give", "Type this + the item in your bag you want to give to the person on the planet"),
    EXPLODE("explode", "Type this + the item you want to explode"),
    BACK("back", "Type this to go the previous planet"),
    ITEMS("items", "Type this to get the info of all the items in your bag");

    private String word, explanation;

    /**
     * constructor
     * @param word the command word
     * @param explanation the explanation of the command word
     */
    CommandWord(String word, String explanation){
        this.word = word;
        this.explanation = explanation;
    }

    /**
     * getter for the command word
     * @return command word
     */
    public String getWord() {
        return word;
    }

    /**
     *
     * @return explanation of the command word
     */
    public String getExplanation(){
        return explanation;
    }
}


