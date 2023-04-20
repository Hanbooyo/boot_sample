package com.app.api.health.controller;

import com.app.api.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthCheckController {
    //개발환경인지 운영환경인지 확인하기 위해서
    // RequiredArgsConstructor 어노테이션을 이용하면 private final 필드들이 자동으로 생성자 생겨서 생성자 주입방식 가능
    private final Environment environment;

    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck(){
        HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder()
                .health("ok")
                .activeProfiles(Arrays.asList(environment.getActiveProfiles())) //현재 어떤 profile이 실행되는지 확인하는 메소드
                .build();
        return ResponseEntity.ok(healthCheckResponseDto);
                            //ok 로 보낼때는 http status가 200으로 반환될것이고
                            //ok().body(body); 형태로 body에 담겨서 보내짐
    }
}
