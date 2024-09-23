package hoang.luan.store.controller;

import hoang.luan.store.model.FeedBack;
import hoang.luan.store.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackService feedBackService;
//    @GetMapping("/getall")
//    public String getAllFeedBack(Model model){
//        List<FeedBack> feedBacks = feedBackService.getAllFeedBack();
//        model.addAttribute("feedbacks",feedBacks);
//        return "report-feedback";
//    }
    @GetMapping("/getall")
    public String getAllFeedBack(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Page<FeedBack> feedBacks = feedBackService.getAllFeedBack(PageRequest.of(page, size));
        model.addAttribute("feedbacks", feedBacks);
        return "report-feedback";
    }
    @GetMapping("/getbystar")
    public String getFeedBackByStar(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam("star") Integer star) {
        Page<FeedBack> feedBacks = feedBackService.getFeedBackByStar(star,PageRequest.of(page, size));
        model.addAttribute("feedbacks", feedBacks);
        return "report-feedback";
    }
    @GetMapping("/feedback/{idtable}")
    public String feedBack(Model model, @PathVariable("idtable") Integer idtable){
        model.addAttribute("idtable",idtable);
        return "customer-feedback";
    }
    @PostMapping("/feedback")
    public @ResponseBody String feedBack(@ModelAttribute FeedBack feedBack){
        try {
            feedBackService.addFeedBack(feedBack);
            return "Đánh giá thành công";
        }catch (Exception e){
            return "Đánh giá thất bại";
        }
    }
}
