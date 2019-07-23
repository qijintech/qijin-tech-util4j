package tech.qijin.util4j.trace.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trace implements Serializable {

    private String traceId;

    private EnvEnum env;


}
