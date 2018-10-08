//import strings.JoinKt;
import strings.StringFunctions;

import java.util.ArrayList;
import java.util.List;

public class TestUX {
    static void test() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
//        Ch01Kt.p(JoinKt.joinToString3(list, ",", "-", "-"));
        Ch01Kt.p(StringFunctions.joinToString3(list, ",", "-", "-"));
    }

    public static void main(String[] args) {
        test();
    }
}
