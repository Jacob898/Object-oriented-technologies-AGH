package app;

import java.io.IOException;
import java.util.List;

public class CrawlerApp {

    public static final String GOOGLE_CUSTOM_SEARCH_API_KEY = "AIzaSyBnCuSOQQO-FIlCf1kDnN_1TMvJ9IonffY";

    private static final List<String> TOPICS = List.of("Agent Cooper", "Sherlock", "Poirot", "Detective Monk");


    public static void main(String[] args) throws IOException, InterruptedException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
  //      photoCrawler.downloadPhotoExamples();
        photoCrawler.downloadPhotosForQuery("batman");
       // photoCrawler.downloadPhotosForMultipleQueries(TOPICS);
       // Thread.sleep(100_000);
    }
}