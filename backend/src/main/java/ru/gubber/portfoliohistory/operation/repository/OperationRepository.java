package ru.gubber.portfoliohistory.operation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gubber.portfoliohistory.operation.model.Operation;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
}
