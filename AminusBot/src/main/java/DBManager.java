import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.sql.*;

public class DBManager {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버. mysql8이후 드라이버가 변경됨
    private final String DB_URL = "jdbc:mysql://localhost/planttree_data?serverTimezone=UTC"; //접속할 DB 서버 ?serverTimezone=UTC는 mysql8이상일때 넣어줘야함

    private final String USER_NAME = "plant"; //DB에 접속할 사용자 이름을 상수로 정의
    private final String PASSWORD = ""; //사용자의 비밀번호를 상수로 정의

    public DBManager(){
        Connection conn = null;
        Statement state = null;

        try{
            Class.forName( JDBC_DRIVER );
            conn = DriverManager.getConnection( DB_URL, USER_NAME, PASSWORD );
            state = conn.createStatement();

            String sql; //SQL 명령문을 저장할 String
            sql = "SELECT * FROM school";//사용할 명령어를 집어넣음
            ResultSet res = state.executeQuery(sql); //SQL 명령문을 전달하여 실행

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
//          }

            //모두 닫음
            res.close();
            state.close();
            conn.close();
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
}