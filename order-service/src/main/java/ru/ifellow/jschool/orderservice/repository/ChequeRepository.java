package ru.ifellow.jschool.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifellow.jschool.orderservice.model.Cheque;

import java.util.UUID;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, UUID> {

}
