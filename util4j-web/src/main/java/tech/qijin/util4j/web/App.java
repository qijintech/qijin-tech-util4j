package tech.qijin.util4j.web;

import org.springframework.core.annotation.AnnotatedElementUtils;
import tech.qijin.util4j.web.validation.Hehe;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        Hehe h = new Hehe();
        Annotation[] annotations = h.getClass().getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            Class c = annotation.annotationType();
            Annotation[] annotations2 = c.getAnnotations();
            System.out.println(c);
        }
        Set a = AnnotatedElementUtils.findAllMergedAnnotations(Hehe.class, TestA.class);

        System.out.println("Hello World!");
    }
}
