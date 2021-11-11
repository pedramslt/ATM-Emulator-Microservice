package atmemulator.cloudgateway.controller;

import atmemulator.cloudgateway.controller.service.FallBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackServiceController {

    @Autowired
    private FallBackService fallBackService;

    @GetMapping("/atmServiceFallBack")
    public ResponseEntity<String> atmServiceFallBack() {
        return new ResponseEntity<>(fallBackService.atmServiceFallBack(), HttpStatus.OK);
    }
}
