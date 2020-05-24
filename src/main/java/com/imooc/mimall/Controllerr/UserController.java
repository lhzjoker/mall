package com.imooc.mimall.Controllerr;

import com.imooc.mimall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 0:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @PostMapping("/register")
    void register(@RequestBody User user) {
        log.info("username={}",user.getUsername());
    }
}
