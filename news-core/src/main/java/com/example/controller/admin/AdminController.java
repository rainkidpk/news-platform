package com.example.controller.admin;

import com.example.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "homeControllerOfAdmin")
public class AdminController {

    @RequestMapping(value = "/admin-page", method = RequestMethod.GET)
    public ModelAndView adminPage(){
        ModelAndView mav = new ModelAndView("admin/home");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("Wellcome Admin");
        mav.addObject("user", userDTO);
        return mav;
    }


}
