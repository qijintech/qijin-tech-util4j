package tech.qijin.util4j.notify;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        List<Integer> test = Lists.newArrayList();
        System.out.println(test.stream().findFirst());
    }
}
