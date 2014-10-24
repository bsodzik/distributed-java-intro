package exercise4;

import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        String rootUrl = "http://wiadomosci.gazeta.pl/";
        String wordToFound = "sikorski";

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        WebCrawlingTask webCrawlingTask = new WebCrawlingTask(rootUrl, wordToFound);
        Integer numberOfWords = forkJoinPool.invoke(webCrawlingTask);

        System.out.printf("Number of words '%s': %d", wordToFound, numberOfWords);
    }
}
