package org.saas.web.controller;


import java.io.IOException;
import java.util.Date;

import org.saas.service.system.helper.WebSocketHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.Session;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @RequestMapping(value = "/index")
    public String index() throws IOException {
        WebSocketHelper helper = new WebSocketHelper();
        helper.sendMessage("ddd");
        return "index";
    }

    @RequestMapping(value = "/home")
    public String home(){
        return "home";
    }


    @RequestMapping(value = "/send")
    public void send() throws IOException {
        WebSocketHelper helper = new WebSocketHelper();
        helper.sendMessageToUsers(new Date().getTime()+"");
    }
}
