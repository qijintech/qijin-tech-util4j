package tech.qijin.util4j.notify;

import java.util.List;

import com.google.common.collect.Lists;

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
