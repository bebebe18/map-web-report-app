package id.co.map.mapwebreportapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ErrorController.BASE_URL)
class ErrorController {

    public static final String BASE_URL = "error";

    @GetMapping("/403")
    public String showForbidden(){
        return "error/403";
    }
}
