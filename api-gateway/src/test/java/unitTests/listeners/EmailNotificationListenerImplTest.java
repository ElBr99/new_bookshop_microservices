package unitTests.listeners;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ifellow.jschool.ebredichina.enums.ChequeType;
import ru.ifellow.jschool.ebredichina.listener.EmailChequeListener;
import ru.ifellow.jschool.ebredichina.model.Cheque;
import ru.ifellow.jschool.ebredichina.service.impl.EmailNotificationServiceImpl;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailNotificationListenerImplTest {

    @Mock
    private EmailNotificationServiceImpl emailNotificationService;

    @InjectMocks
    private EmailChequeListener emailChequeListener;

    @Test
    void onChequeSaved_OfflineCheque() {

        Cheque cheque = Cheque.builder()
                .id(UUID.randomUUID())
                .chequeType(ChequeType.OFFLINE)
                .build();

        emailChequeListener.onChequeSaved(cheque);

        verify(emailNotificationService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testOnChequeSaved_WithOnlineCheque() {

        Cheque cheque = Cheque.builder()
                .id(UUID.randomUUID())
                .customerEmail("qwerty@mail.ru")
                .chequeType(ChequeType.ONLINE)
                .build();

        String recipient = cheque.getCustomerEmail();
        String subject = "Чек за покупку";
        String body = "html";

        emailChequeListener.onChequeSaved(cheque);

        verify(emailNotificationService).sendEmail(eq(recipient), eq(subject), eq(body));
    }
}
