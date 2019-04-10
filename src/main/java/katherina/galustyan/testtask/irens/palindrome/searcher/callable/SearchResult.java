package katherina.galustyan.testtask.irens.palindrome.searcher.callable;

import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber.OperationResult;

/**
 * Created by kate on 04.04.2019.
 */
public class SearchResult {
    public final static String ERR_MSG_MAX_RESULT = "No found: max digits amount is reached!";
    public final static String ERR_MSG_MIN_RESULT = "No found: zero is reached!";

    private String minPalindrome;
    private String maxPalindrome;
    private boolean isError = false;

    public SearchResult() {

    }

    public void setResultNumber(String resultNumber) {
        this.minPalindrome = resultNumber;
        this.maxPalindrome = resultNumber;
    }

    public String getMinPalindrome() {
        return minPalindrome;
    }

    public void setMinPalindrome(String minPalindrome) {
        this.minPalindrome = minPalindrome;
    }

    public void setMinPalindrome(OperationResult palindrome) {
        setError(palindrome.isExitFromBound());
        setMinPalindrome((palindrome.isExitFromBound()) ? ERR_MSG_MIN_RESULT : palindrome.arrToString());
    }

    public String getMaxPalindrome() {
        return maxPalindrome;
    }

    public void setMaxPalindrome(OperationResult palindrome) {
        setError(palindrome.isExitFromBound());
        setMaxPalindrome((palindrome.isExitFromBound()) ? ERR_MSG_MAX_RESULT : palindrome.arrToString());
    }

    public void setMaxPalindrome(String maxPalindrome) {
        this.maxPalindrome = maxPalindrome;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    @Override
    public String toString() {
        return "";
    }
}
