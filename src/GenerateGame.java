import java.util.ArrayList;
import java.util.Random;

public class GenerateGame {
    private ArrayList<Item> items;
    private ArrayList<Planet> planets;
    private ArrayList<Item> codes;
    private ArrayList<Item> bombs;
    private ArrayList<Item>invisableItems;
    private ArrayList<Planet> otherPlanets;
    private Player player;

    public GenerateGame(Player player) {
        items = new ArrayList<>();
        planets = new ArrayList<>();
        codes = new ArrayList<>();
        bombs = new ArrayList<>();
        invisableItems = new ArrayList<>();
        otherPlanets = new ArrayList<>();
        this.player = player;

        createPlanets();
    }

    public ArrayList<Item> getInvisableItems() {
        return invisableItems;
    }

    public ArrayList<Planet> getOtherPlanets() {
        return otherPlanets;
    }

    /**
     * Create all the planets and link their exits together.
     * Create all items
     * Create all the persons
     * Add all in list
     */
    private void createPlanets()
    {
        //name rooms and items
        Planet earth, neptunes, uranus, sun, mars, jupiter, mercurius, comet, saturnus, venus, pluto;
        Item bomb1, bomb2, bomb3, O2Booster, code1, code2, code3, code4, imac, microsoftSurface;
        Person elonMusk, billGates;

        // create the planets
        earth = new Planet("earth");
        earth.setHasInvisableItems(true);
        neptunes = new Planet("neptunes");
        uranus = new Planet("uranus");
        sun = new Planet("the sun");
        mars = new Planet("mars");
        jupiter = new Planet("jupiter");
        mercurius = new Planet("mercurius");
        comet = new Planet("the comet");
        saturnus = new Planet("saturnus");
        venus = new Planet("venus");
        pluto = new Planet("pluto");

        //add planets in array
        planets.add(neptunes);
        planets.add(uranus);
        planets.add(mars);
        planets.add(jupiter);
        planets.add(mercurius);
        planets.add(saturnus);
        planets.add(venus);
        planets.add(pluto);
        otherPlanets.add(comet);
        otherPlanets.add(earth);

        //creat unlock codes
        Random unlockCodeGenerator = new Random();
        int low = 100000;
        int high = 999999;
        int unlockCode1 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode2 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode3 = unlockCodeGenerator.nextInt(high-low) + low;
        int unlockCode4 = unlockCodeGenerator.nextInt(high-low) + low;

        //create the items
        bomb1 = new Item("bomb1", "", 10.0);
        bomb1.setMovable(false);
        bomb1.setXp(200);
        bomb2 = new Item("bomb2", "", 10.0);
        bomb2.setMovable(false);
        bomb2.setXp(200);
        bomb3 = new Item("bomb3", "", 10.0);
        bomb3.setMovable(false);
        bomb3.setXp(200);
        O2Booster = new Item("o2-booster", "bring your O2 to 100%", 0.5);
        code1 = new Item("code1", Integer.toString(unlockCode1), 0.1);
        code2 = new Item("code2", Integer.toString(unlockCode2), 0.1);
        code3 = new Item("code3", Integer.toString(unlockCode3), 0.1);
        code4 = new Item("code4", Integer.toString(unlockCode4), 0.1);
        imac = new Item("imac", "The microsoft server blokker", 4.48);
        imac.setShow(false);
        microsoftSurface = new Item("microsoft-surface", "The pc of Bill gates", 0.7);
        microsoftSurface.setShow(false);

        //add items in array(items)
        items.add(bomb1);
        items.add(bomb2);
        items.add(bomb3);
        items.add(O2Booster);
        items.add(code1);
        items.add(code2);
        items.add(code3);
        items.add(code4);

        //add codes in array
        codes.add(code1);
        codes.add(code2);
        codes.add(code3);

        //add bombs in array
        bombs.add(bomb1);
        bombs.add(bomb2);
        bombs.add(bomb3);

        //add invisable items in array
        invisableItems.add(imac);
        invisableItems.add(microsoftSurface);

        //create persons
        String txtElon = "";
        txtElon += "\tWelcome SpaceX astronaut " + player.getName();
        txtElon+= "\n\t\t\t\tWe are dealing with a major problem, there is a comet in bound to the earth.";
        txtElon+= "\n\t\t\t\tYou are the only astronaut that can fly our Starship 5.0.";
        txtElon+= "\n\t\t\t\tYou need to fly it to all the planets. We have put on different planets bombs and codes to unlock them.";
        txtElon+= "\n\t\t\t\tWe don't know anymore where we have put them and the computer where we had written it has crashed.";
        txtElon+= "\n\t\t\t\tPlease go get the bombs and put them on the comet, so that you can let them explode from earth.";
        txtElon+= "\n\t\t\t\tHold in mind that your spacesuit is still a prototype, so you will lose 15% oxygen on a normal planet and 20% oxygen gas planet.";
        txtElon+= "\n\t\t\t\tEverytime you go back in your Starship get 10%.";
        txtElon+= "\n\t\t\t\tType help to see all the command words that you can use.";
        txtElon+= "\n\t\t\t\tGo save the world " + player.getName() + "!";

        elonMusk = new Person("Elon-Musk", "elonmusk" , txtElon);
        billGates = new Person("Bill-Gates", "billgates", "\tThank you! I will let everyone know that they now can help you. Good luck!");
        billGates.setLockedText("Hi, I'm Bill Gates and the founder of 'Microsoft'. I can't connect to my Microsoft surface on earth because Apple is blocking the signal.\n\t\t\t\tGo destroy the Imac and bring my Microsoft surface. Then I will make sure that the others talk with you. See you soon!");

        //add persons to planet
        earth.addPerson(elonMusk);
        comet.addPerson(billGates);

        // initialise planets exits
        earth.setExit("north", neptunes);
        earth.setExit("east", mars);
        neptunes.setExit("north", sun);
        neptunes.setExit("east", uranus);
        neptunes.setExit("south", earth);
        uranus.setExit("west", neptunes);
        sun.setExit("east", saturnus);
        sun.setExit("south", neptunes);
        sun.setExit("north", pluto);
        saturnus.setExit("east", venus);
        saturnus.setExit("south", comet);
        saturnus.setExit("west", sun);
        venus.setExit("west", saturnus);
        comet.setExit("north", saturnus);
        comet.setExit("south", mercurius);
        mercurius.setExit("north", comet);
        mercurius.setExit("west", mars);
        mars.setExit("north", jupiter);
        mars.setExit("east", mercurius);
        mars.setExit("west", earth);
        jupiter.setExit("south", mars);
        pluto.setExit("south", sun);

        //add invisable items to planets
        earth.addItem(imac);
        earth.addItem(microsoftSurface);

        //initialize gasplanets
        saturnus.setGasplaneet(true);
        jupiter.setGasplaneet(true);

        // put random items + a person on a random planets
        putPersonsAndItemsOnRandomPlanets();

        // start game from earth
        player.setCurrentRoom(earth);
    }

    /**
     * Put items on random planets
     */
    public void putPersonsAndItemsOnRandomPlanets(){
        Random random = new Random();

        //create persons for telling which code the player needs to unlock the bomb
        Person jeffBezos, richardBranson, timDodd;
        jeffBezos = new Person("Jeff-Bezos", "jeffbezos", "");
        jeffBezos.setLockedText("Hi, I'm Jeffrey P. Bezos and the founder of 'Blue Origin'. I know which code you need to unlock this bomb. But first you will need to find Bill Gates and do what he asks you.");

        richardBranson = new Person("Richard-Branson", "richardbranson", "");
        richardBranson.setLockedText("Hi, I'm Richard Branson and the founder of 'Virgin Galactic'. I know which code you need to unlock this bomb. But first you will need to find Bill Gates and do what he asks you.");

        timDodd = new Person("Tim-Dodd", "timdodd", "");
        timDodd.setLockedText("Hi, I'm Tim Dodd also known as the 'Everday Astronaut'. I know which code you need to unlock this bomb. But first you will need to find Bill Gates and do what he asks you.");


        for (Planet planet : planets){
            int randomIndex = random.nextInt(items.size());
            Item item = items.get(randomIndex);
            planet.addItem(item);
            switch (item.getName()){
                case "bomb1":
                    planet.addPerson(jeffBezos);
                    int randomCode1 = random.nextInt(codes.size());
                    Item code1 = codes.get(randomCode1);
                    item.setCode(Integer.parseInt(code1.getDescription()));
                    jeffBezos.setText("\tTo unlock Bomb 1, you will need " + code1.getName());
                    codes.remove(randomCode1);
                    break;
                case "bomb2":
                    planet.addPerson(richardBranson);
                    int randomCode2 = random.nextInt(codes.size());
                    Item code2 = codes.get(randomCode2);
                    item.setCode(Integer.parseInt(code2.getDescription()));
                    richardBranson.setText("\tTo unlock Bomb 2, you will need " + code2.getName());
                    codes.remove(randomCode2);
                    break;
                case "bomb3":
                    planet.addPerson(timDodd);
                    int randomCode3 = random.nextInt(codes.size());
                    Item code3 = codes.get(randomCode3);
                    item.setCode(Integer.parseInt(code3.getDescription()));
                    timDodd.setText("\tTo unlock Bomb 3, you will need " + code3.getName());
                    codes.remove(randomCode3);
                    break;
            }
            items.remove(randomIndex);
        }
    }
}
