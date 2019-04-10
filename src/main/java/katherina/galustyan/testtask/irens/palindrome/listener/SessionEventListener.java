package katherina.galustyan.testtask.irens.palindrome.listener;

import katherina.galustyan.testtask.irens.palindrome.searcher.PalindromeSearcher;
import katherina.galustyan.testtask.irens.palindrome.store.SessionTasksStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by kate on 07.04.2019.
 */
@WebListener
public class SessionEventListener implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger( SessionEventListener.class);

    @Autowired
    private PalindromeSearcher palindromeSearcher;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        SessionTasksStore tasksStore = new SessionTasksStore(session.getId(),palindromeSearcher);
        session.setAttribute("tasksStore", tasksStore);
        logger.info("New session with id=" + session.getId() + " and with separate task storage has created.");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        SessionTasksStore tasksStore = (SessionTasksStore) session.getAttribute("tasksStore");
        tasksStore.cancelAllExecutingTasks();
        logger.debug("Session with id=" + session.getId() + "has destroyed. All tasks from Session task storage were canceled.");
    }
}
