package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by heckman on 2018/7/28.
 */
@Entity
@Where(clause = "deleted=0")
@Table(schema = "demo", name = "tb_product")
public class Product extends IdEntity {

    /**
     * `product_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
     * `product_preferential_price` decimal(10,2) DEFAULT NULL COMMENT '优惠价',
     * `product_name` varchar(50) DEFAULT NULL COMMENT '产品名称',
     * `product_short_instro` varchar(255) DEFAULT NULL COMMENT '产品简介',
     * `product_introduce` text COMMENT '产品详情',
     * `product_img` varchar(100) DEFAULT NULL COMMENT '产品封面',
     * `product_album` text COMMENT '产品相册',
     * `tenant_id` varchar(32) DEFAULT NULL COMMENT '产品所属租户id',
     */

    private String productName;
    private String productImg;
    private BigDecimal productPrice;
    private BigDecimal productPreferentialPrice;
    private String productShortInstro;
    private String productIntroduce;
    private String productAlbum;
    private Tenant tenant;

    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    @Column(name = "product_img")
    public String getProductImg() {
        return productImg;
    }

    @Column(name = "product_price")
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    @Column(name = "product_preferential_price")
    public BigDecimal getProductPreferentialPrice() {
        return productPreferentialPrice;
    }

    @Column(name = "product_short_instro")
    public String getProductShortInstro() {
        return productShortInstro;
    }

    @Column(name = "product_introduce")
    public String getProductIntroduce() {
        return productIntroduce;
    }

    @Column(name = "product_album")
    public String getProductAlbum() {
        return productAlbum;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public Tenant getTenant() {
        return tenant;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductPreferentialPrice(BigDecimal productPreferentialPrice) {
        this.productPreferentialPrice = productPreferentialPrice;
    }

    public void setProductShortInstro(String productShortInstro) {
        this.productShortInstro = productShortInstro;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    public void setProductAlbum(String productAlbum) {
        this.productAlbum = productAlbum;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
