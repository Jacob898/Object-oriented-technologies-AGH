package pl.edu.agh.school.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.IMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.PersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

import java.util.HashSet;
import java.util.Set;

public class SchoolModule extends AbstractModule {

    @Provides
    PersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
        return persistenceManager;
    }

    @Override
    protected void configure() {
//        bind(PersistenceManager.class)
//                .to(SerializablePersistenceManager.class);
        bind(String.class)
                .annotatedWith(Names.named("TeachersFileName"))
                .toInstance("guice-teachers.dat");
        bind(String.class)
                .annotatedWith(Names.named("ClassFileName"))
                .toInstance("guice-classes.dat");

    }

    @Provides
    @Singleton
    Logger provideLogger() {
        Set<IMessageSerializer> serializers = new HashSet<>();
        serializers.add(new FileMessageSerializer("persistence.log"));
        return new Logger(serializers);
    }

}
