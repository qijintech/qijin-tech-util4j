package tech.qijin.util4j.trace.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Trace implements Serializable {

    private String traceId;

    private EnvEnum env;


}
