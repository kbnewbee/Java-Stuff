package com.ea.eadp.product.product.model;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class Utility {
    public String generateUserId(){
        return UUID.randomUUID().toString();
    }
}
