import java.sql.*;

public class DBOperation {
    private static Connection con = null;
    private static Statement st = null;

    public int[] connectionDB(int score) {
        try {
            // データベース接続の確立
            con = getConnection();
            
            // Statementオブジェクトの作成
            st = con.createStatement();
            // スコアの設定
            setScore(score);

            // データの取得
            return getScore();
        } catch (SQLException e) {
            System.out.println("SQLエラーが発生しました。");
            e.printStackTrace();
        } finally {
            // リソースのクローズ
            closeResources();
        }
        return null;
    }

    private static void closeResources() {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("リソースのクローズに失敗しました。");
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/score";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバが見つかりませんでした。");
            e.printStackTrace();
            throw new SQLException("JDBCドライバが見つかりませんでした。");
        }
    }

    private static int[] getScore() throws SQLException {
        String sql = "SELECT * FROM score ORDER BY score ASC LIMIT 3";
        ResultSet result = st.executeQuery(sql);
        int[] scoreRank = new int[3];
        int index = 0;
        while (result.next()) {
            int score = result.getInt("score");
            scoreRank[index] = score;
            index++;
        }
        return scoreRank;
    }

    private static void setScore(int score) throws SQLException {
        String sql = "INSERT IGNORE INTO score (score) VALUES (?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, score);
        pst.executeUpdate();
    }
    // public static void setScore () {

    // }

    // public static String[] getScore () {

    // }
}

