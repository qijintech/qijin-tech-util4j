package tech.qijin.util4j.practice;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @date 2019/4/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class App {
    static class InnerClass {
        Integer id;
        Date ctime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Date getCtime() {
            return ctime;
        }

        public void setCtime(Date ctime) {
            this.ctime = ctime;
        }

        public InnerClass(Integer id, Date ctime) {
            this.id = id;
            this.ctime = ctime;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("InnerClass{");
            sb.append("id=").append(id);
            sb.append(", ctime=").append(ctime);
            sb.append('}');
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        List<InnerClass> innerClasses = Lists.newArrayList();
        Date now = new Date();
        innerClasses.add(new InnerClass(4, now));
        innerClasses.add(new InnerClass(2, now));
        innerClasses.add(new InnerClass(1, now));
        innerClasses.add(new InnerClass(8, now));
        innerClasses.sort(new Comparator<InnerClass>() {
            @Override
            public int compare(InnerClass o1, InnerClass o2) {
                return Long.valueOf(o2.getCtime().getTime() - o1.getCtime().getTime()).intValue();
//                return (int) (o2.getId() - o1.getId());
            }
        });
        System.out.println(innerClasses);
    }
}
