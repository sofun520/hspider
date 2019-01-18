package cn.heckman.moduleservice.entity;

import cn.heckman.moduleservice.base.IdEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause = "deleted=0")
@Table(schema = "wsa", name = "tb_tsa_oil")
public class TsaOil extends IdEntity {
    /**
     * `current_mileage` float(10,2) DEFAULT NULL COMMENT '当前里程',
     * `oil_fee` float(10,2) DEFAULT NULL COMMENT '加油金额',
     * `oil_quantity` float(10,2) DEFAULT NULL COMMENT '加油油量（单位L）',
     * `wx_user_id` varchar(32) DEFAULT '' COMMENT '微信用户id',
     */

    private Float currentMileage;
    private Float oilFee;
    private Float oilQuantity;
    private WxUser wxUser;
    private Float fuelPrice;
    private Float remainingFuel;
    private DataItem fuelType;

    @ManyToOne
    @JoinColumn(name = "fuel_type")
    @NotFound(action = NotFoundAction.IGNORE)
    public DataItem getFuelType() {
        return fuelType;
    }

    public void setFuelType(DataItem fuelType) {
        this.fuelType = fuelType;
    }

    @Column(name="remaining_fuel")
    public Float getRemainingFuel() {
        return remainingFuel;
    }

    public void setRemainingFuel(Float remainingFuel) {
        this.remainingFuel = remainingFuel;
    }

    @Column(name="fuel_price")
    public Float getFuelPrice() {
        return fuelPrice;
    }

    public void setFuelPrice(Float fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    @Column(name = "current_mileage")
    public Float getCurrentMileage() {
        return currentMileage;
    }

    @Column(name = "oil_fee")
    public Float getOilFee() {
        return oilFee;
    }

    @Column(name = "oil_quantity")
    public Float getOilQuantity() {
        return oilQuantity;
    }

    @ManyToOne
    @JoinColumn(name = "wx_user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    public WxUser getWxUser() {
        return wxUser;
    }

    public void setCurrentMileage(Float currentMileage) {
        this.currentMileage = currentMileage;
    }

    public void setOilFee(Float oilFee) {
        this.oilFee = oilFee;
    }

    public void setOilQuantity(Float oilQuantity) {
        this.oilQuantity = oilQuantity;
    }

    public void setWxUser(WxUser wxUser) {
        this.wxUser = wxUser;
    }
}
