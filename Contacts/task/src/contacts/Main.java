package contacts;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.*;

public class Main {
    private static Scanner scanner;
    private static PhoneBook pb;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        pb = new PhoneBook();

        System.out.println("Enter action (add, remove, edit, count, info, exit):");
        String input = scanner.nextLine().toLowerCase().trim();

        while (!"exit".equals(input)){
            System.out.println(input);
            handleInput(input);
            System.out.println("Enter action (add, remove, edit, count, info, exit):");
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
            case "info":
                info();
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    private static boolean validatePhoneNumber(String number){
        // [a-zA-Z\d]
        Pattern phoneNumberPattern = Pattern.compile("\\+?\\([a-zA-Z\\d]{2,}\\)([\\s-][a-zA-Z\\d]{2,})*|\\+?[a-zA-Z\\d]{2,}[\\s-]\\([a-zA-Z\\d]{2,}\\)([\\s-][a-zA-Z\\d]{2,})*|\\+?([a-zA-Z\\d]{2,}[\\s-]?)+([a-zA-Z\\d]{2,}[\\s-])*");
        Matcher phoneNumberMatcher = phoneNumberPattern.matcher(number);
        return phoneNumberMatcher.matches();
    }

    private static void add(){
        System.out.println("Enter the type (person, organization): ");
        String typeInput = scanner.nextLine();

        switch (typeInput){
            case "person":
                addPersonRecord();
                break;
            case "organization":
            case "org":
                addOrganizationRecord();
                break;
            default:
                System.out.println("invalid record type");
                return;
        }
    }

    private static void addPersonRecord(){
        System.out.println("Enter the name: ");
        String nameInput = scanner.nextLine().trim();
        System.out.println("Enter the surname: ");
        String surnameInput = scanner.nextLine().trim();

        System.out.println("Enter the birth date: ");
        String birthDateInput = scanner.nextLine().trim();
        try {
            LocalDate parseAttempt = LocalDate.parse(birthDateInput);
            birthDateInput = parseAttempt.toString();
        }catch (Exception e){
            System.out.println("Bad birth date!");
            birthDateInput = "[no data]";
        }

        System.out.println("Enter the gender (M, F): ");
        String genderInput = scanner.nextLine().trim().toUpperCase();
        switch (genderInput){
            case "M":
                break;
            case "F":
                break;
            default:
                genderInput = "[no data]";
                System.out.println("Bad gender!");
                break;
        }

        System.out.println("Enter the number: ");
        String numberInput = scanner.nextLine().trim();
        if (!validatePhoneNumber(numberInput)){
            numberInput = "[no number]";
            System.out.println("Wrong number format!");
        }

        Record newRecord = new PersonRecord(nameInput, numberInput, surnameInput, genderInput, birthDateInput);

        try{
            pb.addRecord(newRecord);
            System.out.println("The record added.");
        }catch(Exception e){
            System.out.println("Failed to add record.");
        }
    }

    private static void addOrganizationRecord(){
        System.out.println("Enter the name: ");
        String nameInput = scanner.nextLine().trim();
        System.out.println("Enter the address: ");
        String addressInput = scanner.nextLine().trim();

        System.out.println("Enter the number: ");
        String numberInput = scanner.nextLine().trim();
        if (!validatePhoneNumber(numberInput)){
            numberInput = "[no number]";
            System.out.println("Wrong number format!");
        }

        Record newRecord = new OrganizationRecord(nameInput, numberInput, addressInput);
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
        int indexInput = scanner.nextInt();
        Record recordToEdit;
        try{
            int index = indexInput - 1;
            recordToEdit = pb.getRecord(index);
        }catch(Exception e){
            System.out.println("Invalid index!");
            return;
        }

        if (recordToEdit.getClass() == PersonRecord.class){
            editPersonRecord(recordToEdit);
        }else if(recordToEdit.getClass() == OrganizationRecord.class){
            editOrganizationRecord(recordToEdit);
        }
    }

    private static void editPersonRecord(Record record){
        PersonRecord recordToEdit = (PersonRecord) record;
        System.out.println("Select a field (name, surname, birth, gender, number): ");
        String fieldInput = scanner.nextLine().trim();
        switch (fieldInput){
            case "name":
                System.out.println("Enter name: ");
                String nameInput = scanner.nextLine().trim();
                recordToEdit.setName(nameInput);
                System.out.println("The record updated!");
                break;
            case "surname":
                System.out.println("Enter surname: ");
                String surnameInput = scanner.nextLine().trim();
                recordToEdit.setSurname(surnameInput);
                System.out.println("The record updated!");
                break;
            case "birth":
                System.out.println("Enter birth date: ");
                String birthDateInput = scanner.nextLine().trim();
                try {
                    LocalDate parseAttempt = LocalDate.parse(birthDateInput);
                    birthDateInput = parseAttempt.toString();
                }catch (Exception e){
                    System.out.println("Bad birth date!");
                    birthDateInput = "[no data]";
                }
                recordToEdit.setBirthdate(birthDateInput);
                System.out.println("The record updated!");
                break;
            case "gender":
                System.out.println("Enter the gender (M, F): ");
                String genderInput = scanner.nextLine().trim().toUpperCase();
                switch (genderInput){
                    case "M":
                        break;
                    case "F":
                        break;
                    default:
                        genderInput = "[no data]";
                        System.out.println("Bad gender!");
                        break;
                }
                recordToEdit.setGender(genderInput);
                System.out.println("The record updated!");
                break;
            case "number":
                System.out.println("Enter number: ");
                String numberInput = scanner.nextLine().trim();
                if (validatePhoneNumber(numberInput)){
                    recordToEdit.setPhoneNumber(numberInput);
                }else{
                    recordToEdit.setPhoneNumber("[no number]");
                    System.out.println("Wrong number format!");
                }
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("Invalid Field!");
                break;
        }
    }

    private static void editOrganizationRecord(Record record){
        OrganizationRecord recordToEdit = (OrganizationRecord) record;
        System.out.println("Select a field (name, address, number): ");
        String fieldInput = scanner.nextLine().trim();
        switch (fieldInput){
            case "name":
                System.out.println("Enter name: ");
                String nameInput = scanner.nextLine().trim();
                recordToEdit.setName(nameInput);
                System.out.println("The record updated!");
                break;
            case "address":
                System.out.println("Enter address: ");
                String addressInput = scanner.nextLine().trim();
                recordToEdit.setAddress(addressInput);
                System.out.println("The record updated!");
                break;

            case "number":
                System.out.println("Enter number: ");
                String numberInput = scanner.nextLine().trim();
                if (validatePhoneNumber(numberInput)){
                    recordToEdit.setPhoneNumber(numberInput);
                }else{
                    recordToEdit.setPhoneNumber("[no number]");
                    System.out.println("Wrong number format!");
                }
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("invalid field");
                break;
        }
    }

    private static void count(){
        System.out.println("The Phone Book has " + pb.contacts.size() + " records.");
    }

    private static void list(){
        pb.printRecords();
    }

    private static void info() {
        list();
        System.out.println("Enter index to show info: ");
        int indexInput = scanner.nextInt();
        try{
            int index = indexInput - 1;
            Record recordToList = pb.getRecord(index);
            if (recordToList.getClass() == PersonRecord.class){
                PersonRecord localRecord = (PersonRecord) recordToList;
                System.out.println("Name: " + localRecord.getName());
                System.out.println("Surname: " + localRecord.getSurname());
                System.out.println("Birth date: " + localRecord.getBirthdate());
                System.out.println("Gender: " + localRecord.getGender());
                System.out.println("Number: " + localRecord.getPhoneNumber());
                System.out.println("Time created: " + localRecord.getCreated());
                System.out.println("Time last edit: " + localRecord.getMostRecentEdit());
            }else if (recordToList.getClass() == OrganizationRecord.class){
                OrganizationRecord localRecord = (OrganizationRecord) recordToList;
                System.out.println("Organization name: " + localRecord.getName());
                System.out.println("Address: " + localRecord.getAddress());
                System.out.println("Number: " + localRecord.getPhoneNumber());
                System.out.println("Time created: " + localRecord.getCreated());
                System.out.println("Time last edit: " + localRecord.getMostRecentEdit());
            }
        }catch(Exception e){
            System.out.println("Invalid index!");
            return;
        }
    }
}

class PhoneBook{
    ArrayList<Record> contacts;

    PhoneBook() {
        this.contacts = new ArrayList<>();
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
            if (r.getClass() == PersonRecord.class){
                PersonRecord localRecord = (PersonRecord) r;
                System.out.println(String.format("%d. %s %s", count, localRecord.getName(), localRecord.getSurname()));
            }else if (r.getClass() == OrganizationRecord.class){
                OrganizationRecord localRecord = (OrganizationRecord) r;
                System.out.println(String.format("%d. %s", count, localRecord.getName()));
            }
            count += 1;
        }
    }
}

class Record{
    String name;
    String phoneNumber;
    String created;
    String mostRecentEdit;

    Record(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.created = LocalDateTime.now().toString();
    }

    // getters and setters
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
        this.mostRecentEdit = LocalDateTime.now().toString();
    }
    String getPhoneNumber() {
        return phoneNumber;
    }
    void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.mostRecentEdit = LocalDateTime.now().toString();
    }

    public String getCreated() {
        return created;
    }

    public String getMostRecentEdit() {
        return mostRecentEdit;
    }

    public void setMostRecentEdit(LocalDateTime mostRecentEdit) {
        this.mostRecentEdit = mostRecentEdit.toString();
    }
}

class PersonRecord extends Record {
    String surname;
    String gender;
    String birthdate;

    PersonRecord(String name, String phoneNumber, String surname, String gender, String birthdate){
        super(name, phoneNumber);
        this.surname = surname;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        this.mostRecentEdit = LocalDateTime.now().toString();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        this.mostRecentEdit = LocalDateTime.now().toString();

    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
        this.mostRecentEdit = LocalDateTime.now().toString();
    }
}

class OrganizationRecord extends Record {
    String address;

    OrganizationRecord(String name, String phoneNumber, String address){
        super(name, phoneNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.mostRecentEdit = LocalDateTime.now().toString();
    }
}

