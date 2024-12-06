package com.example.imdb.resource;

import com.example.imdb.aspect.RequestAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {
    private final RequestAspect requestAspect;

    @GetMapping("/count-request")
    public int getRequestCount() {
        return requestAspect.getRequestCount();
    }
}
