package ru.ifellow.jschool.ebredichina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@EnableWebMvc
//@Configuration
//@EnableJpaRepositories(basePackages = "ru.ifellow.jschool.ebredichina.repository")
//@ComponentScan(basePackages = "ru.ifellow.jschool.ebredichina")
//@PropertySource(value = "classpath:application.properties")

@SpringBootApplication
@EnableFeignClients
public class ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationRunner.class);
//        Tomcat tomcat = new Tomcat();
//        tomcat.setPort(8081);
//        tomcat.setSilent(false);
//
//        final Connector connector = new Connector();
//        connector.setPort(8081);
//        connector.setScheme("http");
//        connector.setSecure(false);
//
//        tomcat.setConnector(connector);
//
//        File baseDir = Files.createTempDirectory("embedded-tomcat").toFile();
//
//        Context context = tomcat.addWebapp("", baseDir.getAbsolutePath());
//
//        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
//        appContext.register(ApplicationRunner.class);
//
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
//
//        Wrapper addedServlet = Tomcat.addServlet(context, "dispatcherServlet", dispatcherServlet);
//        addedServlet.setAsyncSupported(true);
//        addedServlet.setLoadOnStartup(1);
//        context.addServletMappingDecoded("/*", "dispatcherServlet");
//
//        FilterDef springSecurityFilterDef = new FilterDef();
//        springSecurityFilterDef.setFilterName("springSecurityFilterChain");
//        springSecurityFilterDef.setFilterClass(DelegatingFilterProxy.class.getName());
//        context.addFilterDef(springSecurityFilterDef);
//
//        FilterMap springSecurityFilterMap = new FilterMap();
//        springSecurityFilterMap.setFilterName("springSecurityFilterChain");
//        springSecurityFilterMap.addURLPattern("/api/*");
//
//        context.addFilterMap(springSecurityFilterMap);
//
//        tomcat.start();
//        tomcat.getServer().await();
//        //управляющий поток java нужно прикрепить к потоку tomcat. Это указывает на то, что приложение не завершается, пока работает томкат
//
//    }
    }
}

