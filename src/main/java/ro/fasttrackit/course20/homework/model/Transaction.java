package ro.fasttrackit.course20.homework.model;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record Transaction(
        long id,
        String product,
        TransactionType type,
        double amount
) {
}