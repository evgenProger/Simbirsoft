

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Splitting {
   /** Поле логер  */
   private  final static Logger LOGGER = Logger.getLogger(Splitting.class.getName());
   /** Поле уникальные слова типа Map */
   private final Map<String, Integer> words;

   /** Конструтор - создание нового объекта HashMap */
    public Splitting() {
        words = new HashMap<>();
    }
    /**
     * Метод принимает строку, в которой производится подсчет уникальных слов. Для подсчета используется структура HashMap.
     * @param page - страница, в которой будет вестись подсчет уникальных слов
     * @return возвращает результирующую HashMap words
     */
    public Map<String, Integer> splitStr(String page)  {
        LOGGER.info("Запуск метода splitStr");
        Pattern pattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(page);
        while (matcher.find()) {
            int count = words.getOrDefault(matcher.group().toUpperCase(Locale.ROOT), 0);
            words.put(matcher.group().toUpperCase(Locale.ROOT), count + 1);
        }
        LOGGER.info("конец метода splitStr");
        return words;
    }
}
