package common.html;

public class GazetaHtmlDocument extends HtmlDocument {

    public GazetaHtmlDocument(String documentUrl) {
        super(documentUrl);
    }

    @Override
    protected String getLinkPrefix() {
        return "http://wiadomosci.gazeta.pl/wiadomosci";
    }

    @Override
    protected String getContentSelector() {
        return "#article";
    }
}
