package org.chodavarapu.datamill.configuration;

import org.chodavarapu.datamill.reflection.Bean;
import org.chodavarapu.datamill.values.StringValue;
import rx.functions.*;

import java.util.function.Consumer;

/**
 * @author Ravi Chodavarapu (rchodava@gmail.com)
 */
public class ConfigurationBuilder<T> {
    private final Bean<T> bean;

    public ConfigurationBuilder(Bean<T> bean) {
        this.bean = bean;
    }

    public ConfigurationBuilder<T> configure(Consumer<T> configuration) {
        configuration.accept(bean.unwrap());
        return this;
    }

    public ConfigurationBuilder<T> ifSystemPropertyExists(String name, Consumer<ConfigurationBuilder<T>> configuration) {
        return ifSystemPropertyExists(name, configuration, null);
    }

    public ConfigurationBuilder<T> ifSystemPropertyExists(String name,
                                                          Consumer<ConfigurationBuilder<T>> configuration,
                                                          Consumer<ConfigurationBuilder<T>> elseConfiguration) {
        String value = System.getProperty(name);
        if (value != null) {
            if (configuration != null) {
                configuration.accept(this);
            }
        } else {
            if (elseConfiguration != null) {
                elseConfiguration.accept(this);
            }
        }

        return this;
    }

    private String getSystemProperty(String name, boolean required) {
        String value = System.getProperty(name);
        if (value == null && required) {
            throw new IllegalStateException("Expected " + name + " to be found in the system properties list!");
        }

        return value;
    }

    private <P> ConfigurationBuilder<T> fromSystemProperty(Consumer<T> propertyInvoker, String name,
                                                           Func1<String, P> derivation, boolean required) {
        String value = getSystemProperty(name, required);
        bean.set(propertyInvoker, derivation.call(value));

        return this;
    }

    public <P> ConfigurationBuilder<T> fromRequiredSystemProperty(Consumer<T> propertyInvoker, String name,
                                                                  Func1<String, P> derivation) {
        return fromSystemProperty(propertyInvoker, name, derivation, true);
    }

    private <P> ConfigurationBuilder<T> fromSystemProperties(Consumer<T> propertyInvoker,
                                                             String name1, String name2,
                                                             Func2<String, String, P> derivation,
                                                             boolean required) {
        String value1 = getSystemProperty(name1, required);
        String value2 = getSystemProperty(name2, required);
        bean.set(propertyInvoker, derivation.call(value1, value2));

        return this;
    }

    public <P> ConfigurationBuilder<T> fromRequiredSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2,
                                                                    Func2<String, String, P> derivation) {
        return fromSystemProperties(propertyInvoker, name1, name2, derivation, true);
    }

    private <P> ConfigurationBuilder<T> fromSystemProperties(Consumer<T> propertyInvoker,
                                                                     String name1, String name2, String name3,
                                                                     Func3<String, String, String, P> derivation,
                                                                     boolean required) {
        String value1 = getSystemProperty(name1, required);
        String value2 = getSystemProperty(name2, required);
        String value3 = getSystemProperty(name3, required);
        bean.set(propertyInvoker, derivation.call(value1, value2, value3));

        return this;
    }

    public <P> ConfigurationBuilder<T> fromRequiredSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2, String name3,
                                                                    Func3<String, String, String, P> derivation) {
        return fromSystemProperties(propertyInvoker, name1, name2, name3, derivation, true);
    }

    private <P> ConfigurationBuilder<T> fromSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2, String name3, String name4,
                                                                    Func4<String, String, String, String, P> derivation,
                                                                     boolean required) {
        String value1 = getSystemProperty(name1, required);
        String value2 = getSystemProperty(name2, required);
        String value3 = getSystemProperty(name3, required);
        String value4 = getSystemProperty(name4, required);
        bean.set(propertyInvoker, derivation.call(value1, value2, value3, value4));

        return this;
    }

    public <P> ConfigurationBuilder<T> fromRequiredSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2, String name3, String name4,
                                                                    Func4<String, String, String, String, P> derivation) {
        return fromSystemProperties(propertyInvoker, name1, name2, name3, name4, derivation, true);
    }

    private <P> ConfigurationBuilder<T> fromSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2, String name3, String name4, String name5,
                                                                    Func5<String, String, String, String, String, P> derivation,
                                                                     boolean required) {
        String value1 = getSystemProperty(name1, required);
        String value2 = getSystemProperty(name2, required);
        String value3 = getSystemProperty(name3, required);
        String value4 = getSystemProperty(name4, required);
        String value5 = getSystemProperty(name5, required);
        bean.set(propertyInvoker, derivation.call(value1, value2, value3, value4, value5));

        return this;
    }

    public <P> ConfigurationBuilder<T> fromRequiredSystemProperties(Consumer<T> propertyInvoker,
                                                                    String name1, String name2, String name3, String name4, String name5,
                                                                    Func5<String, String, String, String, String, P> derivation) {
        return fromSystemProperties(propertyInvoker, name1, name2, name3, name4, name5, derivation, true);
    }

    private <V> ConfigurationBuilder<T> fromSystemProperty(Consumer<T> propertyInvoker, String name,
                                                                  V defaultValue, boolean required) {
        String value = getSystemProperty(name, required);
        if (value != null) {
            bean.set(propertyInvoker, new StringValue(value));
        } else {
            bean.set(propertyInvoker, defaultValue);
        }

        return this;
    }

    public <V> ConfigurationBuilder<T> fromRequiredSystemProperty(Consumer<T> propertyInvoker, String name,
                                                                  V defaultValue) {
        return fromSystemProperty(propertyInvoker, name, defaultValue, true);
    }

    public ConfigurationBuilder<T> fromRequiredSystemProperty(Consumer<T> propertyInvoker, String name) {
        return fromRequiredSystemProperty(propertyInvoker, name, null);
    }
}
