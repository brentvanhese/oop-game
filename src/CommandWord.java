/**
 * Alle geldige opdrachtwoorden voor het spel.
 */
public enum CommandWord {
    GO("go", "Type 'go' + the direction you want to go too"),
    QUIT("quit", "To end the game"),
    HELP("help", "To get all command and their explenation"),
    UNKNOWN("", ""),
    TAKE("take", "Type this + the item you want to get from the planet and put in your bag"),
    DROP("drop", "Type this + the item you want to get out your bag and put on the planet"),
    LOOK("look", "To get the planet info"),
    EAT("eat", "To eat"),
    USE("use", "Type this + the item in your bag that you want to use"),
    UNLOCK("unlock", "Type this + the item that you want to unlock"),
    TALK("talk", "Type this + the person you want to talk to"),
    DESTROY("destroy", "Type this + the item on the planet you want to destroy"),
    GIVE("give", "Type this + the item in your bag you want to give to the person on the planet"),
    EXPLODE("explode", "Type this + the item you want to explode"),
    BACK("back", "Type this to go the previous planet");

    private String word, explanation;

    CommandWord(String word, String explanation){
        this.word = word;
        this.explanation = explanation;
    }

    public String getWord() {
        return word;
    }

    public String getExplanation(){
        return explanation;
    }
}


