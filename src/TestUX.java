//import strings.JoinKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
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
//        test();
        //Java1.8
        Ch08Kt.processTheAnswer(number -> number + 1);

        //<Java1.8
        Ch08Kt.processTheAnswer(
                new Function1<Integer, Integer>() {
                    @Override
                    public Integer invoke(Integer number) {
                        System.out.println(number);
                        return number + 1;
                    }
                }
        );

        List<String> strings = new ArrayList<>();
        strings.add("42");
        CollectionsKt.forEach(strings, s -> {       //可以再Java代码中使用Kotlin标准库中的函数
            System.out.println(s);
            return Unit.INSTANCE;                   //必须要显示地返回一个Unit类型的值
        });
    }

}
