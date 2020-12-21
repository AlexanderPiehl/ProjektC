package Database;

import java.io.FileNotFoundException;


import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import Tools.Config;
import Database.Entities.Artikel;
import Database.Entities.Berechtigung;
import Database.Entities.Bilder;
import Database.Entities.Reservierung;
import Database.Entities.SanktionsListe;
import Database.Entities.User;
import Database.Entities.UserGroups;
import Database.Entities.Verleihhistorie;
import Database.Entities.Wartung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HibernateHelper.class);

    public static SessionFactory sessionFactory;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void initHibernate() {
        Configuration config = getHibernateConfig();

        config.addAnnotatedClass(Berechtigung.class);
        config.addAnnotatedClass(UserGroups.class);
        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Artikel.class);
        config.addAnnotatedClass(Reservierung.class);
        config.addAnnotatedClass(SanktionsListe.class);
        config.addAnnotatedClass(Verleihhistorie.class);
        config.addAnnotatedClass(Wartung.class);
        config.addAnnotatedClass(Bilder.class);


        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(sr);
    }

    public Configuration getHibernateConfig() {
        Configuration config = new Configuration();
        String db_host = "localhost";
        String db_port = "3306";
        String db_user = "root";
        String db_password = "";
        String db_table = "ausleihe";
        try {
            Config configFile = new Config();
            db_host = configFile.getProperty_db_host();
            db_port = configFile.getProperty_db_port();
            db_user = configFile.getProperty_db_user();
            db_password = configFile.getProperty_db_password();
            db_table = configFile.getProperty_db_table();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_table); //localhost:3306/ausleihe");
        config.setProperty("hibernate.connection.username", db_user); //"root");
        config.setProperty("hibernate.connection.password", db_password); //"");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        config.setProperty("hibernate.hbm2ddl.auto", "update");
        config.setProperty("hibernate.current_session_context_class", "thread");
        return config;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public boolean checkIfUserExists(String kennung) {
        boolean exists = false;
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select user from User user where user.kennung = :kennung");
        query.setParameter("kennung", kennung);
        if (query.uniqueResult() != null) {
            exists = true;
        }
        transaction.commit();
        LOG.info("User exists {}", exists);
        return exists;
    }

}
