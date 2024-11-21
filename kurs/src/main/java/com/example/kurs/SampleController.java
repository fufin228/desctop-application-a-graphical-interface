package com.example.kurs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Iterator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import java.io.*;
import java.util.ArrayList;

public class SampleController {

    @FXML
    private Button settingsButton;

    @FXML
    private Button ChangeColorAction;


    @FXML
    private TextField NumRecordsField;

    @FXML
    private Label ResultLabel;

    @FXML
    private ContextMenu settingsMenu;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField ProductField;

    @FXML
    private TextField QuantityField;

    @FXML
    private TextField RetailPriceField;

    @FXML
    private TextField WholesalePriceField;

    @FXML
    private TextField WarrantyField;

    @FXML
    private Label NumRecordsLabel;

    @FXML
    private Button AddToFileButton;

    @FXML
    private Button BufferButton;

    @FXML
    private GridPane DataGridPane;

    @FXML
    private TextField SearchField;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField DeleteProductField;

    @FXML
    private TextField NewValueField;

    @FXML
    private Button UpdateButton;

    @FXML
    private VBox optionsContainer;

    @FXML
    private Button ClearDataButton;  // Кнопка для очистки данных

    private ArrayList<ProductSale> addList = new ArrayList<>();
    private String db_path = "src/data.txt";  // Путь к файлу данных

    @FXML
    private Label dateTimeLabel; // Метка для отображения времени

    // Метод для обновления метки с текущей датой и временем
    private void updateDateTime() {
        // Форматируем дату и время
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); // Для даты
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss"); // Для времени

        String currentDate = sdfDate.format(new Date()); // Текущая дата
        String currentTime = sdfTime.format(new Date()); // Текущее время

        // Устанавливаем текст метки с добавлением "Date" и "Time"
        dateTimeLabel.setText("Date: " + currentDate + "    Time: " + currentTime);

        // Устанавливаем белый цвет для текста
        dateTimeLabel.setStyle("-fx-text-fill: white;");
    }

    @FXML
    public void initialize() {
        // Настройка ComboBox
        ObservableList<String> options = FXCollections.observableArrayList(
                "Кількість",
                "Роздрібна ціна",
                "Оптова ціна",
                "Гарантійний термін"
        );
        comboBox.setItems(options);

        // Задаем путь к файлу по умолчанию
        db_path = "путь_к_вашему_файлу.txt"; // Поменяйте на нужный путь к файлу

        // Загружаем данные из файла
        loadDataFromFile(db_path);

        // Настройка таймера для обновления времени
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> updateDateTime()), // Начальный момент
                new KeyFrame(Duration.seconds(1)) // Обновление каждую секунду
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Бесконечный цикл
        timeline.play(); // Запускаем анимацию
    }

    @FXML
    public void showOptions() {
        // Создаем диалоговое окно с двумя кнопками
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Налаштування");
        alert.setHeaderText("Виберіть дію:");

        // Создаем кнопку для информации о программе
        Button infoButton = new Button("Інформація про програму");
        infoButton.setOnAction(event -> showInfo());



        VBox vbox = new VBox();
        vbox.getChildren().addAll(infoButton);

        // Устанавливаем контейнер с кнопками как содержимое диалога
        alert.getDialogPane().setContent(vbox);

        // Показываем диалог
        alert.showAndWait();
    }

    // Метод для показа информации о программе
    @FXML
    public void showInfo() {
        // Создаем всплывающее окно с информацией о программе
        Alert infoAlert = new Alert(AlertType.INFORMATION);
        infoAlert.setTitle("Інформація про програму");
        infoAlert.setHeaderText("Про програму");
        infoAlert.setContentText("desktop-застосунок з графічним  інтерфейсом для збирання інформації про продаж програмних продуктів");
        infoAlert.showAndWait();
    }

    @FXML
    void saveToFile(ActionEvent event) {
        // Проверяем, заполнены ли все поля
        if (ProductField.getText().isEmpty() ||
                QuantityField.getText().isEmpty() ||
                RetailPriceField.getText().isEmpty() ||
                WholesalePriceField.getText().isEmpty() ||
                WarrantyField.getText().isEmpty()) {

            // Если хотя бы одно поле пустое, показываем ошибку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            Stage stage = (Stage) dialogPane.getScene().getWindow();

            alert.setTitle("Помилка введення");
            alert.setHeaderText("Заповніть усі поля!");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }

        // Получаем данные из полей
        String productName = ProductField.getText();
        int quantity = Integer.parseInt(QuantityField.getText());
        double retailPrice = Double.parseDouble(RetailPriceField.getText());
        double wholesalePrice = Double.parseDouble(WholesalePriceField.getText());
        int warranty = Integer.parseInt(WarrantyField.getText());

        // Создаем новый объект ProductSale
        ProductSale product = new ProductSale(productName, quantity, retailPrice, wholesalePrice, warranty);

        // Добавляем объект в список
        addList.add(product);

        // Обновляем метку с количеством записей
        NumRecordsLabel.setText("Кількість записів у буфері: " + addList.size());

        // Сохраняем данные в файл
        int j = 0;
        File file = new File(db_path); // Путь к файлу
        if (!file.exists()) {
            // Если файл не существует, показываем ошибку
            showAlert(Alert.AlertType.ERROR, "Помилка", "Файл відсутній. Оберіть файл для запису.");
            return;
        }

        if (addList.size() == 0) {
            // Если список пуст, показываем ошибку
            showAlert(Alert.AlertType.ERROR, "Input Error!", "Записи відсутні!");
        } else {
            try (PrintWriter out = new PrintWriter(new FileWriter(db_path, true))) {
                // Записываем данные из addList в файл
                Iterator<ProductSale> al = addList.iterator();
                while (al.hasNext()) {
                    ProductSale element = al.next();
                    // Здесь предполагается, что у вашего объекта ProductSale есть метод writeData
                    // Этот метод должен отвечать за форматирование данных перед записью
                    element.writeData(out);
                    j = j + 1;
                }

                // Обновляем метку с количеством записей
                NumRecordsLabel.setText("Кількість записів у буфері: " + j);

                // Очищаем список после записи
                addList.clear();


                // Показываем успешное уведомление
                showAlert(Alert.AlertType.INFORMATION, "Product Added", "Продукти успішно додано в буфер!");
            } catch (IOException exception) {
                exception.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Input Error!", "Сталася помилка при записі в файл!");
            }
        }

        // Очищаем поля ввода
        clearFields();
    }




    @FXML
    private void searchProduct(ActionEvent event) {
        String keyword = SearchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Будь ласка, введіть назву продукту!");
            return;
        }

        DataGridPane.getChildren().clear();
        int row = 0;

        boolean found = false;
        for (ProductSale product : addList) {
            if (product.getProductName().toLowerCase().contains(keyword)) {
                // Добавляем данные о товаре с нужным разделителем
                addGridCell("Назва товару: " + product.getProductName(), 0, row++);
                addGridCell("Кількість: " + product.getQuantity(), 0, row++);
                addGridCell("Роздрібна ціна: " + product.getRetailPrice(), 0, row++);
                addGridCell("Оптова ціна: " + product.getWholesalePrice(), 0, row++);
                addGridCell("Гарантійний термін: " + product.getWarrantyPeriod(), 0, row++);

                // Добавляем разделитель
                addGridCell("=======================", 0, row++);

                found = true;
            }
        }

        if (!found) {
            showAlert(Alert.AlertType.INFORMATION, "Search Result", "Нічого не знайдено!");
        }
    }

    @FXML
    private void updateProduct(ActionEvent event) {
        String productName = SearchField.getText().trim();
        String newValue = NewValueField.getText().trim();
        String selectedField = comboBox.getValue();

        if (productName.isEmpty() || newValue.isEmpty() || selectedField == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Заповніть всі поля!");
            return;
        }

        boolean found = false;
        for (ProductSale product : addList) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                try {
                    switch (selectedField) {
                        case "Кількість":
                            product.setQuantity(Integer.parseInt(newValue));
                            break;
                        case "Роздрібна ціна":
                            product.setRetailPrice(Double.parseDouble(newValue));
                            break;
                        case "Оптова ціна":
                            product.setWholesalePrice(Double.parseDouble(newValue));
                            break;
                        case "Гарантійний термін":
                            product.setWarrantyPeriod(Integer.parseInt(newValue));
                            break;
                        default:
                            showAlert(Alert.AlertType.ERROR, "Invalid Field", "Невірне поле для зміни!");
                            return;
                    }
                    found = true;
                    showAlert(Alert.AlertType.INFORMATION, "Update Successful", "Продукт оновлено!");
                    addToBuffer(); // Сохраняем изменения в файл
                    break;
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Format", "Невірний формат для нового значення!");
                    return;
                }
            }
        }

        if (!found) {
            showAlert(Alert.AlertType.ERROR, "Product Not Found", "Продукт не знайдено!");
        }
    }

    @FXML
    void addToBuffer() {
        // Проверяем, заполнены ли все поля
        if (ProductField.getText().isEmpty() ||
                QuantityField.getText().isEmpty() ||
                RetailPriceField.getText().isEmpty() ||
                WholesalePriceField.getText().isEmpty() ||
                WarrantyField.getText().isEmpty()) {

            // Если хотя бы одно поле пустое, показываем ошибку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            Stage stage = (Stage) dialogPane.getScene().getWindow();

            alert.setTitle("Помилка введення");
            alert.setHeaderText("Заповніть усі поля!");
            alert.setContentText(null);
            alert.showAndWait();
            return;
        }

        try {
            // Считываем данные из полей
            String productName = ProductField.getText();
            int quantity = Integer.parseInt(QuantityField.getText());
            double retailPrice = Double.parseDouble(RetailPriceField.getText());
            double wholesalePrice = Double.parseDouble(WholesalePriceField.getText());
            int warranty = Integer.parseInt(WarrantyField.getText());

            // Создаем новый объект продукта и добавляем его в список
            ProductSale product = new ProductSale(productName, quantity, retailPrice, wholesalePrice, warranty);
            addList.add(product);

            // Показываем сообщение о том, что данные успешно сохранены
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            DialogPane dialogPane = alert.getDialogPane();
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            alert.setTitle("Дані збережені");
            alert.setHeaderText("Дані успішно збережені!");
            alert.setContentText(null);
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // В случае ошибки в формате чисел, показываем ошибку
            Alert alert = new Alert(Alert.AlertType.ERROR);
            DialogPane dialogPane = alert.getDialogPane();
            Stage stage = (Stage) dialogPane.getScene().getWindow();

            alert.setTitle("Помилка введеня");
            alert.setHeaderText("Невірний формат!");
            alert.setContentText(null);
            alert.showAndWait();
        }

        // Очищаем поля после сохранения
        ProductField.clear();
        QuantityField.clear();
        RetailPriceField.clear();
        WholesalePriceField.clear();
        WarrantyField.clear();
        NumRecordsField.setText("Кількість у буфері: " + addList.size());
    }


    @FXML
    void showData() {  // Убираем ActionEvent из параметров
        DataGridPane.getChildren().clear();
        int row = 0;
        for (ProductSale product : addList) {
            addGridCell("Назва товару:", 0, row);
            addGridCell(product.getProductName(), 1, row++);
            addGridCell("Кількість:", 0, row);
            addGridCell(String.valueOf(product.getQuantity()), 1, row++);
            addGridCell("Роздрібна ціна:", 0, row);
            addGridCell(String.valueOf(product.getRetailPrice()), 1, row++);
            addGridCell("Оптова ціна:", 0, row);
            addGridCell(String.valueOf(product.getWholesalePrice()), 1, row++);
            addGridCell("Гарантійний термін:", 0, row);
            addGridCell(String.valueOf(product.getWarrantyPeriod()), 1, row++);

            // Добавляем разделитель после каждого продукта
            addGridCell("=======================", 0, row++);
        }

        NumRecordsLabel.setText("Number of records: " + addList.size());
    }

    @FXML
    private void deleteProduct(ActionEvent event) {
        // Получаем название товара, который нужно удалить
        String productName = DeleteProductField.getText().trim();

        // Проверяем, что поле не пустое
        if (productName.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Будь ласка, введіть назву продукту для видалення!");
            return;
        }

        // Перебираем список продуктов для поиска совпадений
        boolean found = false;
        Iterator<ProductSale> iterator = addList.iterator();
        while (iterator.hasNext()) {
            ProductSale product = iterator.next();
            if (product.getProductName().equalsIgnoreCase(productName)) {
                iterator.remove();  // Удаляем товар из списка
                found = true;
                break;
            }
        }

        // Показать результат
        if (found) {
            showAlert(Alert.AlertType.INFORMATION, "Delete Successful", "Продукт успішно видалено!");
            addToBuffer();  // Сохраняем изменения
            showData();     // Обновляем отображение данных
        } else {
            showAlert(Alert.AlertType.ERROR, "Product Not Found", "Продукт не знайдено!");
        }

        // Очистим поле ввода
        DeleteProductField.clear();
    }

    private void loadDataFromFile(String filePath) {
        addList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");  // Разделяем строку по символу '|'
                if (parts.length == 5) {
                    String name = parts[0].split(":")[1].trim();  // Извлекаем данные после ':'
                    int quantity = Integer.parseInt(parts[1].split(":")[1].trim());
                    double retailPrice = Double.parseDouble(parts[2].split(":")[1].trim());
                    double wholesalePrice = Double.parseDouble(parts[3].split(":")[1].trim());
                    int warrantyPeriod = Integer.parseInt(parts[4].split(":")[1].trim());

                    // Создаем объект и добавляем в список
                    addList.add(new ProductSale(name, quantity, retailPrice, wholesalePrice, warrantyPeriod));
                }
            }

            // Обновляем метку с количеством записей в буфере
            NumRecordsLabel.setText("Кількість записів у буфері: " + addList.size());

        } catch (IOException e) {
            e.printStackTrace();  // Можете вывести ошибку в консоль, если нужно
        }
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void addGridCell(String text, int col, int row) {
        Label label = new Label(text);
        label.setFont(new Font("Arial", 14));
        label.setTextFill(textColor); // Устанавливаем цвет текста
        DataGridPane.add(label, col, row);
    }

    @FXML
    private void updateRecordCount() {
        int numRecords = addList.size(); // Получаем текущее количество записей
        ResultLabel.setText("Кількість записів: " + numRecords);
    }


    @FXML
    private void openFileAction(ActionEvent event) {
        // Получаем ссылку на текущее окно (Stage)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Создаем объект FileChooser для выбора файла
        FileChooser fileChooser = new FileChooser();

        // Устанавливаем фильтр для выбора только текстовых файлов
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        // Устанавливаем заголовок для окна выбора файла
        fileChooser.setTitle("Відкрити файл");

        // Показываем окно выбора файла и получаем выбранный файл
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Если файл выбран, сохраняем его путь в переменной db_path
        if (selectedFile != null) {
            db_path = selectedFile.getAbsolutePath();  // Обновляем путь к файлу
            loadDataFromFile(db_path);
        }
    }

    private Color textColor = Color.BLACK;  // По умолчанию чёрный цвет

    @FXML
    private void changeColorAction(ActionEvent event) {
        Stage window = new Stage();
        window.setTitle("Оберіть колір");
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #111111");
        pane.setBackground(Background.EMPTY);

        ColorPicker cp = new ColorPicker();
        cp.setPrefWidth(130);
        cp.setPrefHeight(70);
        cp.setStyle("-fx-font-size: 18px;");

        Button btn = new Button();
        btn.setPrefSize(130, 70);
        btn.setText("Обрати");
        btn.setStyle("-fx-font-size: 18px;");

        btn.setOnAction(e -> {
            textColor = cp.getValue(); // Устанавливаем выбранный цвет
            window.close();
        });

        pane.setRight(cp);
        pane.setLeft(btn);
        window.setScene(new Scene(pane, 400, 70));
        window.setResizable(false);
        window.show();
    }
    @FXML
    private void clearFields() {
        DataGridPane.getChildren().clear();
        DeleteProductField.clear();  // Очищаем поле для ввода названия продукта
        NumRecordsField.clear();  // Очищаем поле для количества записей
        ResultLabel.setText("");  // Очищаем метку результата
    }

}