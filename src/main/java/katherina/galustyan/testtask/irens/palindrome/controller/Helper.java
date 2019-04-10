package katherina.galustyan.testtask.irens.palindrome.controller;

import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by kate on 07.04.2019.
 */
public class Helper {

    private static Logger logger = LoggerFactory.getLogger(Helper.class);

    public static SessionTasksStore getTasksStore(HttpServletRequest servletRequest, PalindromeSearcher palindromeSearcher){
        HttpSession httpSession = servletRequest.getSession();
        SessionTasksStore tasksStore = (SessionTasksStore) httpSession.getAttribute("tasksStore");
        if (tasksStore == null){
            System.out.println("Creating new task storage for session with id=" + httpSession.getId());
            tasksStore = new SessionTasksStore(httpSession.getId(),palindromeSearcher);
            httpSession.setAttribute("tasksStore", tasksStore );
        }
        return tasksStore;
    }
}
