package ru.ifellow.jschool.orderservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.orderservice.model.Cheque;
import ru.ifellow.jschool.orderservice.repository.ChequeRepository;
import ru.ifellow.jschool.orderservice.service.ChequeService;

@Service
@RequiredArgsConstructor
@Transactional
public class ChequeServiceImpl implements ChequeService {

    private final ChequeRepository chequeRepository;

    @Override
    public void addCheque(Cheque cheque) {
        chequeRepository.save(cheque);
    }

}
