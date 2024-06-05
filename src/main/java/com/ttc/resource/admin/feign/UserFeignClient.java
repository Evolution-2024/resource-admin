package com.ttc.resource.admin.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "https://security-production-d232.up.railway.app/api/v1/users")
public interface UserFeignClient {
    @GetMapping("/auth/{userId}")
    ResponseEntity<?> getUserById(@PathVariable("userId") Long userId);
}
