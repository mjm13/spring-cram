package com.meijm.stomp.controller;

import cn.hutool.json.JSONUtil;
import com.meijm.stomp.vo.Message;
import com.meijm.stomp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/chat")
@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/toChat")
    public ModelAndView index(Model model, HttpServletRequest request) {
        Set<User> users = (Set<User>) request.getServletContext().getAttribute("users");
        User user = (User) request.getSession().getAttribute("user");
        Set<User> friends = users.stream().filter(user1 -> !user1.equals(user)).collect(Collectors.toSet());
        model.addAttribute("friends", friends);
        model.addAttribute("user", user);
        return new ModelAndView("chat");
    }

    @MessageMapping("/sendMsg")
    public void sendMsg(Principal principal, String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Message msg = JSONUtil.toBean(message, Message.class);
        try {
            msg.setSendTime(sdf.format(new Date()));
        } catch (Exception e) {
        }
        if (!"TO_ALL".equals(msg.getReceiver())) {
            template.convertAndSendToUser(msg.getReceiver(), "/chat", JSONUtil.toJsonStr(msg));
        } else {
            template.convertAndSend("/topic/notice", JSONUtil.toJsonStr(msg));
        }
    }
}
