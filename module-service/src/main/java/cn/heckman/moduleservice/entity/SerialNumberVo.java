package cn.heckman.moduleservice.entity;

import javax.persistence.*;

/**
 * Created by heckman on 2018/7/14.
 */
@Entity
@NamedStoredProcedureQuery(
        name = "getSerialno",
        procedureName = "test",
        resultClasses = {SerialNumberVo.class},
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "prefix", type = String.class),
                @StoredProcedureParameter(name = "xcode", mode = ParameterMode.OUT, type = String.class)
        }
)
public class SerialNumberVo {

    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String xcode;

    public String getXcode() {
        return xcode;
    }

    public void setXcode(String xcode) {
        this.xcode = xcode;
    }
}
