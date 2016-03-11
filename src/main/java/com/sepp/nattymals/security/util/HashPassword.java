package com.sepp.nattymals.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashPassword {
	
	 public static PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	 }

	public static void hash(String[] args) {
		
        ConsoleReader reader;
        String line, hash;
        try {
                System.out.printf("Jhipster password 1.0%n");
                System.out.printf("----------------%n%n");
               
                reader = new ConsoleReader(); 
                        
                line = reader.readLine();
                while (!line.equals("quit")) {                                
                        hash = passwordEncoder().encode(line);
                        System.out.println(hash);
                        line = reader.readLine();
                } 
        } catch (Throwable oops) {
                System.out.flush();
                System.err.printf("%n%s%n", oops.getLocalizedMessage());
        }
	   

	}

}
