package contacts;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Scanner scanner;
    private static PhoneBook pb;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        pb = new PhoneBook();

        System.out.println("Enter action (add, remove, edit, count, list, exit):");
        String input = scanner.nextLine().toLowerCase().trim();

        while (!"exit".equals(input)){
            handleInput(input);
            System.out.println("Enter action (add, remove, edit, count, list, exit):");
            input = scanner.nextLine().toLowerCase().trim();
        }
    }

    private static void handleInput(String input){
        switch (input){
            case "add":
                add();
                break;
            case "remove":
                remove();
                break;
            case "edit":
                edit();
                break;
            case "count":
                count();
                break;
            case "list":
                list();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private static boolean validatePhoneNumber(String number){
        Pattern phoneNumberPattern = Pattern.compile("");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(number);
        return phoneNumberMatcher.matches();
    }

    private static void add(){
        System.out.println("Enter the name: ");
        String nameInput = scanner.nextLine().toLowerCase().trim();
        System.out.println("Enter the surname: ");
        String surnameInput = scanner.nextLine().toLowerCase().trim();
        System.out.println("Enter the number: ");
        String numberInput = scanner.nextLine().toLowerCase().trim();

        if (!validatePhoneNumber(numberInput)){
            numberInput = "[no number]";
            System.out.println("Wrong number format!");
        }

        Record newRecord = new Record(nameInput, surnameInput, numberInput);
        try{
            pb.addRecord(newRecord);
            System.out.println("The record added.");
        }catch(Exception e){
            System.out.println("Failed to add record.");
        }
    }

    private static void remove(){
        if (pb.contacts.size() == 0){
            System.out.println("No records to remove!");
            return;
        }
        list();
        System.out.println("Select a record: ");
        String indexInput = scanner.nextLine().trim();
        try{
            int index = Integer.parseInt(indexInput) - 1;
            pb.removeRecord(index);
            System.out.println("The record removed!");
        }catch(NumberFormatException e){
            System.out.println("Invalid index!");
        }
    }

    private static void edit(){
        if (pb.contacts.size() == 0){
            System.out.println("No records to edit!");
            return;
        }
        list();
        System.out.println("Select a record: ");
        String indexInput = scanner.nextLine().trim();
        Record recordToEdit;
        try{
            int index = Integer.parseInt(indexInput) - 1;
            recordToEdit = pb.getRecord(index);
        }catch(Exception e){
            System.out.println("Invalid index!");
            return;
        }

        System.out.println("Select a field (name, surname, number): ");
        String fieldInput = scanner.nextLine().trim();
        switch (fieldInput){
            case "name":
                System.out.println("Enter name: ");
                String nameInput = scanner.nextLine().trim();
                recordToEdit.setSurname(nameInput);
                System.out.println("The record updated!");
                break;
            case "surname":
                System.out.println("Enter surname: ");
                String surnameInput = scanner.nextLine().trim();
                recordToEdit.setSurname(surnameInput);
                System.out.println("The record updated!");
                break;
            case "number":
                System.out.println("Enter number: ");
                String numberInput = scanner.nextLine().trim();
                if (validatePhoneNumber(numberInput)){
                    recordToEdit.setPhoneNumber(numberInput);
                    System.out.println("The record updated!");
                }else{
                    System.out.println("Wrong number format!");
                }
                break;
            default:
                System.out.println("Invalid Field!");
                break;
        }
    }

    private static void count(){
        System.out.println("The Phone Book has " + pb.contacts.size() + "records.");
    }

    private static void list(){
        pb.printRecords();
    }
}

class PhoneBook{
    ArrayList<Record> contacts;

    PhoneBook() {
        this.contacts = new ArrayList<Record>();
    }

    PhoneBook(ArrayList<Record> records) {
        this.contacts = records;
    }

    void addRecord(Record newRecord){
        contacts.add(newRecord);
    }

    void removeRecord(int index) {
        contacts.remove(index);
    }

    Record getRecord(int index){
        return contacts.get(index);
    }

    ArrayList<Record> getContacts() {
        return contacts;
    }

    void printRecords(){
        int count = 1;
        for (Record r : contacts){
            System.out.println(String.format("%d. %s %s, %s", count, r.name,r.surname, r.phoneNumber));
        }
    }
}

class Record{
    String name;
    String surname;
    String phoneNumber;

    Record(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    // getters and setters
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    String getSurname() {
        return surname;
    }
    void setSurname(String surname) {
        this.surname = surname;
    }
    String getPhoneNumber() {
        return phoneNumber;
    }
    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}