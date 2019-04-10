package katherina.galustyan.testtask.irens.palindrome.service;

import katherina.galustyan.testtask.irens.palindrome.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kate on 06.04.2019.
 */
@Repository
public interface UserService extends CrudRepository<User, Long> {

}
