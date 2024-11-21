package com.example.kurs;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;
import java.net.URL;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Создание объекта FXMLLoader для загрузки FXML файла
            FXMLLoader loader = new FXMLLoader();
            // Путь к FXML файлу, который описывает интерфейс
            URL fxmlLocation = getClass().getResource("/com/example/kurs/Sample.fxml");

            // Проверка, что FXML файл был найден
            if (fxmlLocation == null) {
                // Если файл не найден, выбрасываем исключение с описанием ошибки
                throw new IOException("FXML файл 'Sample.fxml' не найден по пути '/com/example/kurs/'. Проверьте структуру проекта.");
            }

            // Устанавливаем местоположение FXML файла для загрузки
            loader.setLocation(fxmlLocation);
            // Загружаем FXML файл и создаем корневой контейнер для сцены (GridPane)
            GridPane root = loader.load();

            // Создание сцены с заданными размерами и установкой корневого элемента
            Scene scene = new Scene(root, 1359, 753);

            // Пытаемся загрузить CSS файл для стилизации интерфейса
            URL cssLocation = getClass().getResource("/com/example/kurs/application.css");
            if (cssLocation != null) {
                // Если CSS файл найден, добавляем его к стилям сцены
                scene.getStylesheets().add(cssLocation.toExternalForm());
            } else {
                // Если CSS файл не найден, выводим сообщение об ошибке
                System.err.println("CSS файл 'application.css' не найден. Интерфейс будет без стилей.");
            }

            // Настройка окна (primaryStage)
            primaryStage.setResizable(true);  // Разрешаем изменение размера окна
            primaryStage.setScene(scene);     // Устанавливаем созданную сцену в окно

            // Пытаемся загрузить иконку для окна
            URL iconLocation = getClass().getResource("/SoftwareSales.png");
            if (iconLocation != null) {
                // Если иконка найдена, добавляем её в окно
                primaryStage.getIcons().add(new Image(iconLocation.toExternalForm()));
            } else {
                // Если иконка не найдена, выводим предупреждение
                System.err.println("Иконка 'SoftwareSales.png' не найдена. Используется стандартная иконка.");
            }

            // Устанавливаем заголовок для окна
            primaryStage.setTitle("Збирання інформації про продаж програмних продуктів");
            // Показываем окно
            primaryStage.show();

        } catch (IOException e) {
            // Обработка ошибок при загрузке FXML или других файлов
            System.err.println("Ошибка при загрузке интерфейса: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Обработка других непредвиденных ошибок
            System.err.println("Непредвиденная ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Запускаем приложение JavaFX
        launch(args);
    }
}
