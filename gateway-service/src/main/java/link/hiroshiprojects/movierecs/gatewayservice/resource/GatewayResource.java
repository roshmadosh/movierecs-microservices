package link.hiroshiprojects.movierecs.gatewayservice.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gateway")
public class GatewayResource {

    @GetMapping
    public String smokeTest() {
        return "SMoke test successfuul!";
    }

}
