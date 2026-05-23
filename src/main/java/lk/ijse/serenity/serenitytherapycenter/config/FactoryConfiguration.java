package lk.ijse.serenity.serenitytherapycenter.config;
import lk.ijse.serenity.serenitytherapycenter.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {

    private static FactoryConfiguration factoryConfiguration;

    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
        //hibernate bootstrapping and schema strategies

        Configuration configuration=new Configuration();
        configuration.configure("hibernate.cfg.xml")
                .addAnnotatedClass(Patient.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(PaymentDetail.class)
                .addAnnotatedClass(Program.class)
                .addAnnotatedClass(Registration.class)
                .addAnnotatedClass(Sessions.class)
                .addAnnotatedClass(Therapist.class)
                .addAnnotatedClass(User.class);

        sessionFactory=configuration.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return  factoryConfiguration == null ?
                factoryConfiguration =new
                        FactoryConfiguration():factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}



