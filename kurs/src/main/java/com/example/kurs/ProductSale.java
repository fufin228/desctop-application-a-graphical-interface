package com.example.kurs;

import java.io.BufferedReader;  // Импортируем класс для чтения текста из потока ввода
import java.io.IOException;     // Импортируем исключение для обработки ошибок ввода-вывода
import java.io.PrintWriter;    // Импортируем класс для записи в текстовый файл
import java.util.ArrayList;    // Импортируем коллекцию ArrayList для работы с динамическими списками
import java.util.List;         // Импортируем интерфейс List для работы с коллекциями

// Класс ProductSale описывает товар, его характеристики и методы работы с ними
public class ProductSale {

    // Поля класса, которые описывают товар
    private String productName;        // Название товара
    private int quantity;              // Количество товара
    private double retailPrice;        // Розничная цена товара
    private double wholesalePrice;     // Оптовая цена товара
    private int warrantyPeriod;        // Гарантийный срок товара

    // Конструктор без параметров, создаёт объект с дефолтными значениями
    public ProductSale() {
        // Конструктор по умолчанию не инициализирует поля, они остаются null или 0
    }

    // Конструктор с параметрами для инициализации всех полей товара
    public ProductSale(String productName, int quantity, double retailPrice, double wholesalePrice, int warrantyPeriod) {
        this.productName = productName;      // Инициализация имени товара
        this.quantity = quantity;            // Инициализация количества товара
        this.retailPrice = retailPrice;      // Инициализация розничной цены
        this.wholesalePrice = wholesalePrice; // Инициализация оптовой цены
        this.warrantyPeriod = warrantyPeriod; // Инициализация гарантийного срока
    }

    // Геттеры и Сеттеры для работы с полями объекта
    public String getProductName() {
        return productName; // Возвращает название товара
    }

    public void setProductName(String productName) {
        this.productName = productName; // Устанавливает новое название товара
    }

    public int getQuantity() {
        return quantity; // Возвращает количество товара
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; // Устанавливает новое количество товара
    }

    public double getRetailPrice() {
        return retailPrice; // Возвращает розничную цену товара
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice; // Устанавливает новую розничную цену
    }

    public double getWholesalePrice() {
        return wholesalePrice; // Возвращает оптовую цену товара
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice; // Устанавливает новую оптовую цену
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod; // Возвращает гарантийный срок товара
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod; // Устанавливает новый гарантийный срок
    }

    // Метод записи данных о товаре в файл
    public void writeData(PrintWriter out) throws IOException {
        // Форматируем данные о товаре в строку и записываем в файл
        out.println(String.format("%s;%d;%.2f;%.2f;%d",
                productName, quantity, retailPrice, wholesalePrice, warrantyPeriod));
    }

    // Метод чтения данных из строки и их парсинг
    public void readData(String line) {
        // Разделяем строку по символу ";" и разбиваем на составляющие части
        String[] parts = line.split(";");
        if (parts.length == 5) {  // Если данные корректны (5 частей)
            this.productName = parts[0].trim(); // Устанавливаем название товара
            try {
                // Преобразуем остальные части в нужные типы данных
                this.quantity = Integer.parseInt(parts[1].trim());         // Количество
                this.retailPrice = Double.parseDouble(parts[2].trim());    // Розничная цена
                this.wholesalePrice = Double.parseDouble(parts[3].trim()); // Оптовая цена
                this.warrantyPeriod = Integer.parseInt(parts[4].trim());   // Гарантийный срок
            } catch (NumberFormatException e) {
                // Если произошла ошибка при преобразовании, выводим сообщение об ошибке
                System.err.println("Ошибка в данных: " + e.getMessage());
            }
        }
    }

    // Переопределённый метод toString для удобного представления объекта в виде строки
    @Override
    public String toString() {
        return String.format("ProductSale{productName='%s', quantity=%d, retailPrice=%.2f, wholesalePrice=%.2f, warrantyPeriod=%d}",
                productName, quantity, retailPrice, wholesalePrice, warrantyPeriod);
    }

    // Статический метод для чтения всех товаров из файла
    public static List<ProductSale> readFromFile(BufferedReader reader) throws IOException {
        List<ProductSale> products = new ArrayList<>(); // Список для хранения продуктов
        String line;
        // Читаем строки из файла
        while ((line = reader.readLine()) != null) {
            ProductSale product = new ProductSale();  // Создаём новый объект товара
            product.readData(line);  // Читаем данные и заполняем объект товара
            products.add(product);    // Добавляем объект в список
        }
        return products;  // Возвращаем список всех прочитанных товаров
    }

    // Статический метод для записи всех товаров в файл
    public static void writeToFile(List<ProductSale> products, PrintWriter out) throws IOException {
        for (ProductSale product : products) {
            product.writeData(out);  // Записываем данные каждого товара в файл
        }
    }

    // Статический метод для поиска товара по имени в списке
    public static ProductSale findProductByName(List<ProductSale> products, String productName) {
        for (ProductSale product : products) {
            // Сравниваем имя товара с переданным значением (игнорируя регистр)
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return product;  // Возвращаем найденный товар
            }
        }
        return null;  // Если товар не найден, возвращаем null
    }

    // Метод для обновления данных товара
    public void updateProduct(int quantity, double retailPrice, double wholesalePrice, int warrantyPeriod) {
        this.quantity = quantity;               // Обновляем количество товара
        this.retailPrice = retailPrice;         // Обновляем розничную цену
        this.wholesalePrice = wholesalePrice;   // Обновляем оптовую цену
        this.warrantyPeriod = warrantyPeriod;   // Обновляем гарантийный срок
    }
}
