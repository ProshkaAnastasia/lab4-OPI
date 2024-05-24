Для корректной работы приложения нужно добавить несколько файлов в проект:

./gradle.properties

wildfly.user = * // без кавычек

wildfly.password = * // без кавычек

wildfly.port = remote+http://localhost:* // номер порта, на котором запущен wildfly-managment

./app/src/main/resources/hibernate.cfg.xml

Для сборки нужно вызвать команду gradle build

Она автоматически задеплоит проект на запущенный wildfly, открыть страницу можно через url:port/app