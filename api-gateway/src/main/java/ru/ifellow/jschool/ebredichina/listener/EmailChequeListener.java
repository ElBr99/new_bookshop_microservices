package ru.ifellow.jschool.ebredichina.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.model.Cheque;
import ru.ifellow.jschool.ebredichina.service.impl.EmailNotificationServiceImpl;

@Component
@RequiredArgsConstructor
public class EmailChequeListener implements ChequeListener {

    private final EmailNotificationServiceImpl emailNotificationService;

    @Override
    public void onChequeSaved(Cheque cheque) {
        if (cheque.getChequeType().equals(ChequeType.OFFLINE)) {
            return;
        }

        emailNotificationService.sendEmail(cheque.getCustomerEmail(), "Чек за покупку", "html");
    }
}

