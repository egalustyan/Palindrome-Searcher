package katherina.galustyan.testtask.irens.palindrome;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber;
import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.searcher.Task;
import katherina.galustyan.testtask.irens.palindrome.searcher.callable.SearchResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.callable.SearcherCallable;
import org.hibernate.mapping.Array;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by kate on 09.04.2019.
 */
public class SearcherCallableTest {

    private static Long userId = 0L;
    private static String sessionId = "NO_SSESION_NUMBER";

    @Test
    public void testSearches() throws Exception {
        checkString("21","11","22");
        checkString("100","99","101");
        checkString("456778","456654","457754");
        checkString("124440697409780745825","124440697404796044421","124440697414796044421");
        checkString("233","232","242");
    }



    public void checkString(String beginString, String minPalindrome, String maxPalindrome) throws Exception {
        SearchResult searchResult = getResultForRightString(beginString);
        assertEquals(searchResult.getMinPalindrome(),minPalindrome);
        assertEquals(searchResult.getMaxPalindrome(), maxPalindrome);
    }

    public SearchResult getResultForRightString(String initString) throws Exception {
        ArrayNumber arrayNumber = new ArrayNumber(initString);
        Task task = new Task(userId, sessionId, arrayNumber, null);
        SearcherCallable searcher = new SearcherCallable(task);
        SearchResult searchResult = searcher.call();
        return searchResult;

    }

}
