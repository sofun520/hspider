package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by heckman on 2018/8/4.
 */
@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_index_column")
public class SpIndexColumn extends IdEntity {

    /**
     * `column_name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
     * `column_type` int(1) DEFAULT NULL COMMENT '栏目类型',
     * `column_image` varchar(200) DEFAULT NULL COMMENT '栏目图片',
     * `column_keyword` varchar(50) DEFAULT NULL COMMENT '栏目指向商品关键字',
     */

    private String columnName;
    private int columnType;
    private String columnImage;
    private String columnKeyword;
    private Tenant tenant;

    @Column(name = "column_name")
    public String getColumnName() {
        return columnName;
    }

    @Column(name = "column_type")
    public int getColumnType() {
        return columnType;
    }

    @Column(name = "column_image")
    public String getColumnImage() {
        return columnImage;
    }

    @Column(name = "column_keyword")
    public String getColumnKeyword() {
        return columnKeyword;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Tenant getTenant() {
        return tenant;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public void setColumnImage(String columnImage) {
        this.columnImage = columnImage;
    }

    public void setColumnKeyword(String columnKeyword) {
        this.columnKeyword = columnKeyword;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
