package com.example.project3.Controller;

import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")

public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get-all-account")
    public ResponseEntity getAllAccount(){
        return ResponseEntity.status(200).body(accountService.findAllAccount());
    }

    @PostMapping("/add-account")
    public ResponseEntity addAccount(@AuthenticationPrincipal User user , @Valid @RequestBody Account account){
        int userId = user.getId();
        accountService.addAccount(userId,account);
        return ResponseEntity.status(200).body("account added");
    }

    @PutMapping("/update-account/{accountId}")
    public ResponseEntity updateAccount(@AuthenticationPrincipal User user , @Valid @RequestBody Account account ,@PathVariable int accountId){

        accountService.updateAccount(user.getId(),account,accountId);
        return ResponseEntity.status(200).body("account updated");
    }

    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal User user , @PathVariable int accountId){
        accountService.deleteAccount(user.getId(),accountId);
        return ResponseEntity.status(200).body("account deleted");
    }

    @PutMapping("/activate-account/{accountId}")
    public ResponseEntity activateAccount(@AuthenticationPrincipal User user , @PathVariable int accountId){

        accountService.activeBankAccount(user.getId(),accountId);
        return ResponseEntity.status(200).body("account activated");
    }

    @GetMapping("/view-account-details/{accountId}")
    public ResponseEntity getAccountDetails(@AuthenticationPrincipal User user , @PathVariable int accountId){
        return ResponseEntity.status(200).body(accountService.viewAccountDetails(user.getId(),accountId));
    }

    @GetMapping("/get-user-accounts")
    public ResponseEntity getUserAccounts(@AuthenticationPrincipal User user ) {
        return ResponseEntity.status(200).body(accountService.getUserAccounts(user.getId()));
    }

    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user , @PathVariable int accountId , @PathVariable int amount){
        accountService.depositAccount(user.getId(),accountId,amount);
        return ResponseEntity.status(200).body("money deposited");
    }

    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity withdraw(@AuthenticationPrincipal User user , @PathVariable int accountId , @PathVariable int amount){
        accountService.withdrawAccount(user.getId(),accountId,amount);
        return ResponseEntity.status(200).body("money withdrawn");
    }

    @PutMapping("/transfer/{accountid1}/{accountid2}/{amount}")
    public ResponseEntity transfer(@AuthenticationPrincipal User user , @PathVariable int accountid1 , @PathVariable int accountid2 , @PathVariable int amount){
        accountService.transferAccount(user.getId(),accountid1,accountid2,amount);
        return ResponseEntity.status(200).body("transfer success");
    }

    @PutMapping("/block-account/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user , @PathVariable int accountId){

        accountService.blockBankAccount(user.getId(),accountId);
        return ResponseEntity.status(200).body("account blocked");
    }
}
