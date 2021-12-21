/**
 * Alle geldige opdrachtwoorden voor het spel.
 */
public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("?"), UNKNOWN(""), TAKE("take"), DROP("drop"), LOOK("look"), EAT("eat"), USE("use"), UNLOCK("unlock");

    private String word;

    CommandWord(String word){
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}


