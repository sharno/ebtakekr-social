package com.zft.service;

import com.zft.pojo.Response;
import com.zft.pojo.ServiceType;
import com.zft.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;

/**
 * Created by Rainfall on 4/17/2015.
 */
@RestController
@RequestMapping("/")
public class Registration {

    @RequestMapping(method = RequestMethod.GET)
    public String getScreen(@RequestParam(value = "shortCode", required = false) String shortCode,
                           @RequestParam(value = "from", required = false) String from,
                           @RequestParam(value = "text", required = false) String text,
                           @RequestParam(value = "sessionOperation", required = false) String sessionOperation,
                           @RequestParam(value = "screenId", required = false) String screenId,
                           @RequestParam(value = "sessionId", required = false) String sessionId,
                           @RequestParam(value = "event", required = false) String event){

        Response response = new Response(sessionOperation, screenId);

        for (User user : ServiceType.users){
            if (user.getHashedPhone().equals(from))
            {
                //Created user with no facebook account yet
                if(user.getFbToken() == null){
                    response.setText("Please, register your facebook account with your id on the website. Your id is " + user.getUserSignature());
                    response.setSessionOperation(Response.SESSION_OPERATION_END);
                    return response.toString();
                }

                //Current user to get notifications
                //call fb.getNotifications(user.getFbToken())
                return null;
            }
        }

        //New User
        User user = new User();
        user.setHashedPhone(from);
        user.setUserSignature("a" + ServiceType.currUserSignature++);

        response.setText("Please, register your facebook account with your id on the website. Your id is " + user.getUserSignature());
        response.setSessionOperation(Response.SESSION_OPERATION_END);
        return response.toString();
    }
}
