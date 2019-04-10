package katherina.galustyan.testtask.irens.palindrome.searcher.callable;

import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber;
import katherina.galustyan.testtask.irens.palindrome.searcher.Task;
import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber.OperationResult;

import java.util.concurrent.Callable;


/**
 * Created by kate on 04.04.2019.
 */
public class SearcherCallable implements Callable<SearchResult> {

    public final static String ERR_MSG_MAX_RESULT = "No found: max digits amount is reached!";
    public final static String ERR_MSG_MIN_RESULT = "No found: zero is reached!";
    private ArrayNumber arrayNumber;
    private SearchResult searchResult;

    public SearcherCallable(Task userTask) {
        this.arrayNumber = userTask.getArrayNumber().clone();
        this.searchResult = new SearchResult();
    }

    @Override
    public SearchResult call() throws Exception {
        if (arrayNumber.isPalindrome()){
            searchResult.setError(false);
            searchResult.setResultNumber(arrayNumber.toString());
            return searchResult;
        }

        OperationResult firstPalindrome = arrayNumber.getNearestPalindrome();
        if (arrayNumber.isLessThen(firstPalindrome)){
            searchResult.setMaxPalindrome(firstPalindrome.arrToString());
            searchResult.setMinPalindrome(arrayNumber.getPalindromeWithDecCentralDigit());
        }else{
            searchResult.setMinPalindrome(firstPalindrome.arrToString());
            searchResult.setMaxPalindrome(arrayNumber.getPalindromeWithIncCentralDigit());
        }
        return searchResult;
    }
}
