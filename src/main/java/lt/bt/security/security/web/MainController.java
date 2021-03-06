package lt.bt.security.security.web;

import lt.bt.security.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService service;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("authenticated", service.isAuthenticated());

        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(Model model) {
        model.addAttribute("name", service.getUsername());
        return "user/index";
    }

    @RequestMapping("/user/edit")
    public String editUser() {
        return "user/edit";
    }

    @RequestMapping("/user/profile")
    public String userProfile(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        model.addAttribute("name", name);
        model.addAttribute("authorities", authorities);

        return "user/profile";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("authenticated", service.isAuthenticated());
        if (service.isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
