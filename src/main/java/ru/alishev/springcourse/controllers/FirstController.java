package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public  String helloPage(HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        System.out.println(name + " " + surname);
        return "first/hello";
    }
    @GetMapping("/goodbye")
    public String goodbyePage(@RequestParam(value = "name",required = false) String name,
                              @RequestParam(value = "surname",required = false) String surname,
                              Model model){
        model.addAttribute("message", "Hello "+ name+ " " + surname);
        return "first/goodbye";
    }

    @GetMapping("/calculator")
    public String calculator(@RequestParam("a") int a, @RequestParam("b") int b,
                             @RequestParam("action") String action, Model model) {

        double result = switch (action) {
            case "mul" -> a * b;
            case "div" -> a / (double) b;
            case "sub" -> a - b;
            case "add" -> a + b;
            default -> 0;
        };

        model.addAttribute("result", result);

        return "first/calculator";
    }
}
