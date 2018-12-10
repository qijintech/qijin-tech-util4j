package tech.qijin.util4j.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.qijin.util4j.practice.dao.CommodityDao;
import tech.qijin.util4j.practice.mapper.CommodityMapper;
import tech.qijin.util4j.practice.model.Commodity;

/**
 * @author michealyang
 * @date 2018/12/9
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Service
public class CommodityService {
    @Autowired
    private CommodityDao commodityDao;

    public Commodity insert(Commodity commodity) {
        commodityDao.insertSelective(commodity);
        return commodity;
    }
}
