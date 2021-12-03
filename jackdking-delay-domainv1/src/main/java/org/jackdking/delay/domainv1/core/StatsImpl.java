package org.jackdking.delay.domainv1.core;

import com.google.common.collect.Sets;
import com.sun.org.glassfish.external.statistics.Statistic;
import com.sun.org.glassfish.external.statistics.Stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class StatsImpl  extends StatisticImpl implements Stats, Resettable {
    //use a Set instead of a Map - to conserve Space
    private Set<StatisticImpl> set;

    public StatsImpl() {
        this(new CopyOnWriteArraySet<StatisticImpl>());
    }

    public StatsImpl(Set<StatisticImpl> set) {
        super("stats", "many", "Used only as container, not Statistic");
        this.set = set;
    }

    public void reset() {
        Statistic[] stats = getStatistics();
        int size = stats.length;
        for (int i = 0; i < size; i++) {
            Statistic stat = stats[i];
            if (stat instanceof Resettable) {
                Resettable r = (Resettable) stat;
                r.reset();
            }
        }
    }

    public Statistic getStatistic(String name) {
        return Optional.ofNullable(this.set).orElse(Sets.newCopyOnWriteArraySet())
                .stream()
                .filter(stat -> stat.getName() != null
                        && stat.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String[] getStatisticNames() {
        List<String> names = new ArrayList<String>();
        for (StatisticImpl stat : this.set) {
            names.add(stat.getName());
        }
        String[] answer = new String[names.size()];
        names.toArray(answer);
        return answer;
    }

    public Statistic[] getStatistics() {
        Statistic[] answer = new Statistic[this.set.size()];
        set.toArray(answer);
        return answer;
    }

    protected void addStatistic(String name, StatisticImpl statistic) {
        this.set.add(statistic);
    }
}
