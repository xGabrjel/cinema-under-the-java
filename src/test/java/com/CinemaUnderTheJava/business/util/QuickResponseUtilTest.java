package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QuickResponseUtilTest {

    @Mock
    private ResourceBundle resourceBundle;

    @InjectMocks
    private QuickResponseUtil quickResponseUtil;

    @Test
    void generateQuickResponseCodeShouldGenerateQRCode() throws BadElementException, IOException {
        // given
        TicketEntity ticket = EntityFixtures.someTicketEntity();
        String email = "test.test@example.com";

        // when
        Element qrCodeElement = quickResponseUtil.generateQuickResponseCode(email, ticket);

        // then
        assertNotNull(qrCodeElement);
    }
}