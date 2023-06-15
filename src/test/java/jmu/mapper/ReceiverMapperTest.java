package jmu.mapper;

import jmu.vo.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiverMapperTest {
    @Autowired
    ReceiverMapper receiverMapper;

    @Test
    public void queryByBuyerID() {
        List<Receiver> receiverList = receiverMapper.queryByBuyerID(6);
        for(Receiver receiver:receiverList){
            System.out.println(receiver.toString());
            System.out.println(receiver.getAllReceiverInformation());
        }
    }

    @Test
    public void deleteByReceiverID() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void queryByReceiverIDfrom() {
    }
}