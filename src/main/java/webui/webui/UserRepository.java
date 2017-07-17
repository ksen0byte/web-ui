package webui.webui;

import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.atomic.AtomicLong;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);
}
