import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ParseURL {
    /** Поле логер  */
    private final static Logger LOGGER = Logger.getLogger(ParseURL.class.getName());
    /** Поле url */
    private final URL url;

    /** Конструтор - создание нового объекта парсера
     * @param url;
     * */
    public ParseURL(URL url) {
        this.url = url;
    }

    /**
     * В методе происходит чтение и парсинг страницы частями при помощи регулярных выражений. После чего страница
     * передаются в метод splitSTR из класса Splitting, в котором уже происходит подсчет уникальных слов.
     *
     * @return возвращает результирующую HashMap с подсчитанными словами.
     */
    public Map<String, Integer> urlParse() {
        LOGGER.info("Старт метода urlParse");
        Map<String, Integer> result = new HashMap<>();
        Splitting splitting = new Splitting();
        String s;
        StringBuilder sb = new StringBuilder();
        URLConnection connection = null;
        try {
            connection =  url.openConnection();
        } catch (IOException e) {
            LOGGER.error("Страница не найдена", e);
        }
        connection.setDoOutput(true);
        try (BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            while ((s = r.readLine()) != null) {
                if (s.trim().startsWith("<script")) {
                    while (!s.contains("</script")) {
                        s = r.readLine();
                    }
                    s = "";
                }
                if (s.trim().startsWith("<style")) {
                    while (!s.contains("</style")) {
                        s = r.readLine();
                    }
                    s = "";
                }
                if (s.trim().startsWith("<")) {
                    while (!s.contains(">")) {
                        sb.append(s);
                        s = r.readLine();
                    }
                    s = sb.toString() + s;
                    sb = new StringBuilder();
                }
                String str = s.replaceAll("(?s)<!--.+?-->|<script.+?</script>|<.+?>", "")
                        .replaceAll("&nbsp;| +", " ")
                        .replaceAll("\r\n ", "\r\n")
                        .replaceAll("[\r\n\t]+", "\r\n")
                        .replaceAll("\\n", "");
                if (!str.trim().equals("")) {
                    result.putAll(splitting.splitStr(str));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Ошибка Ввода-вывода", e);
        }
        LOGGER.info("Звершение метода urlParse");
        return result;
    }
}
