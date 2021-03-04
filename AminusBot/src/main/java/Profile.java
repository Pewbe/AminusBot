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
    public int getItem( String name ){
        if( name.equals( "chopsticks" ) )
            return chopsticks;
        if( name.equals( "plates" ) )
            return plates;
        if( name.equals( "keyrings" ) )
            return keyrings;
        if( name.equals( "sculptures" ) )
            return sculptures;
        if( name.equals( "toys" ) )
            return  toys;
        if( name.equals( "tables" ) )
            return  tables;
        if( name.equals( "chairs" ) )
            return chairs;
        if( name.equals( "swings" ) )
            return  swings;

        return 0;
    }
}