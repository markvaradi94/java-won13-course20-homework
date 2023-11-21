package ro.fasttrackit.course20.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.course20.homework.model.Transaction;
import ro.fasttrackit.course20.homework.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @GetMapping
    List<Transaction> getAll(@RequestParam(required = false) String product,
                             @RequestParam(required = false) String type) {
        return service.filterProducts(product, type);
    }

    @GetMapping("{id}")
    Transaction getById(@PathVariable Integer id) {
        return service.getTransactionById(id);
    }

    @PostMapping
    Transaction postTransaction(@RequestBody Transaction add) {
        return service.addTransaction(add);
    }

    @PutMapping("{id}")
    Transaction replaceTransaction(@PathVariable Integer id, @RequestBody Transaction transaction) {
        return service.replaceTransaction(id, transaction);
    }

    @DeleteMapping("{id}")
    Transaction deleteTransaction(@PathVariable Integer id) {
        return service.deleteTransaction(id);
    }
}
