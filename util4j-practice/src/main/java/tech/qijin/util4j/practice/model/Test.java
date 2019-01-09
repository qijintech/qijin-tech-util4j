package tech.qijin.util4j.practice.model;

import java.math.BigDecimal;
import java.util.Date;

public class Test {
    private Integer id;

    private Integer col1;

    private String col2;

    private BigDecimal col3;

    private Byte env;

    private Byte valid;

    private Date ctime;

    private Date utime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCol1() {
        return col1;
    }

    public void setCol1(Integer col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2 == null ? null : col2.trim();
    }

    public BigDecimal getCol3() {
        return col3;
    }

    public void setCol3(BigDecimal col3) {
        this.col3 = col3;
    }

    public Byte getEnv() {
        return env;
    }

    public void setEnv(Byte env) {
        this.env = env;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}