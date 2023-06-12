package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class County {
    private int cityID;
    private int countyID;
    private String countyName;

    private City city;
    private Province province;
    private List<Receiver> receivers;

}
