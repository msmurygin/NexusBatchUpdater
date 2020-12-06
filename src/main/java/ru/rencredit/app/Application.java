package ru.rencredit.app;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args)  {
        ApplicationContext context = new AnnotationConfigApplicationContext(NexusConfig.class);
        BatchUpdater updater = context.getBean(BatchUpdater.class);
        updater.update();
    }
}
