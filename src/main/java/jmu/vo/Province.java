package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class Province {
    private int provinceID;
    private String provinceName;

    private List<City> cityList;


}
