
import java.util.Scanner;
import  ducdmd.utils.SHA256;
import java.security.NoSuchAlgorithmException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MSI
 */
public class SHA256_Testing {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("Enter string to hash: ");
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        
        System.out.println(SHA256.getHash(input).length());
    }
}
