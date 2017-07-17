package webui.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User loadUserFromDB(User user){
        return userRepository.findByUsername(user.getUsername());
    }

    //checks password and username
    public boolean containsValidUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        return (userFromDB != null && userFromDB.getPassword().equals(user.getPassword()));
    }
}
