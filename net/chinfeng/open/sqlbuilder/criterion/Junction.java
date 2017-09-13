package net.chinfeng.open.sqlbuilder.criterion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Junction implements Criterion {
    private final List<Criterion> conditions;
    private final String operator;

    public Junction(String operator) {
        this.conditions = new ArrayList();
        this.operator = operator;
    }

    protected Junction(String operator, Criterion... criterion) {
        this.conditions = new ArrayList();
        this.operator = operator;
        Collections.addAll(this.conditions, criterion);
    }

    public String toSqlString() {
        if (this.conditions.size() == 0) {
            return "1=1";
        }
        StringBuilder buffer = new StringBuilder().append('(');
        Iterator itr = this.conditions.iterator();
        while (itr.hasNext()) {
            buffer.append(((Criterion) itr.next()).toSqlString());
            if (itr.hasNext()) {
                buffer.append(' ').append(this.operator).append(' ');
            }
        }
        return buffer.append(')').toString();
    }

    public Junction add(Criterion criterion) {
        this.conditions.add(criterion);
        return this;
    }
}
