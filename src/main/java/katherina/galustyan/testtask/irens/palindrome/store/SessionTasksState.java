package katherina.galustyan.testtask.irens.palindrome.store;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kate on 04.04.2019.
 */
public class SessionTasksState {

    private List<TaskResult> executingTaskResults;
    private List<TaskResult> completedTaskResults;
    private List<TaskResult> canceledTaskResults;

    public SessionTasksState() {
        executingTaskResults = new LinkedList<>();
        completedTaskResults = new LinkedList<>();
        canceledTaskResults = new LinkedList<>();
    }

    public List<TaskResult> getExecTaskResults() {
        return Collections.unmodifiableList(executingTaskResults);
    }

    public List<TaskResult> getFinishedTaskResults() {
        return Collections.unmodifiableList(completedTaskResults);
    }

    public List<TaskResult> getCanceledTaskResults() {
        return canceledTaskResults;
    }

    protected void addExecTaskResults(TaskResult taskResult){
        executingTaskResults.add(taskResult);
    }

    protected void addFinishedTaskResult(TaskResult taskResult){
        completedTaskResults.add(taskResult);
    }

    protected void addCanceledTaskResult(TaskResult taskResult){
        canceledTaskResults.add(taskResult);
    }

    public void printTasks(String message, List<TaskResult> taskResults){
        System.out.println(message);
        taskResults.forEach(taskResult -> System.out.println(taskResult.toString()));
    }

    public void printFinishedTasks(){
        printTasks("Completed Tasks:", completedTaskResults);
    }

    public void printExecutingTasks(){
        printTasks("Executing Tasks:", executingTaskResults);
    }

    public boolean AllTasksHasDone() {
        return executingTaskResults.isEmpty() && canceledTaskResults.isEmpty();
    }

    public TaskResult findTaskResultById(String taskId){
        TaskResult taskResult = findTaskResultInList(taskId, completedTaskResults);

        if (taskResult != null){
            return taskResult;
        }

        taskResult = findTaskResultInList(taskId, executingTaskResults);

        if (taskResult != null){
            return taskResult;
        }

        taskResult = findTaskResultInList(taskId, canceledTaskResults);

        if (taskResult != null){
            return taskResult;
        }

        return null;

    }

    public TaskResult findTaskResultInList(String taskId, List<TaskResult> taskResults){
        for (TaskResult taskResult: taskResults){
            if (taskResult.getTaskId().equals(taskId)){
                return taskResult;
            }
        }
        return null;
    }


}
