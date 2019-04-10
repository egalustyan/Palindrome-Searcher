package katherina.galustyan.testtask.irens.palindrome.entity;

import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber;
import katherina.galustyan.testtask.irens.palindrome.searcher.callable.SearchResult;

import java.io.Serializable;

/**
 * Created by kate on 04.04.2019.
 */
public class TaskResult implements Serializable{
    private String taskId;

    private Long userId;
    private String sessionId;
    private String beginNumber;

    private boolean taskDone;
    private boolean taskRejected;
    private boolean taskCanceled;
    private String minResult;
    private String maxResult;

    public TaskResult(String taskId, Long userId, String sessionId, ArrayNumber arrayNumber) {
        this.taskId = taskId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.beginNumber = (arrayNumber.isNormalInit()) ? arrayNumber.toString()
                : arrayNumber.getWrongInitString();
        this.taskRejected = !arrayNumber.isNormalInit();
        this.taskDone = arrayNumber.isPalindrome();
        if (taskDone){
            this.minResult = beginNumber;
            this.maxResult = beginNumber;
        }

    }

    public String getTaskId() {
        return taskId;
    }

    public String getBeginNumber() {
        return beginNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setResults(SearchResult searchResult) {
        this.minResult = searchResult.getMinPalindrome();
        this.maxResult = searchResult.getMaxPalindrome();
        this.taskDone = true;
    }

    public void setResults(String minResult, String maxResult) {
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.taskDone = true;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public String getMinResult() {
        return minResult;
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    public String toJSONString() {
        return "{" +
                "\"taskId\":\"" + taskId + '\"' +
                ", \"userId\":" + userId +
                ", \"sessionId\":\"" + sessionId + '\"' +
                ", \"beginNumber\":\"" + beginNumber + '\"' +
                ", \"taskDone\":" + taskDone +
                ", \"taskRejected\":" + taskRejected +
                ", \"taskCanceled\":" + taskCanceled +
                ", \"minResult\":\"" + minResult + '\"' +
                ", \"maxResult\":\"" + maxResult + '\"' +
                '}';
    }

    public String toInfoString(){
        StringBuilder result = new StringBuilder("TaskResult{ Task{");
        result.append("taskId='").append(taskId).append('\'')
                .append(", beginNumber='").append(beginNumber).append("\'}, Result: {");
        if (taskRejected){
            result.append("state: REJECTED");
        } else if (taskDone){
            result.append("state: DONE, minResult=")
                    .append(minResult).append(", maxResult=").append(maxResult);
        } else{
            result.append("state: SEARCHING");
        }
        result.append("}}");
        return result.toString();
    }

    public boolean isTaskDone() {
        return taskDone;
    }

    public boolean isTaskRejected() {
        return taskRejected;
    }

    public void setTaskCanceled() {
        this.taskCanceled = true;
    }

    public TaskResultState getState(){
        if (taskRejected){
            return TaskResultState.REJECTED;
        }

        if (taskDone){
            return TaskResultState.DONE;
        }

        if (taskCanceled){
            return TaskResultState.CANCELED;
        }

        return TaskResultState.EXECUTING;
    }

    public enum TaskResultState{
        REJECTED, DONE, EXECUTING, CANCELED;
    }
}
