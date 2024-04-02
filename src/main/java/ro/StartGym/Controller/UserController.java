package ro.StartGym.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ro.StartGym.Exceptions.UserException;
import ro.StartGym.Service.UserService;
import ro.StartGym.security.UserSession;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserSession userSession;
@GetMapping("login-action")
    public ModelAndView login(@RequestParam(value="username") String username,@RequestParam(value="password") String password){
    ModelAndView modelAndView = new ModelAndView("index");
    try {
        userService.loginUser(username, password);
        userSession.setId(userService.returnIdForLoggedUser(username));
        modelAndView = new ModelAndView("dashboard");
    }catch (UserException e){
        modelAndView.addObject("error", e.getMessage());
    }
    return modelAndView;

}

@GetMapping("/register")
    public ModelAndView register(){
    return new ModelAndView("register");
}
    @GetMapping("register-action")
    public ModelAndView newAccount(@RequestParam(value="username") String username,
                                   @RequestParam(value="password1") String password1,
                                   @RequestParam(value="password2") String password2){
        ModelAndView modelAndView = new ModelAndView("register");
        try {
            userService.validateAndRegister(username, password1, password2);
            modelAndView = new ModelAndView("index");
        }catch (UserException e){
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;

    }
}
