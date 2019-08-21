package contacts;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
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

    private static void add(){
        System.out.println("Enter the name: ");
        String nameInput = scanner.nextLine().toLowerCase().trim();
        System.out.println("Enter the surname: ");
        String surnameInput = scanner.nextLine().toLowerCase().trim();
        System.out.println("Enter the number: ");
        String numberInput = scanner.nextLine().toLowerCase().trim();

        Pattern phoneNumberPattern = Pattern.compile("");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(numberInput);

        if (!phoneNumberMatcher.matches()){
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
        list();
        System.out.println("Select a record: ");
        String indexInput = scanner.nextLine().trim();
        int index;
        try{
            index = Integer.parseInt(indexInput) - 1;
        }catch(NumberFormatException e){
            System.out.println("Invalid index!");
            return;
        }

        try{
            pb.removeRecord(index);
            System.out.println("The record removed!");
        } catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid index!");
        }
    }

    private static void edit(){

    }

    private static void count(){
        System.out.println("The Phone Book has " + pb.contacts.size() + "records.");
    }

    private static void list(){
        pb.printRecords();
    }
}

class PhoneBook{
    public ArrayList<Record> contacts;

    public PhoneBook() {
        this.contacts = new ArrayList<Record>();
    }

    public PhoneBook(ArrayList<Record> records) {
        this.contacts = records;
    }

    public void addRecord(Record newRecord){
        contacts.add(newRecord);
    }

    public void removeRecord(int index) {
        contacts.remove(index);
    }

    public Record getRecord(int index){
        return contacts.get(index);
    }

    public ArrayList<Record> getContacts() {
        return contacts;
    }

    public void printRecords(){
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

    public Record(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    // getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}