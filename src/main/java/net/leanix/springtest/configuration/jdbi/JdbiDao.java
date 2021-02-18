package net.leanix.springtest.configuration.jdbi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Indexed;

// The Retention meta annotations is important! Otherwise it will be lost in runtime and Spring has no way to detect it
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface JdbiDao {

}
