package com.zhuzhi.spring.cloud.alibaba.freeswitch.domain.mobileloc;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;

@Data
public class MobileLoc implements Serializable {

    private static final long serialVersionUID = -104317481308359943L;
    private Integer id;

    private String prefix;

    private String mobilenumber;

    private String mobileareaprovince;

    private String mobileareacity;

    private String mobiletype;

    private String postcode;

    private String areacode;

    private String citycode;

    public MobileLoc() {
    }

    public MobileLoc(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
}