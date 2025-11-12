package test_cases.parent;

public class Person {

    private String name; private String surname; private String email; private String phoneNumber; private int age;

    //constructors for person class
    public Person(){}
    public Person(String name, String surname, String email, String phoneNumber, int age) {}
    public void doSomething(int val){}
    @Override
    public String toString() {
        return "name : " + name + ", surname : " + surname + ", email : " + email + ", phoneNumber : " + phoneNumber + ", age : " + age;
    }
}