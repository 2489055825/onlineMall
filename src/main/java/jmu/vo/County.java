package jmu.vo;

import lombok.Data;

import java.util.List;

@Data
public class County {
    private int countyID;
    private String countyName;
    private int cityID;


    private City city;
    private Province province;
    private List<Receiver> receiverList;

}
