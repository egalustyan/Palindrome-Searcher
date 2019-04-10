package katherina.galustyan.testtask.irens.palindrome.searcher;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.callable.SearchJob;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kate on 04.04.2019.
 */
public class Task implements Serializable {
    private static long task_count = 0L;

    private String taskId;

    private Long userId;
    private String sessionId;
    private ArrayNumber arrayNumber;

    private TaskResult taskResult;

    private transient PalindromeSearcher palindromeSearcher;
    private transient SearchJob searcher;


    public Task(Long userId, String sessionId, ArrayNumber arrayNumber, PalindromeSearcher palindromeSearcher) {
        this.palindromeSearcher = palindromeSearcher;
        this.taskId = generateNewId();
        this.userId = userId;
        this.sessionId = sessionId;
        this.arrayNumber = arrayNumber;
        taskResult = new TaskResult(taskId, userId, sessionId, arrayNumber);
    }

    private synchronized String generateNewId() {
        task_count++;
        Date dateNow = new Date();
        return (task_count + "_" + dateNow.getTime());
    }

    public String getTaskId() {
        return taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public ArrayNumber getArrayNumber() {
        return arrayNumber;
    }

    public boolean isResultDone(){
        return taskResult.isTaskDone();
    }

    public boolean isRejected(){
        return (taskResult.isTaskRejected());
    }

    public boolean isDone(){
        if (isResultDone()){
            return true;
        }
        if (searcher == null){
            return false;
        }
        if (searcher.isSearchDone()){
            taskResult.setResults(searcher.getSearchResult());
            if (palindromeSearcher != null){
                palindromeSearcher.decTasksAmount();
            }
            return true;
        }
        return false;
    }

    public TaskResult getTaskResult(){
        return taskResult;
    }

    public void setSearcher(SearchJob searcher){
        this.searcher = searcher;
    }

    @Override
    public String toString() {
        return taskResult.toString();
    }

    public void cancel() {
        if (isDone() || isRejected()){
            return;
        }
        taskResult.setTaskCanceled();
        if (searcher != null) {
            searcher.cancel();
        }
    }
}
