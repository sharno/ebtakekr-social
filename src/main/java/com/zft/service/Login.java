package com.zft.service;

import com.zft.pojo.ServiceType;
import com.zft.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rainfall on 4/18/2015.
 */

@RestController
@RequestMapping("/web")
public class Login {

    @RequestMapping(method = RequestMethod.GET, value = "/signature")
    public String loginSignature(@RequestParam(value = "user_signature") String userSignature){
        for (User user : ServiceType.users){
            if (user.getUserSignature().equals(userSignature))
                return "success";
        }
        return "error";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/facebook")
    public String loginFacebook(@RequestParam(value = "user_signature") String userSignature,
                                @RequestParam(value = "fb") String fbToken){
        for (User user : ServiceType.users){
            if (user.getUserSignature().equals(userSignature)){
                user.setFbToken(fbToken);
                return "success";
            }
        }
        return "error";
    }


}
