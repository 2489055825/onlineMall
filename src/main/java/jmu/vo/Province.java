package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Province {
    private int provinceID;
    private String provinceName;

    //多收货 多区 多市
    private List<Receiver> receivers;
    private List<City> cities;
    private List<County> counties;


}
