package org.example.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EmpowerController {
    @GetMapping("/empower")
    public String requestSentinel() {
        log.info("授权规则requestSentinel");
        return "授权规则requestSentinel";
    }
}
