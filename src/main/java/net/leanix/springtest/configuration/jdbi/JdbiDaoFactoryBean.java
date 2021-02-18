package net.leanix.springtest.configuration.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbiDaoFactoryBean<T> implements FactoryBean<T> {

    @Autowired
    private Jdbi jdbi;

    private final Class<T> daoType;

    public JdbiDaoFactoryBean(Class<T> daoType) {
        this.daoType = daoType;
    }

    @Override
    public T getObject() throws Exception {
        return jdbi.onDemand(daoType);
    }

    @Override
    public Class<T> getObjectType() {
        return daoType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
