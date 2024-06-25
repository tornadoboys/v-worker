//package com.itheima.test.controller;
//
//import com.itheima.test.service.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/redis")
//public class RedisController {
//
//    @Autowired
//    private RedisService redisService;
//
//    @PostMapping("/add-to-list")
//    public String addToList(@RequestParam String key, @RequestParam String value) {
//        redisService.addToList(key, value);
//        return "Value added to list";
//    }
//
//    @GetMapping("/get-list")
//    public List<String> getList(@RequestParam String key) {
//        return redisService.getList(key);
//    }
//}

