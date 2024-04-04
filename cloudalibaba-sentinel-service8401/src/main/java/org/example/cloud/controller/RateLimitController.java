package org.example.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimitController {
    @GetMapping("/rateLimit/byUrl")
    //@SentinelResource()
    public String byUrl() {
        return "byUrl";
    }

    @GetMapping("/rateLimit/doActon/{p1}")
    @SentinelResource(value = "doActionSentinelResource",
            blockHandler = "doActionBlockHandler",
            fallback = "doActionFallback")
    public String doAction(@PathVariable("p1") Integer p1) {
        if (p1 == 0) {
            throw new RuntimeException("i == 0 error");
        }
        return "normal";
    }

    public String doActionBlockHandler(@PathVariable("p1") Integer p1, BlockException e) {
        log.error("配置了自定义限流: {}", e.getMessage());
        return "doActionBlockHandler";
    }

    public String doActionFallback(@PathVariable("p1") Integer p1, Throwable e) {
        log.error("程序逻辑异常: {}", e.getMessage());
        return "doActionFallback";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "testHotKeyBlockHandler")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "testHotKey";
    }

    public String testHotKeyBlockHandler(@RequestParam(value = "p1", required = false) String p1,
                                         @RequestParam(value = "p2", required = false) String p2,
                                         BlockException blockException) {
        blockException.printStackTrace();
        return "testHotKeyBlockHandler";
    }

}
