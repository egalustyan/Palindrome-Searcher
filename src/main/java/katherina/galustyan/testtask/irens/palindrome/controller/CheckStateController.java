package katherina.galustyan.testtask.irens.palindrome.controller;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.ArrayNumber;
import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksState;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by kate on 08.04.2019.
 */
@Controller
@RequestMapping("/checkstate")
public class CheckStateController {

    @Autowired
    private PalindromeSearcher palindromeSearcher;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<TaskResult> sendSessionTasksState(HttpServletRequest servletRequest){
        SessionTasksStore tasksStore = Helper.getTasksStore(servletRequest, palindromeSearcher);
        SessionTasksState tasksState = tasksStore.getSessionTasksState();
        return tasksState.getFinishedTaskResults();
    }

    @ResponseBody
    @RequestMapping(value = "/one", method = RequestMethod.GET, produces = "application/json")
    public TaskResult sendSessionTaskResult(@RequestParam String taskId, HttpServletRequest servletRequest){
        SessionTasksStore tasksStore = Helper.getTasksStore(servletRequest, palindromeSearcher);
        SessionTasksState tasksState = tasksStore.getSessionTasksState();
        TaskResult taskResult = tasksState.findTaskResultById(taskId);
        if (taskResult == null){
            taskResult = new TaskResult(taskId,-1L,servletRequest.getSession().getId(),new ArrayNumber("no found"));
            taskResult.setResults("no", "no");
        }
        return taskResult;
    }
}
