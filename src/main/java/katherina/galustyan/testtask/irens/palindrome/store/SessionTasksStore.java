package katherina.galustyan.testtask.irens.palindrome.store;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.searcher.Task;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.persistence.Transient;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kate on 05.04.2019.
 */
public class SessionTasksStore implements HttpSessionBindingListener, Serializable{
    private static Logger logger = LoggerFactory.getLogger( SessionTasksStore.class);

    private String sessionId;
    private Long userId;

    private HashMap<String, TaskResult> completedTasks;
    private HashMap<String, Task> executingTasks;
    private HashMap<String, TaskResult> canceledTasks;

    private transient PalindromeSearcher palindromeSearcher;

    public SessionTasksStore(String sessionId, PalindromeSearcher palindromeSearcher) {
        this.userId = -1L;
        this.sessionId = sessionId;
        this.palindromeSearcher = palindromeSearcher;
        completedTasks = new HashMap<String, TaskResult>();
        canceledTasks = new HashMap<String, TaskResult>();
        executingTasks = new HashMap<String, Task>();
    }

    public TaskResult addNewTask(String stringNumber){
        Task task = palindromeSearcher.scheduleNewTask(userId, sessionId, stringNumber);
        if (!task.isDone() && !task.isRejected()){
            executingTasks.put(task.getTaskId(), task);
        } /*else {
            completedTasks.put(task.getTaskId(), task.getTaskResult());
        }*/
        return task.getTaskResult();
    }

    public SessionTasksState getSessionTasksState(){
        SessionTasksState state = new SessionTasksState();
        ArrayList<String> taskIdsForRemoving = new ArrayList<>();

        executingTasks.values().forEach(task ->{
            if (task.isDone()){
                completedTasks.put(task.getTaskId(), task.getTaskResult());
                taskIdsForRemoving.add(task.getTaskId());
            } else{
                state.addExecTaskResults(task.getTaskResult());
            }
        });

        taskIdsForRemoving.forEach(taskId -> executingTasks.remove(taskId));

        completedTasks.values().forEach(state::addFinishedTaskResult);

        canceledTasks.values().forEach(state::addCanceledTaskResult);

        return state;
    }

    public SessionTasksState cancelAllExecutingTasks(){
        SessionTasksState state = new SessionTasksState();

        executingTasks.values().forEach(task ->{
            if (task.isDone()){
                completedTasks.put(task.getTaskId(), task.getTaskResult());
            } else{
                task.cancel();
                canceledTasks.put(task.getTaskId(), task.getTaskResult());
                state.addCanceledTaskResult(task.getTaskResult());
            }
        });

        executingTasks.keySet().forEach(executingTasks::remove);

        completedTasks.values().forEach(state::addFinishedTaskResult);
        canceledTasks.values().forEach(state::addCanceledTaskResult);

        return state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logger.info("Session task storage: starting closing threads...");
        this.cancelAllExecutingTasks();
    }

    public void setPalindromeSearcher(@Autowired PalindromeSearcher palindromeSearcher) {
        this.palindromeSearcher = palindromeSearcher;
    }

    public void printToLog(String firstMsg) {
        logger.debug(firstMsg);
        logger.debug("Executing Tasks:");
        executingTasks.values().forEach(task -> logger.debug(task.toString()));
        logger.debug("Done Tasks:");
        completedTasks.values().forEach(taskResult -> logger.debug(taskResult.toInfoString()));
        logger.debug("Canceled Tasks:");
        canceledTasks.values().forEach(taskResult -> logger.debug(taskResult.toInfoString()));

    }
}
