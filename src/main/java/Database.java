import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Database {
    private Properties cfg;
    private Connection cn;
    private ParseURL parseURL;


    public Database(ParseURL parseURL, Properties cfg) throws ClassNotFoundException, SQLException {
        this.parseURL = parseURL;
        this.cfg = cfg;
        initConnection();
    }

    private void initConnection() throws ClassNotFoundException, SQLException {
        String driver = cfg.getProperty("driver");
        Class.forName(driver);
        String url = cfg.getProperty("url");
        String login = cfg.getProperty("username");
        String password = cfg.getProperty("password");
        cn = DriverManager.getConnection(url, login, password);
    }

    public void load(ParseURL parseURL) throws SQLException {
        Map<String, Integer> words = new HashMap<>(parseURL.urlParse());
        for (Map.Entry entry: words.entrySet()) {
            try (PreparedStatement statement = cn.prepareStatement("insert into words (word, amount) values (?, ?)")) {
                statement.setString(1, (String) entry.getKey());
                statement.setInt(2, (Integer) entry.getValue());
                statement.execute();
            }
        }
    }
}
