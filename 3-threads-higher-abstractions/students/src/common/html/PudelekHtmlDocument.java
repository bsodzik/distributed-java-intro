package common.html;

public class PudelekHtmlDocument extends HtmlDocument {

    public PudelekHtmlDocument(String documentUrl) {
        super(documentUrl);
    }

    @Override
    protected String getLinkPrefix() {
        return "http://www.pudelek.pl/artykul";
    }

    @Override
    protected String getContentSelector() {
        return ".single-entry";
    }
}
