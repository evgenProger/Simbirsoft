import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParseURLTest {
    @Test
    public void whenGetUrlThenParse() throws MalformedURLException {
        Map<String, Integer> res = new HashMap<>();
        URL url = Paths.get("src/main/resources/test1.html").toUri().toURL();
        ParseURL parseURL = new ParseURL(url);
        System.out.println(parseURL.urlParse());
        res.putAll(parseURL.urlParse());
        System.out.println(res);
        assertThat(res.get("РАЗРАБОТКА"), is(2));
    }

    @Test
    public void whenGetUrlThenGetContent() throws MalformedURLException {
        URL url = Paths.get("src/main/resources/test2.html").toUri().toURL();
        ParseURL parseURL = new ParseURL(url);
        Map<String, Integer> res = new HashMap<>(parseURL.urlParse());
        System.out.println(res);
        assertThat(res.get("РАЗРАБОТКА"), is(1));
    }

    @Test
    public void whenGetUrlThenGetText() throws MalformedURLException {
        URL url = Paths.get("src/main/resources/curshunter.html").toUri().toURL();
        ParseURL parseURL = new ParseURL(url);
        Map<String, Integer> res = new HashMap<>(parseURL.urlParse());
        System.out.println(res);
        assertThat(res.get("ПОЛНОЦЕННЫЙ"), is(2));
    }

}