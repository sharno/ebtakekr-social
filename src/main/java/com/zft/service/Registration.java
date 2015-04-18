package com.zft.service;

import com.zft.pojo.Response;
import com.zft.pojo.ServiceType;
import com.zft.pojo.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainfall on 4/17/2015.
 */
@RestController
@RequestMapping("/")
public class Registration {

    public static Map<String, User> sessionManager = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public String getScreen(@RequestParam(value = "shortCode", required = false) String shortCode,
                            @RequestParam(value = "from", required = false) String from,
                            @RequestParam(value = "text", required = false) String text,
                            @RequestParam(value = "sessionOperation", required = false) String sessionOperation,
                            @RequestParam(value = "screenId", required = false) String screenId,
                            @RequestParam(value = "sessionId", required = false) String sessionId,
                            @RequestParam(value = "event", required = false) String event) {

        Response response = new Response(sessionOperation, screenId);

        System.out.println("from:" + from);

        if (screenId != null) {

            switch (screenId) {
                case "":
                    for (User user : ServiceType.users) {
                        if (user.getHashedPhone().equals(from)) {
                            //Created user with no facebook account yet
                            if (user.getFbToken() == null) {
                                response.setText("Please, register your facebook account with your id on the website. Your id is " + user.getUserSignature());
                                response.setSessionOperation(Response.SESSION_OPERATION_END);
                                return response.toString();
                            }

                            sessionManager.put(sessionId, user);
                            response.setText("Social Afaq. Send 1 to get next notification.");
                            response.setSessionOperation(Response.SESSION_OPERATION_CONTINUE);
                            response.setScreenId("1");
                            return response.toString();
                        }

                    }
                    return createNewUser(from, response).toString();
                case "6":
                    response.setSessionOperation(Response.SESSION_OPERATION_END);
                    return response.toString();
                default:
                    System.out.println("screenid:" + screenId);

                    response.setText(getNotifications(sessionId, screenId));
                    response.setSessionOperation(Response.SESSION_OPERATION_CONTINUE);
                    response.setScreenId(String.valueOf(Integer.parseInt(screenId) + 1));

                    System.out.println("text:" + response.getText());

                    return response.toString();
            }
        }
        return response.toString();
    }

    public Response createNewUser(String from, Response response) {
        User user = new User(from, "a" + ServiceType.currUserSignature++);
        ServiceType.users.add(user);

        response.setText("Please, register your facebook account with your id on the website. Your id is " + user.getUserSignature());
        response.setSessionOperation(Response.SESSION_OPERATION_END);
        return response;
    }

    public String getNotifications(String sessionId, String screenId) {
        User user = sessionManager.get(sessionId);
        if (user != null) {
            //Current user to get notifications
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpRequest = new HttpGet("https://graph.facebook.com/v2.3/me/notifications?access_token=" + user.getFbToken());
            HttpResponse httpResponse = null;
            BufferedReader responseContent = null;
            StringBuffer result = null;
            try {
                httpResponse = httpClient.execute(httpRequest);
                responseContent = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                result = new StringBuffer();
                String line = "";

                while ((line = responseContent.readLine()) != null)
                    result.append(line);

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = (JSONObject) JSONValue.parse(String.valueOf(result));
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            String title = "";

            if (jsonArray.get(Integer.parseInt(screenId) - 1) != null) {
                Object object = jsonArray.get(Integer.parseInt(screenId) - 1);
                title += "-" + ((JSONObject) object).get("title").toString();
            }
            return String.valueOf(title);
        }

        return null;
    }
}
