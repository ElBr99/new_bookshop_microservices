package ru.ifellow.jschool.ebredichina.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifellow.jschool.ebredichina.model.Cheque;
import ru.ifellow.jschool.ebredichina.repository.ChequeRepository;
import ru.ifellow.jschool.ebredichina.service.ChequeService;

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
