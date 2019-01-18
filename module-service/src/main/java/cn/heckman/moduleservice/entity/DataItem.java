package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by heckman on 2018/6/30.
 */
@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_data_item")
public class DataItem extends IdEntity {

    /**
     * `item_name` varchar(50) DEFAULT NULL,
     * `item_value` varchar(50) DEFAULT NULL,
     * `dictionary_id` varchar(32) DEFAULT NULL,
     */

    private String itemCode;
    private String itemValue;
    private DataDictionary dataDictionary;

    @Column(name = "item_name")
    public String getItemCode() {
        return itemCode;
    }

    @Column(name = "item_value")
    public String getItemValue() {
        return itemValue;
    }

    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public void setDataDictionary(DataDictionary dataDictionary) {
        this.dataDictionary = dataDictionary;
    }
}
