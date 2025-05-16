package com.pm.telegrambot.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ProductAvailabilityScheduler {

    private final ProductCheckerService productCheckerService;
    private final TelegramNotificationService telegramNotificationService;

    public ProductAvailabilityScheduler(ProductCheckerService productCheckerService, TelegramNotificationService telegramNotificationService) {
        this.productCheckerService = productCheckerService;
        this.telegramNotificationService = telegramNotificationService;
    }
    String notificationIndicator = "false";

    @Scheduled(fixedRate = 6000) // Check every 6 seconds
    public void checkProductAvailability() {

        try {

            if (productCheckerService.isProductInStock()) {
                String productUrl = productCheckerService.productUrl;
                String message = "🎉 Casio AE1200 WHL available! Buy now:\n" + productUrl;
                if(notificationIndicator.equals("false")){
                    System.out.println("📢 Sending notification...");
                    telegramNotificationService.sendNotification(message);
                    System.out.println("✅ Notification sent successfully!");
                    notificationIndicator = "true";
                }

            } else {
                System.out.println("❗ Notification not sent as product is still out of stock");
                notificationIndicator = "false";
            }
        } catch (IOException e) {
            System.err.println("❌ Error while checking product availability: " + e.getMessage());
        }
    }
}
