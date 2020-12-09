public class Profile {
    Long user_id;
    String user_name;
    int money, rank, proficiency, planted_tree, woods, seedlings, chopsticks, plates, keyrings,
                sculptures, toys, tables, chairs, swings, figures, plushs, glassmarbles, primogems;

    public Profile(){}
    public Profile( String user_name, int money ){
        this.user_name = user_name;
        this.money = money;
    }
}