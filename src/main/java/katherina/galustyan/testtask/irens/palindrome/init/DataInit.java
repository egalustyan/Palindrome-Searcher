package katherina.galustyan.testtask.irens.palindrome.init;

import katherina.galustyan.testtask.irens.palindrome.entity.User;
import katherina.galustyan.testtask.irens.palindrome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by kate on 06.04.2019.
 */
@Component
public class DataInit implements ApplicationRunner {

    private UserService userService;

    @Autowired
    public DataInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        long count = userService.count();

        if (count == 0) {
            User admin = new User("admin");
            admin.setPassword("admin");
            userService.save(admin);
        }
    }
}
