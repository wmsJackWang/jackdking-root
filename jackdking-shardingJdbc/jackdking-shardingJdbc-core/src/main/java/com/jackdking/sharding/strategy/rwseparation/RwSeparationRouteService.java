package com.jackdking.sharding.strategy.rwseparation;

import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.config.Constants;
import com.jackdking.sharding.enums.MethodOperationType;
import com.jackdking.sharding.enums.RWSeparationStrategyType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class RwSeparationRouteService {

    private List<RWSeparationStrategy> strategyList;

    public void decideWriteReadDs(MethodOperationType operationType, ShardingContext finalRWSeparationDBContext) throws Exception {
        Optional<RWSeparationStrategy> ops = Optional.ofNullable(strategyList).orElseGet(Collections::emptyList)
                .stream().filter(rwSeparationStrategy -> rwSeparationStrategy.support(finalRWSeparationDBContext.rwSeparationStrategy()))
                .findFirst();

        ops.ifPresent(rwSeparationStrategy -> {
            try {
                rwSeparationStrategy.execute(operationType, finalRWSeparationDBContext);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
