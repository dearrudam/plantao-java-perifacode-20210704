package com.github.dearrudam.controller;

import com.github.dearrudam.entity.Account;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class AccountControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource("testCreateAccountArgs")
    void testCreateAccount(int expectedResponseCode,
                           String accountName) {

        Account account = new Account(accountName);

        final var response =
            restTemplate.postForEntity(
                UriComponentsBuilder
                    .fromHttpUrl("http://localhost:" + port)
                    .path("accounts")
                    .build()
                    .toUriString(),
                account,
                Account.class
            );

        assertThat(response.getStatusCodeValue()).isEqualTo(expectedResponseCode);

        Account persistedAccount = response.getBody();

        assertThat(persistedAccount).isNotNull();
        assertThat(persistedAccount.getId()).isNotNull();
        assertThat(persistedAccount.getName()).isEqualTo(account.getName());

    }

    static Stream<Arguments> testCreateAccountArgs(){
        return Stream.of(
          Arguments.arguments(
                200,
                "max"
          ),
          Arguments.arguments(
              200,
              "joao"
          ),
          Arguments.arguments(
              200,
              "alice"
          )
        );
    }

}
