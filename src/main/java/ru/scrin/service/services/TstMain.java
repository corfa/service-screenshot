package ru.scrin.service.services;

public class TstMain {

    public static void main(String[] args) {

       String x = JwtUtil.generateToken("ivan");
       x+="5";
       System.out.println(JwtUtil.validateToken(x));
    }

}
