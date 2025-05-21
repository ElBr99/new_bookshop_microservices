package ru.ifellow.jschool.orderservice.listener;

import ru.ifellow.jschool.orderservice.model.Cheque;

public interface ChequeListener {
    void onChequeSaved(Cheque cheque);
}
