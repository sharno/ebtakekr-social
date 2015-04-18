package com.zft.service;

import com.zft.pojo.ServiceType;
import com.zft.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rainfall on 4/18/2015.
 */

@Controller
@RequestMapping("/web")
public class Login {

    @RequestMapping(method = RequestMethod.GET, value = "/signature")
    public String loginSignature(@RequestParam(value = "user_signature") String userSignature){
        for (User user : ServiceType.users){
            if (user.getUserSignature().equals(userSignature))
                return "facebook";
        }
        return "error";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/facebook")
    public String loginFacebook(@RequestParam(value = "user_signature") String userSignature,
                                @RequestParam(value = "fb") String fbToken){

        for (User user : ServiceType.users){
            System.out.println("param: " + userSignature);
            System.out.println("users: " + user.getUserSignature());
            if (user.getUserSignature().equals(userSignature)){
                user.setFbToken(fbToken);
                System.out.println(fbToken);
                return "success";
            }
        }
        System.out.println("Error");
        return "error";
    }


}
