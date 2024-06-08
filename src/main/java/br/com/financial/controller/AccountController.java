package br.com.financial.controller;

import br.com.financial.dto.StatusAccount;
import br.com.financial.dto.TotalPayment;
import br.com.financial.model.Account;
import br.com.financial.service.AccountService;
import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Account", description = "Endpoints for managing account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/")
    @Operation(summary = "Finds all accounts", description = "Finds all accounts", tags = {"Account"})
    public Page<Account> findAll(@PageableDefault(sort = {"accountType", "expirationDate"})Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find one account", description = "Find one account", tags = {"Account"})
    public Account findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/filter")
    @Operation(summary = "Find the accounts for expiration date and description",
               description = "Find the accounts for expiration date and description",
               tags = {"Account"})
    public List<Account> findFilterExpirationAndDescriptionAndType(@RequestParam(name = "expiration")  @DateTimeFormat(pattern="yyyy-MM-dd")Date expirationDate,
                                                                   @RequestParam String description,
                                                                   @RequestParam String type) {
        return service.filterExpirationAndDescriptionAndType(expirationDate, description, AccountTypeEnum.valueOf(type));
    }

    @GetMapping("/getFullPaid")
    @Operation(summary = "Get the amount paid in a period",
            description = "Get the amount paid in a period",
            tags = {"Account"})
    public TotalPayment findByPaymentDate(@RequestParam(name = "paymentDateStart")  @DateTimeFormat(pattern="yyyy-MM-dd")Date paymentDateStart,
                                          @RequestParam(name = "paymentDateEnd")  @DateTimeFormat(pattern="yyyy-MM-dd")Date paymentDateEnd,
                                          @RequestParam String type) {
        var amount = service.findByPaymentDate(paymentDateStart, paymentDateEnd, AccountTypeEnum.valueOf(type));
        var totalPayment = new TotalPayment();
        totalPayment.setAmount(amount);
        return totalPayment;

    }

    @PostMapping("/")
    @Operation(summary = "Create one account", description = "Create one account", tags = {"Account"})
    public Account create(@RequestBody Account account) throws Exception{
        return service.create(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update one account", description = "Update one account", tags = {"Account"})
    public Account update(@PathVariable(value = "id") Long id, @RequestBody Account account) throws Exception {
        return service.update(id, account);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of account", description = "Update status of account", tags = {"Account"})
    public Account updateStatus(@PathVariable(value = "id") Long id, @RequestBody StatusAccount newStatus) throws Exception {
        return service.updateStatus(id, StatusEnum.valueOf(newStatus.getStatus()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting one account", description = "Deleting one account", tags = {"Account"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}