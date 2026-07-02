// com/example/shopit/config/DataSeeder.java
package com.shopit.shop.Seeder;

import com.shopit.shop.Entity.Product;
import com.shopit.shop.Entity.Types.Category;
import com.shopit.shop.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Profile("dev")                    // only runs when spring.profiles.active=dev
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            log.info("DataSeeder: products table already has data, skipping.");
            return;
        }

        setSystemSecurityContext();     // AuditorAware needs an authenticated principal
        seedProducts();
        clearSecurityContext();

        log.info("DataSeeder: successfully inserted 10 products.");
    }

    // -------------------------------------------------------------------------
    // Products
    // -------------------------------------------------------------------------

    private void seedProducts() {
        List<Product> products = List.of(
                Product.builder()
                        .name("Sony WH-1000XM5 Headphones")
                        .description("Industry-leading noise cancelling wireless headphones with 30-hour battery life and crystal clear hands-free calling.")
                        .price(new BigDecimal("349.99"))
                        .stockQuantity(120)
                        .category(Category.ELECTRONICS)
                        .build(),

                Product.builder()
                        .name("Levi's 501 Original Fit Jeans")
                        .description("Classic straight-leg jeans crafted from heavyweight denim with a button fly and iconic Levi's styling.")
                        .price(new BigDecimal("69.99"))
                        .stockQuantity(250)
                        .category(Category.CLOTHING)
                        .build(),

                Product.builder()
                        .name("Clean Code by Robert C. Martin")
                        .description("A handbook of agile software craftsmanship covering principles, patterns, and practices for writing clean, maintainable code.")
                        .price(new BigDecimal("34.99"))
                        .stockQuantity(80)
                        .category(Category.BOOKS)
                        .build(),

                Product.builder()
                        .name("Instant Pot Duo 7-in-1")
                        .description("Multi-use pressure cooker, slow cooker, rice cooker, steamer, sauté pan, yogurt maker, and warmer — all in one appliance.")
                        .price(new BigDecimal("89.95"))
                        .stockQuantity(60)
                        .category(Category.HOME_AND_KITCHEN)
                        .build(),

                Product.builder()
                        .name("Yoga Mat Pro — 6mm Non-Slip")
                        .description("Extra-thick non-slip yoga mat with alignment lines, carrying strap, and sweat-resistant surface for home or studio use.")
                        .price(new BigDecimal("45.00"))
                        .stockQuantity(180)
                        .category(Category.SPORTS)
                        .build(),

                Product.builder()
                        .name("CeraVe Moisturising Cream 340g")
                        .description("Developed with dermatologists, this non-greasy moisturising cream contains three essential ceramides and hyaluronic acid for 24-hour hydration.")
                        .price(new BigDecimal("18.49"))
                        .stockQuantity(400)
                        .category(Category.BEAUTY)
                        .build(),

                Product.builder()
                        .name("LEGO Technic Bugatti Chiron")
                        .description("Precision-engineered 3,599-piece LEGO Technic set replicating the iconic Bugatti Chiron, including a W16 engine with moving pistons.")
                        .price(new BigDecimal("349.00"))
                        .stockQuantity(35)
                        .category(Category.TOYS)
                        .build(),

                Product.builder()
                        .name("Apple MacBook Air M3")
                        .description("Thin and light laptop featuring the Apple M3 chip with an 8-core CPU, 10-core GPU, up to 18 hours of battery life, and a stunning Liquid Retina display.")
                        .price(new BigDecimal("1099.00"))
                        .stockQuantity(45)
                        .category(Category.ELECTRONICS)
                        .build(),

                Product.builder()
                        .name("Organic Arabica Ground Coffee 1kg")
                        .description("Single-origin, 100% organic Arabica coffee beans slow-roasted and ground to a medium roast. Rich, smooth flavour with chocolate and hazelnut notes.")
                        .price(new BigDecimal("22.99"))
                        .stockQuantity(300)
                        .category(Category.GROCERY)
                        .build(),

                Product.builder()
                        .name("The Pragmatic Programmer 20th Anniversary Edition")
                        .description("Revised and updated for modern software development, covering topics from personal responsibility to career development and architectural best practices.")
                        .price(new BigDecimal("39.99"))
                        .stockQuantity(95)
                        .category(Category.BOOKS)
                        .build()
        );

        productRepository.saveAll(products);
    }

    // -------------------------------------------------------------------------
    // Security context helpers — required so @CreatedBy / @LastModifiedBy
    // are populated by AuditAwareImplementation during the seed inserts
    // -------------------------------------------------------------------------

    private void setSystemSecurityContext() {
        var systemToken = new UsernamePasswordAuthenticationToken(
                "SYSTEM",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_SYSTEM"))
        );
        SecurityContextHolder.getContext().setAuthentication(systemToken);
    }

    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}