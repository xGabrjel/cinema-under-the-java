package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.TicketEntity;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import lombok.AllArgsConstructor;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@AllArgsConstructor
public class QuickResponseUtil {

    public Element generateQuickResponseCode(String email, TicketEntity ticket) throws BadElementException, IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());

        String content = String.format("%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s\n%s%s %s\n%s%s",
                resourceBundle.getString("ticket.name"), ticket.getName(),
                resourceBundle.getString("ticket.film"), ticket.getFilmTitle(),
                resourceBundle.getString("ticket.date"), ticket.getProjectionDate(),
                resourceBundle.getString("ticket.time"), ticket.getProjectionTime(),
                resourceBundle.getString("ticket.roomNumber"), ticket.getRoomNumber(),
                resourceBundle.getString("ticket.row"), ticket.getRowNumber(),
                resourceBundle.getString("ticket.seat"), ticket.getSeatInRow(),
                resourceBundle.getString("ticket.ticketType"), ticket.getTicketType(),
                resourceBundle.getString("ticket.ticketPrice"), ticket.getTicketPrice(), ticket.getTicketCurrency(),
                resourceBundle.getString("ticket.email"), email);

        ByteArrayOutputStream stream = QRCode.from(content)
                .to(ImageType.PNG)
                .stream();

        return Image.getInstance(stream.toByteArray());
    }
}
