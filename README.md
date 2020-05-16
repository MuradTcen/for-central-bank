### Задание на разработчика Java:

#### Напишите программу, эмулирующую работу диспетчера печати документов.

1. Программа должна быть написана с использованием Java 7.
2. Диспетчер печати может работать с несколькими типами документов (3-5 типов).
3. Каждый тип документа должен иметь уникальные реквизиты: продолжительность печати, наименование типа документа, размер бумаги.
4. Диспетчер помещает в очередь печати неограниченное количество документов. При этом каждый документ может быть обработан, только если в это же время не обрабатывается другой документ, время обработки каждого документа равно продолжительности печати данного документа.
5. Диспетчер должен иметь следующие методы:
* Остановка диспетчера. Печать документов в очереди отменяется. На выходе должен быть список ненапечатанных документов.
* Принять документ на печать. Метод не должен блокировать выполнение программы.
* Отменить печать принятого документа, если он еще не был напечатан.
* Получить отсортированный список напечатанных документов. Список может быть отсортирован на выбор: по порядку печати, по типу документов, по продолжительности печати, по размеру бумаги.
* Рассчитать среднюю продолжительность печати напечатанных документов

 

#### Правила оформления результатов:

1. Результатом выполнения задания (в зависимости от типа задания) являются текстовые документы, исходные коды/файлы проектов.

2. Скомпилированные/исполняемые модули должны быть исключены из результата.

3. Решение должно запускаться локально, либо при размещении на изолированном веб-сервере, т.е. включать в себя все необходимые зависимости.

4. К результатам необходимо приложить текстовый файл «list.txt», содержащий список направляемых файлов, включая относительные пути.

##### Пример содержания:

PO\Game.java

PO\Helper.java

PO\Сценарий тестирования.docx

PO\Постановка задачи.docx

5. К полным именам всех файлов необходимо добавить второе расширение txt (например, Game.java.txt).

6. После этого все файлы результатов с расширением txt, включая «list.txt», без упаковки в архивы, прикрепляются к ответному письму.

----

Сборка докер-контейнера: `sudo docker build -t app .`

Запуск докер-контейнера: `sudo docker run -p 8080:8080 app`

Завершение докер-контейнера: `sudo docker stop CONTAINER ID`

Узнать CONTAINER ID докер-контейнера: `sudo docker ps`

Доступно описание API через SWAGGER: `http://localhost:8080/swagger-ui.html#/document-controller`


<a href="https://priscree.ru/img/e1c9fb450395a3.png" target="_blank">
<img src="https://priscree.ru/img/e1c9fb450395a3.png" border="0"></a>

----

#### Что получилось:
*
*
*
----

#### Что не получилось:
*
*
*

----
#### Затрачено времени:

