package ru.ifellow.jschool.ebredichina.listener;

import ru.ifellow.jschool.ebredichina.model.Cheque;

public interface ChequeListener {
    void onChequeSaved(Cheque cheque);
}
