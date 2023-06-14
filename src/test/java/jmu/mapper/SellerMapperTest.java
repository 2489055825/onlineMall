package jmu.mapper;

import jmu.vo.Seller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerMapperTest {


    @Autowired
    private SellerMapper sellerMapper;

    @Test
    public void queryBySellerID() {
        Seller seller = sellerMapper.queryBySellerID(1);
        System.out.println(seller.toString());
    }

    @Test
    public void updateBySellerID() {
    }

    @Test
    public void queryBySellerIDfrom() {
    }
}