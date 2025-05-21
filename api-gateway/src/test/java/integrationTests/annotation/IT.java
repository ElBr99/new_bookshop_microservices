package integrationTests.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.wiremock.spring.EnableWireMock;
import ru.ifellow.jschool.ebredichina.ApplicationRunner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@WebAppConfiguration
@ActiveProfiles("test")
//@ContextConfiguration(classes = ApplicationRunner.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(classes = ApplicationRunner.class)
public @interface IT {

}
