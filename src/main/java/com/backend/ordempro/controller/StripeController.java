package com.backend.ordempro.controller;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stripe")
public class StripeController {
    @PostMapping("/create-checkout-session")
    public Map<String, String> createSession() throws Exception {

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                        .setSuccessUrl("http://localhost:3000/success")
                        .setCancelUrl("http://localhost:3000/cancel")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice("price_1TIK2a1yiQSefJ7zCEyGIOPA") // seu price_id
                                        .build()
                        )
                        .build();

        Session session = Session.create(params);

        return Map.of("url", session.getUrl());
    }
}
