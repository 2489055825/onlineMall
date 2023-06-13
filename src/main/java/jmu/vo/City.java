package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class City {
    private int cityID;
    private int provinceID;
    private String cityName;

    private List<County> countyList;

    private Province province;



}
