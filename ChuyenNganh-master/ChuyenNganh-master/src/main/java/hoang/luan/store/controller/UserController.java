package hoang.luan.store.controller;


import hoang.luan.store.model.CalendarStaff;
import hoang.luan.store.model.CalendarStaffResponse;
import hoang.luan.store.model.User;
import hoang.luan.store.repository.UserRepository;
import hoang.luan.store.service.CalendarStaffService;
import hoang.luan.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CalendarStaffService calendarStaffService;

    @GetMapping("/view")
    public String viewprofile(HttpServletRequest request, Model model) {
        String username = request.getUserPrincipal().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user=userOptional.get();
        model.addAttribute("user",user);
        return "profile";
    }
    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        User user1= userService.getUserByUserName(user.getUsername());
        user.setPassword(user1.getPassword());
        user.setRoles(user1.getRoles());
        userService.updateUser(user);
        return "redirect:/user/view";
    }
    @GetMapping("/calender")
    public String showCalender(){
        return "user-calender";
    }
    @GetMapping("/calenderuser")
    public @ResponseBody List<CalendarStaffResponse> getCalender(HttpServletRequest request){
        String username = request.getUserPrincipal().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user=userOptional.get();
        List<CalendarStaffResponse> calendarStaffResponses= new ArrayList<>();
        for (CalendarStaff c: user.getCalendars() ) {
            CalendarStaffResponse calendarStaffResponse = new CalendarStaffResponse();
            calendarStaffResponse.setTitle(c.getTitle());
            calendarStaffResponse.setStart(c.getStart());
            calendarStaffResponse.setEnd(c.getEnd());
            calendarStaffResponses.add(calendarStaffResponse);
        }
        return calendarStaffResponses;
    }
}
