package katherina.galustyan.testtask.irens.palindrome.searcher;

import katherina.galustyan.testtask.irens.palindrome.searcher.callable.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by kate on 04.04.2019.
 */

public class PalindromeSearcher {

    private volatile AtomicLong tasksAmount;

    private ConcurrentLinkedDeque<Task> taskQueue;
    private ExecutorService taskExecutor;
    private Thread startingTaskWorker;

    public PalindromeSearcher() {
        tasksAmount = new AtomicLong(0L);
        taskQueue = new ConcurrentLinkedDeque<Task>();
        taskExecutor = Executors.newCachedThreadPool();
        startingTaskWorker = new Thread(new StartingTaskRunnable(this));
        startingTaskWorker.setDaemon(true);
        startingTaskWorker.start();
    }

    public  void decTasksAmount() {
        tasksAmount.decrementAndGet();
    }

    public synchronized boolean isAllTasksDone(){
        return  (tasksAmount.get() == 0);
    }

    public Task scheduleNewTask(Long userId, String sessionId, String stringNumber){
        ArrayNumber arrayNumber = new ArrayNumber(stringNumber);
        Task newTask = new Task(userId, sessionId, arrayNumber, this);
        if ((arrayNumber.isNormalInit()) && (!arrayNumber.isPalindrome())){
            taskQueue.add(newTask);
            tasksAmount.incrementAndGet();
        }
        return newTask;
    }

    public Task scheduleNewTask(String stringNumber){
        return scheduleNewTask(-1L,"NO_SESSION",stringNumber);
    }

    protected boolean addTaskToExecutor() throws InterruptedException {
        Task task = taskQueue.peek();
        if (task == null){
            return false;
        }
        SearchJob resultJob = null;
        resultJob = submitSearchCallable(new SearcherCallable(task));
        task.setSearcher(resultJob);
        taskQueue.poll();
        return true;
    }


    private SearchJob submitSearchCallable(SearcherCallable searcherCallable) throws InterruptedException {
        boolean done = false;
        Future<SearchResult> futureResult = null;

        while (!done){
            try{
                futureResult = taskExecutor.submit(searcherCallable);
                done = true;
                return new SearchJob(futureResult);
            }catch (RejectedExecutionException e){
                Thread.sleep(1000L);
            }
        }

        return null;
    }

    public class StartingTaskRunnable implements Runnable{

        private final PalindromeSearcher palindromeSearcher;

        public StartingTaskRunnable(PalindromeSearcher palindromeSearcher){
            this.palindromeSearcher = palindromeSearcher;
        }

        @Override
        public void run() {
            boolean addResult, stop = false;
            while (!stop){
                try {
                    addResult = palindromeSearcher.addTaskToExecutor();
                    Thread.sleep((addResult) ? 50L : 1000L);
                } catch (InterruptedException e) {
                    stop = true;
                }
            }
        }
    }
}
