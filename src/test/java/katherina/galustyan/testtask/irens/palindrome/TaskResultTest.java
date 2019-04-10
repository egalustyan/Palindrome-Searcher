package katherina.galustyan.testtask.irens.palindrome;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber;
import katherina.galustyan.testtask.irens.palindrome.searcher.Task;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by kate on 10.04.2019.
 */
public class TaskResultTest {

    private static Long userId = 0L;
    private static String sessionId = "NO_SSESION_NUMBER";

    @Test
    public void testWrongStrings(){
        checkWrongString("");
        checkWrongString(null);
        checkWrongString("hgkjgka");
        checkWrongString("-34");
    }

    @Test
    public void testPalindromes(){
        checkPalindrome("4");
        checkPalindrome("33");
        checkPalindrome("12321");
        checkPalindrome("0989");
        checkPalindrome("00002332");
        checkPalindrome("   675414576    ");
    }

    @Test
    public void testNumberStrings(){
        checkNumberString("12356");
        checkNumberString("000002345");
        checkNumberString("   456   ");
        checkNumberString("6555584751765961945914681969146964969469163919649469164919364919196916");
    }


    public void checkPalindrome(String beginString){
        ArrayNumber arrayNumber = new ArrayNumber(beginString);
        Task task = new Task(userId, sessionId, arrayNumber, null);
        TaskResult taskResult = task.getTaskResult();
        assertEquals(taskResult.isTaskDone(),true);
        assertEquals(taskResult.isTaskRejected(),false);
        assertEquals(taskResult.getMinResult(), arrayNumber.toString());
        assertEquals(taskResult.getMaxResult(), arrayNumber.toString());
    }

    public void checkWrongString(String beginString){
        TaskResult taskResult = getResultForString(beginString);
        assertEquals(taskResult.isTaskDone(),false);
        assertEquals(taskResult.isTaskRejected(),true);
        assertNull(taskResult.getMinResult());
        assertNull(taskResult.getMaxResult());
    }

    public void checkNumberString(String beginString){
        TaskResult taskResult = getResultForString(beginString);
        assertEquals(taskResult.isTaskDone(),false);
        assertEquals(taskResult.isTaskRejected(),false);
        assertNull(taskResult.getMinResult());
        assertNull(taskResult.getMaxResult());
    }

    public TaskResult getResultForString(String initString){
        ArrayNumber arrayNumber = new ArrayNumber(initString);
        Task task = new Task(userId, sessionId, arrayNumber, null);
        return task.getTaskResult();
    }
}
