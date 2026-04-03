package com.example.studentmanagement.controller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Map<String, Object> errors = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE, ErrorAttributeOptions.Include.BINDING_ERRORS));

        int status = (int) errors.getOrDefault("status", 500);
        String message = (String) errors.getOrDefault("error", "Unexpected Error");
        String path = (String) errors.getOrDefault("path", request.getRequestURI());

        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("path", path);

        return "error";
    }
}
