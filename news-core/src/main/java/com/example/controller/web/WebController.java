package com.example.controller.web;

import com.example.dto.UserDTO;
import com.example.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "homeControllerOfWeb")
public class WebController {

    @Autowired
    private INewService newService;

    @RequestMapping(value = "/trang-chu", method = RequestMethod.GET)
    public ModelAndView homePage(){
        ModelAndView mav = new ModelAndView("web/home");
        mav.addObject("news", newService.getAll());
        return mav;
    }

}
