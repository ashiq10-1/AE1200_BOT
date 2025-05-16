package com.pm.telegrambot.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProductCheckerService {

    @Value("${product_url2}")
    String productUrl;

    public boolean isProductInStock() throws IOException {
        // Fetch the product page
        Document doc = Jsoup.connect(productUrl).get();


        String page = doc.text();


        if (page.contains("Out of Stock")) {
            System.out.println("Product is still out of stock.");
            return false;
        }else{
            System.out.println("Product is in stock.");
            return true;
        }
    }
}

