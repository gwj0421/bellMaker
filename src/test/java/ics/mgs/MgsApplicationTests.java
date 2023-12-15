package ics.mgs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MgsApplicationTests {

    @Test
    void contextLoads() {
        List<String> test = new ArrayList<>();
        test.add("a");
        test.add("b");
        test.add(null);
        test.add("c");
        test.add("d");

        System.out.println(String.join("|", test));
    }

}
