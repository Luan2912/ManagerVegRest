package hoang.luan.store.controller;

import hoang.luan.store.model.FeedBack;
import hoang.luan.store.model.Invoice;
import hoang.luan.store.model.OpenAiResponse;
import hoang.luan.store.model.Product;
import hoang.luan.store.service.FeedBackService;
import hoang.luan.store.service.InvoiceService;
import hoang.luan.store.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class ChatGPTController {

    @Value("${openai.api.key}") // Đặt giá trị trong file application.properties hoặc application.yml
    private String openaiApiKey;
    @Autowired
    private ProductService productService;
     @Autowired
      private InvoiceService invoiceService;
    @Autowired
    private FeedBackService feedBackService;
    String request = "Nhân viên tư vấn khách hàng trả lời câu hỏi của khách một cách ngắn gọn, dễ đọc.";
    String info = "Thông tin nhà hàng: Tên: VegEats, Địa chỉ:  Trâu Quỳ, Gia Lâm, Hà Nội, " +
            " Nhà hàng chuyên phục vụ ẩm thực đa dạng và đẳng cấp, từ món sáng đến món tráng miệng tinh tế.";

    String menu ="";
    String statustable="";
    @Scheduled(fixedRate = 60000) // 1 minutes = 15 * 60 * 1000 milliseconds
    public void updateInfoStoreForAI() {
        List<Product> products = productService.getAllProductForCustomer(1);
        menu="";
        statustable="thông tin tất cả các hóa đơn chưa thanh toán ";
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(localeVN);

        for (Product p: products) {
            menu+="tên:"+p.getName()+" giá: "+numberFormat.format(p.getPrice());
        }
        List<Invoice> invoices = invoiceService.getInvoiceByStatus(0);
        for (Invoice p: invoices) {
            statustable+="mã hóa đơn:"+p.getId()+"bàn số "+p.getIdTable()+" tổng tiền : "+numberFormat.format(p.getTotal())+"ngàn"+", ";
        }
    System.out.println("Data Ai  đã được cập nhập");
    }

//
//    @GetMapping("/chat")
//    public ResponseEntity<String> getChatResult(Model model , @RequestParam("content") String content) {
//
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append(request);
//        stringBuffer.append(info);
//        stringBuffer.append(menu);
//        stringBuffer.append(content);
//        String result = stringBuffer.toString();
//        System.out.println(result.length());
//
//        String apiUrl = "https://api.openai.com/v1/chat/completions";
//        String gptModel = "gpt-4o";
//        String payload = "{ \"model\": \"" + gptModel + "\", " +
//                "\"messages\": [ {\"role\": \"user\", \"content\": \""+result+"\"}," +
//                "{\"role\": \"user\", \"content\": \"\"}," +
//                "{\"role\": \"user\", \"content\": \"\"}] }";
//        RestTemplate restTemplate = new RestTemplate();
//        OpenAiResponse response = restTemplate.postForObject(apiUrl, createHttpEntity(payload), OpenAiResponse.class);
//        return new ResponseEntity<>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
//    }



    @GetMapping("/chat")
    public ResponseEntity<String> getChatResult(Model model , @RequestParam("content") String content) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String gptModel = "gpt-4o";
        String payload = "{ \"model\": \"" + gptModel + "\", " +
                "\"messages\": [ {\"role\": \"user\", \"content\": \""+request+"\"}," +
                "{\"role\": \"user\", \"content\": \""+info+menu+"\"}," +
                "{\"role\": \"user\", \"content\": \""+content+"\"}] }";
        RestTemplate restTemplate = new RestTemplate();
        OpenAiResponse response = restTemplate.postForObject(apiUrl, createHttpEntity(payload), OpenAiResponse.class);
        return new ResponseEntity<>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
    }

    @GetMapping("/khuyennghi")
    public String getKhuyenNghi(Model model ) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String gptModel = "gpt-4o";
        String payload = "{ \"model\": \"" + gptModel + "\", " +
                "\"messages\": [ {\"role\": \"user\", \"content\": \""+getDataChatGPT()+"\"}," +
                "{\"role\": \"user\", \"content\": \"\"}," +
                "{\"role\": \"user\", \"content\": \"\"}] }";
        RestTemplate restTemplate = new RestTemplate();
        OpenAiResponse response = restTemplate.postForObject(apiUrl, createHttpEntity(payload), OpenAiResponse.class);
        model.addAttribute("content",response.getChoices().get(0).getMessage().getContent());
        System.out.println(response.getChoices().get(0).getMessage().getContent());
        return "khuyennghi";
    }
    private String getDataChatGPT() {
        List<FeedBack> temps = feedBackService.getNewFeedBack();
        StringBuilder contents = new StringBuilder("Đây là 30 đánh giá gần nhất của cửa hàng được phân cách nhau bởi dấu ';'." +
                " Hãy đưa ra các khuyến nghị cho chủ cửa hàng để kinh doanh tốt hơn trả lời 1 cách ngắn gọn. ");

        String[] saoContents = new String[5];

        for (FeedBack feedback : temps) {
            int stars = feedback.getStars();
            if (stars >= 1 && stars <= 5) {
                if (saoContents[stars - 1] == null) {
                    saoContents[stars - 1] = feedback.getContent() + "," + feedback.getStars() + " sao,sdt "+feedback.getPhone()+";";
                } else {
                    saoContents[stars - 1] += feedback.getContent() + "," + feedback.getStars() + " sao ,sdt "+feedback.getPhone()+";";
                }
            }
        }
        for (String saoContent : saoContents) {
            if (saoContent != null) {
                contents.append(saoContent);
            }
        }
        System.out.println(contents.toString());
        return contents.toString();
    }

    private HttpEntity<String> createHttpEntity(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + openaiApiKey);
        return new HttpEntity<>(payload, headers);
    }


}
