package com.tacoloco.order.Repository;

import com.tacoloco.order.Entity.PriceChart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceChartRepository extends CrudRepository<PriceChart,String> {
}
