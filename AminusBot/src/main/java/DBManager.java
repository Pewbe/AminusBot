import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버. mysql8이후 드라이버가 변경됨
    private final String DB_URL = "jdbc:mysql://localhost/planttree_data?serverTimezone=UTC"; //접속할 DB 서버 ?serverTimezone=UTC는 mysql8이상일때 넣어줘야함

    private final String USER_NAME = "plant"; //DB에 접속할 사용자 이름을 상수로 정의
    private final String PASSWORD = ""; //사용자의 비밀번호를 상수로 정의

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

    public boolean enrollment( String nickname, Long userid ) {
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
        return profile;
    }
    public void moneyUpdate( long userid, int money ){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            state = conn.createStatement();
            String sql = "select money from users where user_id=" + userid;
            int havingmoney=0;

            res = state.executeQuery(sql);
            res.next();
            havingmoney = res.getInt("money");

            havingmoney += money;

            sql = "update users set money=" + havingmoney;

            state.executeUpdate(sql);

            printDBLOG( "데이터가 업데이트되었습니다. ID: " + res.getString("user_id") + " NAME: " + res.getString("user_name") );

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