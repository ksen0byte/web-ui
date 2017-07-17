package webui.webui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView indexPage(@ModelAttribute User user) {

        return new ModelAndView("index");
    }

    @PostMapping(params = "signin")
    public ModelAndView signIn(@Valid User user, BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (userService.containsValidUser(user)) {
            return new ModelAndView("test", "user", userService.loadUserFromDB(user));
        } else {
            result.addError(new ObjectError("error", "Invalid pass or name"));
            return new ModelAndView("index", "formErrors", result.getAllErrors());
        }
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationForm(@ModelAttribute User user) {

        return new ModelAndView("registration");
    }

    @PostMapping(params = "registration")
    public ModelAndView register(@Valid User user, BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (userService.loadUserFromDB(user) != null) {
            result.addError(new ObjectError("error", "User with the " +
                    "same name already exists!"));
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "formErrors", result.getAllErrors());
        }
        userService.saveUser(user);
        return new ModelAndView("test", "user.id", user.getId());
    }
}
