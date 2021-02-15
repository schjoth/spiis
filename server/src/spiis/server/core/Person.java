package spiis.server.core;

import java.util.ArrayList;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;



public class Person{

 /* Kommenter ut alt med 'Middag' til


    //private/public/protected???:
   private ArrayList<Middag> signedUpFor;
    private ArrayList<Middag> hosting;
    private ArrayList<String> allergies;*/

    @Column(nullable = false)
    private String name;

    private int age;
    private String adress;
    private String email;


    //The user
    public Person(String navn, int alder, /* String bursdag,*/ String addresse, String epost){
        name = navn;
        age = alder; //age = birthdayToAge(bursdag)
        adress = addresse;
        email = epost;

    /*  signedUpFor = new ArrayList<Middag>;
        hosting = new ArrayList<Middag>;
        allergies = new ArrayList<String>; //Person has no allergies or food preferences as default.*/
    }


/*
    //(the method can be extended to make a PQ...)
    protected void addAllergy(String al){
        String a = al.toLowerCase();//all allergies in 'allergies' are in lower case. Comparison and/or addition only makes sense if 'al' is also all lower case letters.
        if (allergies.contains(a) == false){
            allergies.add(a);
        }
    }

    public ArrayList<Middag> getAllergies(){
        return allergies;
    }



    public ArrayList<Middag> attend(Middag dinner){
        if (signedUpFor.contains(dinner) == false){ //if you have already signed up to attend a dinner (Middag object is already in the ArrayList)... If not:
            signedUpFor.add(a);
        }
    }

    public ArrayList<Middag> getAttending(){
        return signedUpFor;
    }



    public ArrayList<Middag> host(Middag myDinner){
        if (hosting.contains(myDinner) == false){ //Person can not host the same dinner (Middag-object) more than once.
            signedUpFor.add(a);
        }
    }

    public ArrayList<Middag> getHosting(){
        return hosting;
    }


  /*

  private int birthdayToAge(String ddmmyy){
    //
  }

  */


    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getAdress(){
        return adress;
    }

    public String getEmail(){
        return email;
    }




    @Id
    @GeneratedValue
    @Nullable
    private Long id; //lag metode for å generere ID lenger opp, til konstruktøren

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
