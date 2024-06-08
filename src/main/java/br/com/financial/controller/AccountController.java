package br.com.financial.controller;

import br.com.financial.dto.StatusAccount;
import br.com.financial.dto.TotalPayment;
import br.com.financial.model.Account;
import br.com.financial.service.AccountService;
import br.com.financial.util.AccountTypeEnum;
import br.com.financial.util.CsvUtility;
import br.com.financial.util.StatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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
    public Page<Account> findFilterExpirationAndDescriptionAndType(@RequestParam(name = "expiration")  @DateTimeFormat(pattern="yyyy-MM-dd")Date expirationDate,
                                                                   @RequestParam String description,
                                                                   @RequestParam String type,
                                                                   @PageableDefault(sort = {"expirationDate"}) Pageable pageable) {
        return service.filterExpirationAndDescriptionAndType(expirationDate, description, AccountTypeEnum.valueOf(type), pageable);
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

    @PostMapping("/importCsvFile")
    @Operation(summary = "Import accounts from file csv", description = "Import accounts from file csv", tags = {"Account"})
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        var message = "";

        if (CsvUtility.hasCsvFormat(file)) {
            try {
                service.importAccountsCSV(file);
                message = "The file is uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "The file is not upload successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload a csv file.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @PostMapping("/")
    @Operation(summary = "Create one account", description = "Create one account", tags = {"Account"})
    public Account create(@RequestBody Account account) {
        return service.create(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update one account", description = "Update one account", tags = {"Account"})
    public Account update(@PathVariable(value = "id") Long id, @RequestBody Account account) {
        return service.update(id, account);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of account", description = "Update status of account", tags = {"Account"})
    public Account updateStatus(@PathVariable(value = "id") Long id, @RequestBody StatusAccount newStatus) {
        return service.updateStatus(id, StatusEnum.valueOf(newStatus.getStatus()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting one account", description = "Deleting one account", tags = {"Account"})
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}