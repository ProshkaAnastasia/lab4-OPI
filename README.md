Для корректной работы приложения нужно добавить несколько файлов в проект:

./gradle.properties

wildfly.user = * // без кавычек
wildfly.password = * // без кавычек
wildfly.port = remote+http://localhost:* // номер порта, на котором запущен wildfly-managment

./app/src/main/resources/hibernate.cfg.xml

<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
"-//Hibernate/Hibernate Configuration DTD//EN"
"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">*</property>
        <property name="hibernate.connection.username">*</property>
        <property name="hibernate.connection.password">*</property>
        
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="show_sql">true</property>
        <property name="dialect">org.hibernate.dialect.PostgreSQLDialect</property>

        <mapping class="models.User"/>
        <mapping class="models.UPRecord"/>

    </session-factory>
</hibernate-configuration>

Для сборки нужно вызвать команду gradle build
Она автоматически задеплоит проект на запущенный wildfly, открыть страницу можно через url:port/app