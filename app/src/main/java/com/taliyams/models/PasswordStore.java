/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.taliyams.models;

/**
 *
 * @author taliyameyswara
 */

import com.taliyams.models.Encryptor;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordStore {
    public String name; // untuk menyimpan judul akun yang tersimpan
    public String username; // untuk menyimpan username dari akun
    private String password; // untuk menyimpan password terenkripsi dari akun
    private String hashkey; // untuk menyimpan teks key yang digunakan untuk enkripsi dan dekripsi
    private double score; // untuk menyimpan skor keamanan dari password yang tersimpan 
    private int category; // untuk memyimpan kategori akun yang tersimpan

    
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;
    
    //    ========================== M E T H O D ==================================
    
  
    /* DONE
     * Constructor : PasswordStore
     * Melakukan generateKey dan menyimpan hasil generate-nya ke dalam attribut hashkey
     * Mengisi atribut name dan username berdasarkan parameter yang diberikan
     * Mengeset password menggunakan method setPassword() dari parameter plainPass
     * Mengeset kategori menggunakan method setCategory() dari parameter category
     */    
    public PasswordStore(String name, String username, String plainPass, int category){
        try {
            this.hashkey = Encryptor.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error generating key: " + e.getMessage());
        }
        
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(category);
        
    }
    
    /* DONE
     * Constructor : PasswordStore
     * Melakukan aksi yang sama persis dengan constructor sebelumnya
     * Namun pada pemanggilan setCategory() diberikan nilai UNCATEGORIZED
     */
    public PasswordStore(String name, String username, String plainPass){
        try {
            this.hashkey = Encryptor.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error generating key: " + e.getMessage());
        }
        
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(UNCATEGORIZED);
    }
    
    /* DONE
     * Method : setPassword
     * Melakukan enkripsi terhadap parameter plainPass dengan key dari hashkey menggunakan method static, 
     * Encryptor.encrypt() serta menyimpan hasilnya pada atribut password.
     * Memanggil method calculateScore()  untuk mengkalkulasi tingkat keamanan password.
     */    
    public void setPassword(String plainPass){
        /*
        Karena fungsi generateKey, encrypt, dan decrypt menimbulkan exception, 
        pada pemanggilannya kita harus membungkus dengan try ... catch atau melakukan throw dengan exception yang sesuai.
        */
        try {
            this.password = Encryptor.encrypt(plainPass, hashkey);
            calculateScore(plainPass);
        } catch (Exception e) {
             System.out.println("Error: " + e.getMessage());
        }
    }
    
    /* DONE
     * Method : getPassword
     * Memanggil method static Encryptor.decrypt() menggunakan atribut password dan hashkey serta mengembalikan hasilnya.
     */   
    public String getPassword(){
        try {
            return Encryptor.decrypt(password, hashkey);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    
    /* DONE
     * Method : setCategory
     * Hanya ada 4 kategori yang tersedia, sehingga method harus mengecek 
     * apakah kategori yang diinputkan merupakan nilai antara 0--3, jika iya maka atribut category diset sesuai parameter tersebut.
     * Jika tidak maka atribut akan diset nilanya = 0
     */   
    public void setCategory(int category){
        if (category >= 0 && category <= 3) {
            this.category = category;
        } else {
            this.category = 0;
        }
    }
    
    /* DONE
     * Method: getCategory
     * Mengembalikan teks dari kategori dengan ketentuan sebagai berikut:
     * 0 = "Belum terkategori"
     * 1 = "Aplikasi web"
     * 2 = "Aplikasi mobile"
     * 3 = "Akun lainnya"
    */
    public String getCategory(){
        if(category == 0){
            return  "Belum terkategori";
        }else if(category == 1){
            return "Aplikasi Web";
        }else if(category == 2){
            return "Aplikasi Mobile";
        }else if(category == 3){
            return "Akun lainnya";
        }else{
            return "";
        }
    }
    
    /* DONE
     * Method: calculateScore
     * Menghitung score kamanan password secara sederhana dengan ketentuan:
     * Jika panjang password lebih dari 15 maka skornya adalah 10
     * Jika panjang password kurang dari maka skornya adalah (panjang / 15) * 10
    */
    private void calculateScore(String plainPass){
        if (plainPass.length() > 15) {
            this.score = 10;
        } else {
            this.score = (plainPass.length()/15.0) * 10;
        }
    }
    
    /*
     * Method: toString
     * Mengembalikan representasi String dari object PasswordStore dengan ketentuan harus mengandung beberapa atribut berikut:
     * Username
     * Password (encrypted) 
     * Hashkey
     * Kategori
     * Score
    */
    @Override
    public String toString() {
        return "Username: " + username + "\n" +
               "Password: " + password + "\n" +
               "Hashkey: " + hashkey + "\n" +
               "Kategori: " + getCategory() + "\n" +
               "Score: " + score;
    }


            
}
