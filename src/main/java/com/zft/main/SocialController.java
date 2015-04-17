package com.zft.main;

import com.zft.pojo.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sharno on 4/17/15.
 */
@RestController
@RequestMapping("/")
public class SocialController {

    @RequestMapping(method = RequestMethod.GET)
    public String getScreen(
            @RequestParam(value = "shortCode", required = false) String shortCode,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "sessionOperation", required = false) String sessionOperation,
            @RequestParam(value = "screenId", required = false) String screenId,
            @RequestParam(value = "sessionId", required = false) String sessionId,
            @RequestParam(value = "event", required = false) String event) {

        Response response = new Response(sessionOperation, screenId);

        if (screenId != null && screenId.equals("0")) {

            switch (sessionOperation) {
                case Response.SESSION_OPERATION_NEW:
                    response.createTypeScreen();
                    response.setScreenId("1");
                    response.setSessionOperation(Response.SESSION_OPERATION_CONTINUE);
                    break;
                case Response.SESSION_OPERATION_CONTINUE:
                    response.setSessionOperation(Response.SESSION_OPERATION_END);
                    break;
                case Response.SESSION_OPERATION_END:
                    response.setSessionOperation(Response.SESSION_OPERATION_END);
                    break;
                default:
                    response.setText("error session is invalid : " + sessionOperation);
                    response.setSessionOperation(Response.SESSION_OPERATION_END);
                    break;
            }

            return response.toString();
        } else {
            if (screenId != null && screenId.equals("1")) {
                if (text != null) {
                    try {
                        int screenIDint = Integer.parseInt(screenId);
                        int type = Integer.parseInt(text);

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        response.setText("error invalid input type " + text);
                        return response.toString();
                    }

                }
            }
        }
        response.setText("error paramter error");
        response.setSessionOperation(Response.SESSION_OPERATION_END);
        return response.toString();
    }
}
