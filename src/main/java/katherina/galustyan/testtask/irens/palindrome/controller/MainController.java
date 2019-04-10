package katherina.galustyan.testtask.irens.palindrome.controller;

import katherina.galustyan.testtask.irens.palindrome.entity.TaskResult;
import katherina.galustyan.testtask.irens.palindrome.entity.User;
import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.service.UserService;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksState;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksStore;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kate on 06.04.2019.
 */
@Controller
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PalindromeSearcher palindromeSearcher;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model uiModel, HttpServletRequest httpServletRequest){
        SessionTasksStore tasksStore = Helper.getTasksStore(httpServletRequest, palindromeSearcher);
        SessionTasksState tasksState = tasksStore.getSessionTasksState();
        uiModel.addAttribute("executingTasks", tasksState.getExecTaskResults());
        uiModel.addAttribute("finishedTasks", tasksState.getFinishedTaskResults());
        uiModel.addAttribute("canceledTasks", tasksState.getCanceledTaskResults());

        return "palindrome_searcher";

    }

    @RequestMapping(params="close", value = "/", method = RequestMethod.GET)
    public ResponseEntity invalidateSession(HttpSession httpSession){
        logger.debug("Received close tab/page event: closing session and cancelling executing tasks...");
        httpSession.invalidate();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
