package common.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class HtmlDocument {

    protected final Document document;

    protected HtmlDocument(String documentUrl) {
        document = getDocument(documentUrl);
    }

    public Set<String> getLinks() {
        if (document == null) {
            return Collections.emptySet();
        }

        Elements elements = document.select("a");

        Set<String> result = new HashSet<String>();
        for (Element element : elements) {
            String href = element.attr("href");

            if (href.startsWith(getLinkPrefix())) {
                if (href.indexOf('#') > 0) {
                    href = href.substring(0, href.indexOf('#'));
                }
                result.add(href);
            }
        }
        return result;
    }

    public String getContent() {
        return document == null ? "" : document.select(getContentSelector()).text();
    }

    protected abstract String getLinkPrefix();

    protected abstract String getContentSelector();

    private Document getDocument(String documentUrl) {
        try {
            return Jsoup.connect(documentUrl).timeout(5000).get();
        } catch (IOException e) {
            System.err.println("Unable to get document " + documentUrl);
            return null;
        }
    }
}
