package com.example.todolist;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    TodoRespository todoRespository;
    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listPotlucks(Model model){
        model.addAttribute("todos", todoRespository.findAll());
        return "index";
    }
    @GetMapping("/add")
    public String newPotluck(Model model){
        model.addAttribute("todo", new Todo());
        return "add";
    }
    @PostMapping("/process")
    public String processTodo(@ModelAttribute Todo todo,  @RequestParam("file") MultipartFile file){
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            todo.setImage(uploadResult.get("url").toString());
            todoRespository.save(todo);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/add";
    }
}
