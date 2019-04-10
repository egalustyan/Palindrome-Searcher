package katherina.galustyan.testtask.irens.palindrome.searcher.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by kate on 05.04.2019.
 */
public class SearchJob {

    private Future<SearchResult> job;
    private SearchResult searchResult = null;


    public SearchJob(Future<SearchResult> job) {
        this.job = job;
    }

    public boolean isSearchDone() {
        return job.isDone();
    }

    public boolean isError() {
        return (searchResult == null) ? false : searchResult.isError();
    }

    public SearchResult getSearchResult() {
        if (searchResult != null){
            return searchResult;
        }

        if (isSearchDone()){
            try {
                SearchResult searchResult = job.get();
                this.searchResult = searchResult;
                return searchResult;
            } catch (InterruptedException | ExecutionException e) {
                //this catch never will work
            }
        }

        return null;
    }

    public void cancel() {
        if (getSearchResult() == null){
            job.cancel(true);
        }
    }
}
