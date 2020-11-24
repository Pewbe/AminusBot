import javafx.scene.control.ProgressIndicator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUpdate implements Runnable{
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //드라이버. mysql8이후 드라이버가 변경됨
    private final String DB_URL = "jdbc:mysql://localhost/planttree_data?serverTimezone=UTC"; //접속할 DB 서버 ?serverTimezone=UTC는 mysql8이상일때 넣어줘야함

    private final String USER_NAME = "plant"; //DB에 접속할 사용자 이름을 상수로 정의
    private final String PASSWORD = "planttree0893"; //사용자의 비밀번호를 상수로 정의

    @Override
    public void run() {
        Connection conn = null;
        Statement state = null;
        ResultSet res = null;
        Profile profile;

        while( true ){
            profile = new Profile();

            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                state = conn.createStatement();

                Thread.sleep(500);
            }
            catch ( Exception e ) { e.printStackTrace(); }
        }
    }
}
