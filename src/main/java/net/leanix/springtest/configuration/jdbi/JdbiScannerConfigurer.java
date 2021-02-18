package net.leanix.springtest.configuration.jdbi;

import static org.springframework.util.Assert.notNull;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

public class JdbiScannerConfigurer
    implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;

    private ApplicationContext applicationContext;

    private String beanName;

    private BeanNameGenerator nameGenerator;

    /**
     * This property lets you set the base package for your mapper interface files.
     * <p>
     * You can set more than one package by using a semicolon or comma as a separator.
     * <p>
     * Mappers will be searched for recursively starting in the specified package(s).
     *
     * @param basePackage
     *          base package name
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }


    /**
     * Specifies a flag that whether execute a property placeholder processing or not.
     * <p>
     * The default is {@literal false}. This means that a property placeholder processing does not execute.
     *
     * @since 1.1.1
     *
     * @param processPropertyPlaceHolders
     *          a flag that whether execute a property placeholder processing or not
     */
//    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
//        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }


    /**
     * Sets beanNameGenerator to be used while running the scanner.
     *
     * @param nameGenerator
     *          the beanNameGenerator to set
     * @since 1.2.0
     */
    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // left intentionally blank
    }

    /**
     * {@inheritDoc}
     *
     * @since 1.0.2
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
//        if (this.processPropertyPlaceHolders) {
//            processPropertyPlaceHolders();
//        }

        var scanner = new JdbiMapperScanner(registry, JdbiDao.class, JdbiDaoFactoryBean.class);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setBeanNameGenerator(this.nameGenerator);
        scanner.registerFilters();
        scanner.scan(
            StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    /*
     * BeanDefinitionRegistries are called early in application startup, before BeanFactoryPostProcessors. This means that
     * PropertyResourceConfigurers will not have been loaded and any property substitution of this class' properties will
     * fail. To avoid this, find any PropertyResourceConfigurers defined in the context and run them on this class' bean
     * definition. Then update the values.
     */
//    private void processPropertyPlaceHolders() {
//        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class,
//            false, false);
//
//        if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
//            BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
//                .getBeanDefinition(beanName);
//
//            // PropertyResourceConfigurer does not expose any methods to explicitly perform
//            // property placeholder substitution. Instead, create a BeanFactory that just
//            // contains this mapper scanner and post process the factory.
//            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
//            factory.registerBeanDefinition(beanName, mapperScannerBean);
//
//            for (PropertyResourceConfigurer prc : prcs.values()) {
//                prc.postProcessBeanFactory(factory);
//            }
//
//            PropertyValues values = mapperScannerBean.getPropertyValues();
//
//            this.basePackage = getPropertyValue("basePackage", values);
//            this.sqlSessionFactoryBeanName = getPropertyValue("sqlSessionFactoryBeanName", values);
//            this.sqlSessionTemplateBeanName = getPropertyValue("sqlSessionTemplateBeanName", values);
//            this.lazyInitialization = getPropertyValue("lazyInitialization", values);
//            this.defaultScope = getPropertyValue("defaultScope", values);
//        }
//        this.basePackage = Optional.ofNullable(this.basePackage).map(getEnvironment()::resolvePlaceholders).orElse(null);
//        this.sqlSessionFactoryBeanName = Optional.ofNullable(this.sqlSessionFactoryBeanName)
//            .map(getEnvironment()::resolvePlaceholders).orElse(null);
//        this.sqlSessionTemplateBeanName = Optional.ofNullable(this.sqlSessionTemplateBeanName)
//            .map(getEnvironment()::resolvePlaceholders).orElse(null);
//        this.lazyInitialization = Optional.ofNullable(this.lazyInitialization).map(getEnvironment()::resolvePlaceholders)
//            .orElse(null);
//        this.defaultScope = Optional.ofNullable(this.defaultScope).map(getEnvironment()::resolvePlaceholders).orElse(null);
//    }

    private Environment getEnvironment() {
        return this.applicationContext.getEnvironment();
    }

    private String getPropertyValue(String propertyName, PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);

        if (property == null) {
            return null;
        }

        Object value = property.getValue();

        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            return null;
        }
    }

}

