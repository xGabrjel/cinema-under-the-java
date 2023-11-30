package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    private void sendEmailWithAttachment(String email, ResourceBundle resourceBundle, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

        messageHelper.setTo(email);
        messageHelper.setSubject(resourceBundle.getString("emailPdf.subject"));
        String text = resourceBundle.getString("emailPdf.message");
        messageHelper.setText(text, true);

        messageHelper.addAttachment("Ticket.pdf", new ByteArrayResource(pdfBytes));

        javaMailSender.send(message);
    }

    private void addLogoToDocument(Document doc) throws DocumentException, IOException {
        String logoPath = "classpath:static/images/CinemaUnderTheJavaLogo.png";
        Image logo = Image.getInstance(logoPath);
        logo.scaleToFit(600, 200);
        logo.setSpacingBefore(0);
        logo.setAlignment(Element.ALIGN_CENTER);
        doc.add(logo);
    }

    private void addTitleToDocument(Document doc, TicketEntity ticket) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 25, Font.NORMAL, BaseColor.BLACK);
        Paragraph title = new Paragraph(ticket.getFilmTitle(), font);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
    }

    private void addTicketDetailsToDocument(Document doc, ResourceBundle resourceBundle, TicketEntity ticket, String email) throws DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 20, Font.NORMAL, BaseColor.BLACK);
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.date"), ticket.getProjectionDate()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.time"), ticket.getProjectionTime()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.name"), ticket.getName()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.roomNumber"), ticket.getRoomNumber()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.row"), ticket.getRowNumber()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.seat"), ticket.getSeatInRow()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.ticketType"), ticket.getTicketType()), font));
        doc.add(new Paragraph(String.format("%s%s %s", resourceBundle.getString("ticket.ticketPrice"), ticket.getTicketPrice(), ticket.getTicketCurrency()), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.email"), email), font));
        doc.add(new Paragraph(String.format("%s%s", resourceBundle.getString("ticket.purchaseDate"), LocalDate.now()), font));
    }

    private void addQRCodeToDocument(Document doc, String email, TicketEntity ticket) throws DocumentException, IOException {
        Image qrCodeImage = (Image) qr.generateQuickResponseCode(email, ticket);
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        qrCodeImage.scaleToFit(280, 280);
        doc.add(qrCodeImage);
    }
}
