# Практична робота "Робота з файлами та мережею"

[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://openjdk.org/)
[![JSOUP](https://img.shields.io/badge/JSOUP-1.16.1-green.svg)](https://jsoup.org/)

Цей проект є виконанням практичної роботи з модуля "Файли та мережеві комунікації". Основна мета — навчитися обробляти дані з різних джерел: файлів (CSV, JSON) та мережевих ресурсів.

## Мета роботи
Навчитися:
- Рекурсивно обходити дерево папок
- Читати та обробляти файли форматів CSV і JSON
- Отримувати дані з HTML-сторінок за допомогою парсингу
- Створювати JSON-файли на основі зібраних даних

## Технічні вимоги
- **JDK 17** або вище
- **Бібліотеки:**
  - JSOUP (для роботи з HTML)
  - Gson/Jackson (для роботи з JSON)
  - OpenCSV (для роботи з CSV)

## Структура проекту


## Основні завдання
### 1. Парсинг веб-сторінки
**Клас:** `MetroPageParser.java`  
**Методи:**
- `parse()` - отримує HTML за URL і парсить його
- `parseLines()` - видобуває дані про лінії метро
- `parseStations()` - видобуває дані про станції метро
- `parseConnections()` - видобуває дані про пересадки між станціями

**Джерело:** [Список станцій Московського метро](https://skillbox-java.github.io/)

### 2. Обробка файлів
**Класи:**
- `CsvParser.java` - читає CSV файли
- `JsonParser.java` - читає JSON файли

**Методи для кожного класу:**
- `parse(Path path)` - завантажує дані з файлу

### 3. Рекурсивний обхід файлів
**Клас:** `FileFinder.java`
**Метод:**
- `findFiles(String path, String... extensions)` - обходить директорії та повертає список файлів
- `findFilesRecursively(Path path, String... extensions)` - рекурсивно знаходить файли з вказаними розширеннями

### 4. Експорт даних
**Класи:**
`StationDataWriter.java`
`StationDataJsonWriter.java`
**Методи:**
- `write(List<Line> lines, List<Station> stations, List<Connection> connections, List<StationDepth> depths, List<StationInfo> dates)` - записує дані в JSON файл

## Як запустити
1. Клонуйте репозиторій:
   ```bash
   git clone https://github.com/shazon-noname/FilesAndNetwork.git
    ```
2. Перейдіть до директорії проекту:
3. ```bash
   cd FilesAndNetwork
   ```
4. Скомпілюйте проект:
   ```bash
    ./mvnw clean build
    ```
5. Запустіть головний клас:
6. ```bash
   java -cp build/libs/FilesAndNetwork.jar com.example.Main
   ```
## Приклади використання
### Парсинг веб-сторінки
```java
MetroPageParser parser = new MetroPageParser();
List<Line> lines = parser.parseLines();
List<Station> stations = parser.parseStations();
List<Connection> connections = parser.parseConnections();
```
### Читання CSV файлу
```java
  
CsvParser csvParser = new CsvParser();
List<Station> stations = csvParser.parse(Paths.get("stations.csv"));
```
### Читання JSON файлу
```java
JsonParser jsonParser = new JsonParser();
List<Station> stations = jsonParser.parse(Paths.get("stations.json"));
```
### Рекурсивний пошук файлів
```java
FileFinder fileFinder = new FileFinder();
List<Path> csvFiles = fileFinder.findFilesRecursively(Paths.get("data"), "csv");
```
### Запис даних у JSON файл
```java
StationDataJsonWriter writer = new StationDataJsonWriter();
write(List<Line> lines,
      List<Station> stations,
      List<Connection> connections,
      List<StationDepth> depths,
      List<StationInfo> dates);
```
## Ліцензія
Цей проект ліцензований на умовах MIT License. Ви можете вільно використовувати, змінювати та розповсюджувати цей код за умови збереження авторських прав.
## Висновок
Ця практична робота дозволяє отримати навички роботи з файлами та мережевими ресурсами, що є важливими для розробки сучасних Java-додатків. Використання бібліотек для парсингу HTML, CSV та JSON значно спрощує обробку даних і робить код більш читабельним та підтримуваним.
## Додаткові ресурси
- [JSOUP Documentation](https://jsoup.org/cookbook/)
