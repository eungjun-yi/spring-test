package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    private HelloService service;

    // Value does not work for static field
    @Value("${test}")
    private static String test;

    @RequestMapping("/")
    public String test() {
        return test;
    }

    @RequestMapping("/test2")
    public String test2(@RequestParam String target) {
        return service.admin(target);
    }

    @RequestMapping("/case/{x}")
    @ResponseBody
    public String caseTest(@PathVariable String x) {
        return x;
    }

    @RequestMapping(value = "/case/{x}", params = {"a"})
    @ResponseBody
    public String caseTest2(@PathVariable String x, @RequestParam String a) {
        return a;
    }
}
