import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
public class Test {
    // ЗАДАНИЕ. Реализуйте структуру телефонной книги с помощью HashMap.
    // Программа также должна учитывать, что во входной структуре будут повторяющиеся имена с разными телефонами, их
    // необходимо считать, как одного человека с разными телефонами. Вывод должен быть отсортирован по убыванию числа телефонов.
    public static void main(String[] args) throws IOException {
        BookGenerator(); // Создаем и заполняем телефонную книгу (документ telephone_book.txt)
        ArrayList<String> abonentList = BookReader(); // Создаем строковый список для хранения данных абонентов из телефонной книги.
        // Загружаем абонентов из книги в список
        HashMap<String, Integer> myMap =  MapFiller(abonentList); // Инициализируем коллекцию HashMap, ключ - String (ФИО + номер),
        // значение - Integer (кол-во номеров). Заполняем коллекцию HashMap данными
        // из списка абонентов
        MapSortedPrint(myMap); // Сортированный по убыванию числа номеров вывод коллекции myMap
    } // The end of the main method
    public static void BookGenerator() {
        try(FileWriter writer = new FileWriter("telephone_book.txt", false)) {
            writer.write("Великий Борис Иосифович 8 900 222 12 12");
            writer.append("\n");
            writer.write("Филипова Зинаида Степановна 8 900 111 21 21");
            writer.append("\n");
            writer.write("Коваленко Борис Николаевич 8 999 999 99 99");
            writer.append("\n");
            writer.write("Кен Александр Игоревич 1 111 111 11 11");
            writer.append("\n");
            writer.write("Максимов Майкл Отчествович 1 111 111 11 12");
            writer.append("\n");
            writer.write("Великанова Ольга Олеговна 8 900 222 22 22");
            writer.append("\n");
            writer.write("Коваленко Борис Николаевич 3 333 333 33 33");
            writer.append("\n");
            writer.write("Филипова Зинаида Степановна 8 921 127 24 54");
            writer.append("\n");
            writer.write("Филипова Зинаида Степановна 8 977 117 91 81");
            writer.append("\n");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    } // The end of the BookGenerator method
    public static ArrayList<String> BookReader() throws IOException {
        ArrayList<String> abonentList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader("telephone_book.txt"));
        String str;
        while ((str = reader.readLine()) != null) {
            abonentList.add(str);
        }
        reader.close();
        return abonentList;
    } // The end of the BookReader method
    public static HashMap<String, Integer> MapFiller(ArrayList<String> abonentList) {
        HashMap<String, Integer> myMap = new HashMap<>(); // Инициализируем локальный экземпляр коллекции для выгрузки return'ом
        ArrayList<String> abonentFIO = new ArrayList<String>(); // Инициализируем список ФИО абонентов (чтобы считать повторы)
        for (int i = 0; i < abonentList.size(); i++) {
            String[] str = abonentList.get(i).split(" ");
            String tmp = str[0] + " " + str[1] + " " + str[2];
            abonentFIO.add(tmp);
        } // Теперь в списке abonentFIO хранятся только ФИО абонентов
        ArrayList<Integer> abonentNumbersValue = new ArrayList<>(); // Инициализируем список количества номеров телефонов абонента
        int count = 0; // Счетчик повторов ФИО одного абонента в списке
        for (int i = 0; i < abonentList.size(); i++) {
            for (int j = 0; j < abonentList.size(); j++) {
                if (abonentFIO.get(i).equals(abonentFIO.get(j))) {count++;}
            } // Inner cycle
            myMap.put(abonentList.get(i), count);
            count = 0;
        } // Outer cycle
        // Имеем коллекцию myMap, в которой в качестве ключей используются ФИО+номер абонента, а в качестве значений -
        // количество номеров у одного абонента. Возвращаем эту кооллекцию.
        return myMap;
    } // The end of the MapFiller method
    public static void MapSortedPrint(HashMap<String, Integer> myMap) {
        ArrayList<Integer> list = new ArrayList<Integer>(); // Инициализируем список для значений коллекции myMap
        list.addAll(myMap.values()); // Добавляем значения из коллекции в список
        list.sort(Comparator.reverseOrder()); // Сортируем список по убыванию
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            int v = list.get(i);
            for (int j = 0; j < myMap.size(); j++) {
                String k = new ArrayList<>(myMap.keySet()).get(j);
                if (v == myMap.get(k)) {
                    System.out.println("Абонент: " + k + ", кол-во номеров: " + v);
                    count++;
                }
            } // Inner cycle
            i = i + count;
            count = 0;
        } // Outer cycle
    } // The end of the MapSortedPrint method
} // The end of the TelephoneBook class
