package cn.mrxiexie.gizwits;

import cn.mrxiexie.gizwits.entity.openapi.DeviceInfo;
import cn.mrxiexie.gizwits.entity.openapi.GizwitsUser;
import cn.mrxiexie.gizwits.openapi.GizwitsOpenApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@SpringBootApplication
public class GizwitsTestApplication {

    @Autowired
    GizwitsOpenApi gizwitsOpenApi;

    public static void main(String[] args) {
        SpringApplication.run(GizwitsTestApplication.class, args);
    }

    @PostConstruct
    public void hello() {
        List<DeviceInfo> boundDevices = gizwitsOpenApi.getBoundDevices();
        GizwitsUser user = gizwitsOpenApi.createUser("hello", "hello");
    }

}
