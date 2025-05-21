package ru.ifellow.jschool.ebredichina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifellow.jschool.ebredichina.model.Cheque;

import java.util.UUID;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, UUID> {

}
