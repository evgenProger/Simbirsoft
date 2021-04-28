import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        LOGGER.info("Старт метода Main");
        URL url = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter URL");
        String str = in.nextLine();
        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            LOGGER.error("Страница не найдена", e);
        }
        ParseURL parseURL = new ParseURL(url);
        Map<String, Integer> splitStr = parseURL.urlParse();
        for (Map.Entry<String, Integer> stringIntegerEntry : splitStr.entrySet()) {
            System.out.println(stringIntegerEntry.getKey() + " " + stringIntegerEntry.getValue());
        }

        FileReader reader = new FileReader("src/main/resources/app.properties");
        Properties properties = new Properties();
        properties.load(reader);
        Database database = new Database(parseURL, properties);
        database.load(parseURL);
    }
}
