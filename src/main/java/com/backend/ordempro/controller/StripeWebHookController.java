package com.backend.ordempro.controller;
import com.backend.ordempro.model.PremiumTenants;
import com.backend.ordempro.repository.PremiumTenantsRepository;
import com.backend.ordempro.repository.TenantsRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.Optional;

@RestController
public class StripeWebHookController {
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;
    private final PremiumTenantsRepository premiumTenantsRepository;
    private final TenantsRepository tenantsRepository;
    private static final Logger log = LoggerFactory.getLogger(StripeWebHookController.class);

    public StripeWebHookController(PremiumTenantsRepository premiumTenantsRepository,
                                   TenantsRepository tenantsRepository){
        this.premiumTenantsRepository = premiumTenantsRepository;
        this.tenantsRepository = tenantsRepository;
    }

    @PostMapping("/stripe/events")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        log.info("Webhook recebido do Stripe");
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            log.error("Falha na verificação da assinatura do Stripe", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        switch (event.getType()) {
            case "checkout.session.completed":
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                handleCheckoutComplete(session);
                break;

            case "invoice.paid":
                com.stripe.model.Invoice invoice = (com.stripe.model.Invoice) event.getDataObjectDeserializer().getObject().get();
                handleInvoicePaid(invoice);
                break;

            case "customer.subscription.deleted":
                com.stripe.model.Subscription subscription = (com.stripe.model.Subscription) event.getDataObjectDeserializer().getObject().get();
                handleSubscriptionDeleted(subscription);
                break;
        }

        return ResponseEntity.ok("");
    }

    private void handleCheckoutComplete(Session session) {
        String stripeCustomerId = session.getCustomer();
        String tenantIdStr = session.getMetadata().get("tenant_id");

        if (tenantIdStr != null) {
            Long tenantId = Long.parseLong(tenantIdStr);
            atualizarOuCriarPremium(tenantId, stripeCustomerId);
        }
    }

    private void handleInvoicePaid(com.stripe.model.Invoice invoice) {
        String stripeCustomerId = invoice.getCustomer();
        PremiumTenants premium = premiumTenantsRepository.findByStripeCustomerId(stripeCustomerId);

        if (premium != null) {
            premium.setDueDate(LocalDate.now().plusMonths(1));
            premiumTenantsRepository.save(premium);
        }
    }

    private void handleSubscriptionDeleted(com.stripe.model.Subscription subscription) {
        String stripeCustomerId = subscription.getCustomer();
        PremiumTenants premium = premiumTenantsRepository.findByStripeCustomerId(stripeCustomerId);

        if (premium != null) {
            premiumTenantsRepository.delete(premium);
        }
    }

    private void atualizarOuCriarPremium(Long tenantId, String stripeCustomerId) {
        LocalDate now = LocalDate.now();
        Optional<PremiumTenants> premiumOpt = premiumTenantsRepository.findByTenantsId(tenantId);

        PremiumTenants premium = premiumOpt.orElseGet(() -> {
            PremiumTenants p = new PremiumTenants();
            tenantsRepository.findById(tenantId).ifPresent(p::setTenants);
            p.setBaseDate(now);
            return p;
        });

        premium.setStripeCustomerId(stripeCustomerId);
        premium.setDueDate(now.plusMonths(1));
        premiumTenantsRepository.save(premium);
    }
}
