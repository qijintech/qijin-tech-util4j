//package tech.qijin.util4j.practice.spi.favorites;
//
//import com.google.common.collect.Maps;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tech.qijin.satellites.favorites.service.spi.FavoritesProvider;
//import tech.qijin.util4j.practice.service.TestService;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author michealyang
// * @date 2019/1/10
// * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
// **/
//@Service
//public class FavoritesProviderImpl implements FavoritesProvider {
//    @Autowired
//    private TestService testService;
//
//    @Override
//    public Map<Long, Object> mapFavoritesItemByIds(List<Long> itemIds) {
//        Map<Long, Object> res = Maps.newHashMap();
//        res.put(1L, "test1");
//        res.put(2L, "test2");
//        return res;
//    }
//}
