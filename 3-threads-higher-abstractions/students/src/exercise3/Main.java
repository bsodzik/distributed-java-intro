package exercise3;

import common.html.GazetaHtmlDocument;
import common.html.HtmlDocument;

import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        HtmlDocument rootDocument = new GazetaHtmlDocument("http://wiadomosci.gazeta.pl/");
        Set<String> links = rootDocument.getLinks();
        String wordToFound = "sikorski";

        // TODO: Create ExecutorService

        // TODO: Create list of results of type List<Future<Integer>>

        for (String link : links) {
            // TODO: Create new WordCounter and submit it to executorService
            // TODO: Store Future object in list of results
        }

        // TODO: shutdown executor

        int numberOfWords = 0;
        // TODO: Iterate over list of results and for each Future invoke get() method
        // TODO: add value returned from get() method to numberOfWords variable

        System.out.printf("Number of words '%s': %d", wordToFound, numberOfWords);
    }
}
