package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Component
@AllArgsConstructor
public class EmailTicketSenderUtil {

    private final JavaMailSender javaMailSender;
    private final QuickResponseUtil qr;

    public void sendEmailWithTicket(String email, TicketEntity ticket) throws MessagingException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, outputStream);
            doc.open();

            addLogoToDocument(doc);
            addTitleToDocument(doc, ticket);
            addTicketDetailsToDocument(doc, resourceBundle, ticket, email);
            addQRCodeToDocument(doc, email, ticket);

            doc.close();

            sendEmailWithAttachment(email, resourceBundle, outputStream.toByteArray());
        } catch (IOException | DocumentException ex) {
            log.error("An exception occurred while sending the email with the ticket", ex);
        }
    }

    private void sendEmailWithAttachment(String sendTo, ResourceBundle resourceBundle, byte[] pdfBytes) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        String mailContent = resourceBundle.getString("emailPdf.message");

        messageHelper.setTo(sendTo);
        messageHelper.setFrom(resourceBundle.getString("registration.from"), resourceBundle.getString("registration.personal"));
        messageHelper.setSubject(resourceBundle.getString("emailPdf.subject"));
        messageHelper.setText(mailContent, true);
        messageHelper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }

    private void addLogoToDocument(Document doc) throws DocumentException, IOException {
        String logoPath = "static/images/CinemaUnderTheJavaLogo.PNG";
        ClassPathResource classPathResource = new ClassPathResource(logoPath);
        Image logo = Image.getInstance(classPathResource.getURL());
        logo.setAlignment(Element.ALIGN_CENTER);
        float a4Width = PageSize.A4.getWidth();
        float a4Height = PageSize.A4.getHeight();
        float imageWidthToHeightRatio = 1367f / 545f;
        float desiredHeight = a4Width / imageWidthToHeightRatio;

        if (desiredHeight > a4Height) {
            float desiredWidth = a4Height * imageWidthToHeightRatio;
            logo.scaleAbsolute(desiredWidth, a4Height);
        } else {
            logo.scaleAbsolute(a4Width, desiredHeight);
        }

        logo.setAbsolutePosition(0, a4Height - desiredHeight);
        doc.add(logo);
    }

    private void addTitleToDocument(Document doc, TicketEntity ticket) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 25, Font.NORMAL, BaseColor.BLACK);
        Paragraph title = new Paragraph(ticket.getFilmTitle(), font);
        title.setAlignment(Element.ALIGN_CENTER);
        addEmptyLines(doc, 12);
        doc.add(title);
    }

    private void addTicketDetailsToDocument(Document doc, ResourceBundle resourceBundle, TicketEntity ticket, String email) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.BLACK);

        addEmptyLines(doc, 1);
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.date"), ticket.getProjectionDate()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.time"), ticket.getProjectionTime()), font));
        addEmptyLines(doc, 1);
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.name"), ticket.getName())));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.roomNumber"), ticket.getRoomNumber())));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.row"), ticket.getRowNumber())));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.seat"), ticket.getSeatInRow())));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.ticketType"), ticket.getTicketType())));
        doc.add(new Paragraph(String.format("%s%s %s", resourceBundle.getString("ticket.ticketPrice"), ticket.getTicketPrice(), ticket.getTicketCurrency())));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.email"), email)));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.purchaseDate"), LocalDate.now())));
        addEmptyLines(doc, 2);
    }

    private void addQRCodeToDocument(Document doc, String email, TicketEntity ticket) throws DocumentException, IOException {
        Image qrCodeImage = (Image) qr.generateQuickResponseCode(email, ticket);
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        qrCodeImage.scaleToFit(140, 140);
        doc.add(qrCodeImage);
    }

    private void addEmptyLines(Document doc, int lines) throws DocumentException {
        for (int i = 0; i < lines; i++) {
            doc.add(new Paragraph(" "));
        }
    }
}
