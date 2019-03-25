package cn.mrxiexie.gizwits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GizwitsTestApplication {

//    @Autowired
//    GizwitsOpenApi gizwitsOpenApi;

    public static void main(String[] args) {
        SpringApplication.run(GizwitsTestApplication.class, args);
    }
//
//    @PostConstruct
//    public void hello() {
//        List<DeviceInfo> boundDevices = gizwitsOpenApi.getBoundDevices();
//        GizwitsUser user = gizwitsOpenApi.createUser("hello", "hello");
//    }

}
