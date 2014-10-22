package exercise4;

public class Main {

    public static void main(String[] args) {
        String rootUrl = "http://wiadomosci.gazeta.pl/";
        String wordToFound = "sikorski";
        Integer numberOfWords = 0;

        // TODO: Create new ForkJoinPool object
        // TODO: Create new WebCrawlingTask for rootUrl and wordToFound
        // TODO: Invoke invoke method on ForkJoinPool object passing WebCrawlingTask
        // TODO: Assign result of invoke method to numberOfWords variable

        System.out.printf("Number of words '%s': %d", wordToFound, numberOfWords);
    }
}
