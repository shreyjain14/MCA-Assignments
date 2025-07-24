package me.shreyjain.joobseekerservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/add")
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        return a + b;
    }

    @GetMapping("/multiply")
    public Integer multiply(@RequestParam Integer a, @RequestParam Integer b) {
        return a * b;
    }

    @GetMapping("/divide")
    public Integer divide(@RequestParam Integer a, @RequestParam Integer b) {
        if (b == 0) {
            throw new IllegalArgumentException("b cannot be 0");
        } else {
            return a / b;
        }
    }

}
