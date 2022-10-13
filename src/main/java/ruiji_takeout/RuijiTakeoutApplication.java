package ruiji_takeout;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ruiji_takeout.mapper")
public class RuijiTakeoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijiTakeoutApplication.class, args);
    }

}
