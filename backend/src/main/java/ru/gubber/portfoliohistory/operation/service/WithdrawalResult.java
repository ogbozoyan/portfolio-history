package ru.gubber.portfoliohistory.operation.service;

import java.util.UUID;

public record WithdrawalResult(UUID uuid, OperationStatus status) {
}
