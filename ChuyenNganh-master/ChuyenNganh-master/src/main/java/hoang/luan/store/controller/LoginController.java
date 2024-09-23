package hoang.luan.store.controller;


import hoang.luan.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;



    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }

//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "register";
//        }
//
//        userService.register(user);
//        return "redirect:/login";
//    }


}