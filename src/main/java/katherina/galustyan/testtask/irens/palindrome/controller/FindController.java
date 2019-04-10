package katherina.galustyan.testtask.irens.palindrome.controller;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kate on 07.04.2019.
 */
@Controller
@RequestMapping("/find")
public class FindController {


    @Autowired
    private PalindromeSearcher palindromeSearcher;

    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json")
    public TaskResult findPalindrome(@RequestBody String stringNumber, HttpServletRequest servletRequest) {
        SessionTasksStore tasksStore = Helper.getTasksStore(servletRequest, palindromeSearcher);
        TaskResult taskResult = tasksStore.addNewTask(stringNumber);
        return taskResult;
    }
}