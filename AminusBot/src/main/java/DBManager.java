import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버. mysql8이후 드라이버가 변경됨
    private final String DB_URL = "jdbc:mysql://localhost/planttree_data?serverTimezone=UTC"; //접속할 DB 서버 ?serverTimezone=UTC는 mysql8이상일때 넣어줘야함

    private final String USER_NAME = "plant"; //DB에 접속할 사용자 이름을 상수로 정의
    private final String PASSWORD = "planttree0893"; //사용자의 비밀번호를 상수로 정의

    Connection conn = null;
    Statement state = null;
    ResultSet res = null;//SQL 명령문을 전달하여 실행. 실행 결과를 가져올 때 사용함
    boolean isfound;

    public DBManager(){
//          while(res.next()){
//                String number = res.getString("_number");
//                String name = res.getString("name");
//                String position = res.getString("position");
//                String age = res.getString("age");
//
//                System.out.println("번호: " + number);
//                System.out.println("이름: " + name);

//                System.out.println("직책: " + position);
//                System.out.println("나이: " + age);
//                System.out.println("-------------------------------");
//          }    테스트용
    }

    public boolean enrollment( String nickname, Long userid ) {//등록
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "SELECT * FROM users WHERE user_id=" + userid;//사용할 명령어를 String으로 미리 저장해둠

            res = state.executeQuery(sql);
            isfound = res.next();

            if (isfound)
                return false;

            sql = "insert into users (user_id, user_name) values (\"" + userid + "\", \"" + nickname + "\")";
            state.executeUpdate(sql);
            sql = "select * from users where user_id=" + userid;
            res = state.executeQuery(sql);

            res.next();
            printDBLOG( "새로운 데이터가 추가되었습니다. ID: " + res.getString("user_id") + " NAME: " + res.getString("user_name") );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally { //예외가 있든 없든 무조건 실행. state와 conn이 만약 닫히지 않았을 때 제대로 닫아줌
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
        return true;
    }
    public boolean secession( long userid ){//탈퇴
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "SELECT * FROM users WHERE user_id=" + userid;//사용할 명령어를 String으로 미리 저장해둠

            res = state.executeQuery(sql);
            isfound = res.next();

            if (!isfound)
                return false;

            sql = "DELETE FROM users WHERE user_id=" + userid;
            state.executeUpdate(sql);
            printDBLOG( "데이터가 삭제되었습니다. ID: " + userid );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally { //예외가 있든 없든 무조건 실행. state와 conn이 만약 닫히지 않았을 때 제대로 닫아줌
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
        return true;
    }
    public Profile getProfile( long userid ){
        Profile profile = new Profile();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "SELECT * FROM users WHERE user_id=" + userid;

            res = state.executeQuery(sql);
            isfound = res.next();

            if (!isfound)
                return null;

            profile.user_name = res.getString("user_name");
            profile.money = res.getInt("money");
            profile.rank = res.getInt("rank");
            profile.proficiency = res.getInt("proficiency");
            profile.planted_tree = res.getInt("planted_tree");
            profile.woods = res.getInt("woods");
            profile.seedlings = res.getInt("seedlings");
            profile.chopsticks = res.getInt("chopsticks");
            profile.plates = res.getInt("plates");
            profile.keyrings = res.getInt("keyrings");
            profile.sculptures = res.getInt("sculptures");
            profile.toys = res.getInt("toys");
            profile.tables = res.getInt("tables");
            profile.chairs = res.getInt("chairs");
            profile.swings = res.getInt("swings");
            profile.figures = res.getInt("figures");
            profile.plushs = res.getInt("plushs");
            profile.glassmarbles = res.getInt("glassmarbles");
            profile.primogems = res.getInt("primogems");

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){

                ex1.printStackTrace();
            }
        }
        return profile;
    }
    public Items getItems( int itemId ){
        Items items = new Items();
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "SELECT * FROM items WHERE item_id=" + itemId;

            res = state.executeQuery(sql);
            isfound = res.next();

            if (!isfound)
                return null;

            items.item_name = res.getString("item_name");
            items.description = res.getString("description");
            items.minvalue = res.getInt("minvalue");
            items.minproficiency = res.getInt("minproficiency");
            items.needWoods = res.getInt("needWoods");
            items.iscanmake = res.getBoolean("iscanmake");

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){

                ex1.printStackTrace();
            }
        }
        return items;
    }
    public ArrayList<Profile> getRank( long userid ){
        ArrayList<Profile> rank = new ArrayList<Profile>();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "select * from users order by money desc";

            res = state.executeQuery( sql );

            for(int i=0; i<3; i++){
                res.next();
                rank.add( new Profile( res.getString("user_name"), res.getInt("money") ) );
            }

            sql = "select * from users where user_id=" + userid;
            res = state.executeQuery( sql );
            res.next();
            rank.add( new Profile( res.getString("user_name"), res.getInt("money") ) );
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
            return rank;
        }
    }
    public void dataEdit( long userid, String attribute, int cnt ){//넘겨준 속성의 값을 넘겨준 만큼 증가하거나 감소
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "select " + attribute + " from users where user_id=" + userid;
            int having=0;

            res = state.executeQuery(sql);
            res.next();
            having = res.getInt( attribute );

            having += cnt;

            sql = "update users set " + attribute + "=" + having + " where user_id=" + userid;
            state.executeUpdate(sql);

            sql = "select * from users where user_id=" + userid;
            res = state.executeQuery( sql );

            res.next();
            printDBLOG( "데이터가 업데이트되었습니다. ID: " + res.getString("user_id") + " NAME: " + res.getString("user_name") + "에 " + attribute + " " + cnt );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
    }
    public boolean addValue( String table, String attribute, String add ) {//테이블에 값 추가
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "select * from " + table + "where " + attribute + "=" + add;

            res = state.executeQuery(sql);
            isfound = res.next();

            if (isfound)
                return false;//이미 존재하는 값으로 추가 실패

            sql = "insert into " + table + " (" + attribute + ") values (\"" + add + "\")";
            state.executeUpdate(sql);
            sql = "select * from " + table + "where " + attribute + "=" + add;
            res = state.executeQuery(sql);

            res.next();
            printDBLOG( table + " 에 새로운 데이터가 추가되었습니다: " + res.getString(attribute) );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally { //예외가 있든 없든 무조건 실행. state와 conn이 만약 닫히지 않았을 때 제대로 닫아줌
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
        return true;//모두 성공시 반환
    }
    public boolean deleteValue( String table, String attribute, String delete ) {//테이블의 값 삭제
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "select * from " + table + "where " + attribute + "=" + delete;

            res = state.executeQuery(sql);
            isfound = res.next();

            if (!isfound)
                return false;//존재하지 않는 값

            sql = "delete from " + table + " where " + attribute + "=" + delete;
            state.executeUpdate(sql);

            res.next();
            printDBLOG( table + " 의 데이터가 삭제되었습니다: " + delete );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally { //예외가 있든 없든 무조건 실행. state와 conn이 만약 닫히지 않았을 때 제대로 닫아줌
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
        return true;//모두 성공시 반환
    }
    public void dataChange( long userid, String attribute, int cnt ){//넘겨준 속성의 데이터값을 넘겨준 값으로 완전히 치환
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "update users set " + attribute + "=" + cnt + " where user_id=" + userid;
            state.executeUpdate(sql);

            sql = "select * from users where user_id=" + userid;
            res = state.executeQuery( sql );

            res.next();
            printDBLOG( "데이터가 업데이트되었습니다. ID: " + res.getString("user_id") + " NAME: " + res.getString("user_name") + "에 " + attribute + "값 " + cnt + "로 변경" );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
    }
    public void stringDataChange( long userid, String attribute, String changename ){//넘겨준 속성의 "문자열" 데이터값을 넘겨준 값으로 완전히 치환
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "update users set " + attribute + "=\"" + changename + "\" where user_id=" + userid;

            System.out.println( sql );

            state.executeUpdate(sql);

            sql = "select * from users where user_id=" + userid;
            res = state.executeQuery( sql );

            res.next();
            printDBLOG( "데이터가 업데이트되었습니다. ID: " + res.getString("user_id") + " NAME: " + res.getString("user_name") + "에 " + attribute + "값 " + changename + "로 변경" );

            conn.close();
            state.close();
            res.close();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state!=null)
                    state.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }

            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException ex1){
                ex1.printStackTrace();
            }
        }
    }
    public void printDBLOG( String content ) throws IOException {
        SimpleDateFormat formatNow = new SimpleDateFormat ("yyyy.MM.dd(E) ahh:mm:ss Zz");
        java.util.Date timeNow = new Date();
        String tmNow= formatNow.format(timeNow);
        String path = "D:\\somthing I made\\AminusBot\\LOG.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
        final PrintWriter pw = new PrintWriter(bw, true);
        String log = "[DB]" + "[" + tmNow + "] " + content;

        System.out.println(log);
        pw.write( log+"\n" );
        pw.flush();
        pw.close();
    }
}