package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AccountService {

    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public List<Account> findAllAccount(){

        return accountRepository.findAll();

    }

    public void addAccount(int userId , Account account){
        User user = authRepository.findUserById(userId);
        if(!user.getRole().equalsIgnoreCase("CUSTOMER")){
            throw new ApiException("you are not a customer");
        }
        Customer customer = customerRepository.findCustomerById(user.getId());
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void updateAccount(int userId, Account newAccount , int accountId ){
        Account oldAccount = accountRepository.findAccountById(accountId);
        if(oldAccount==null){
            throw new ApiException("account not found");
        }
        if(oldAccount.getCustomer().getId() != userId){
            throw new ApiException("invalid account");
        }
        oldAccount.setAccountNumber(newAccount.getAccountNumber());
        oldAccount.setActive(newAccount.isActive());
        oldAccount.setBalance(newAccount.getBalance());
        accountRepository.save(oldAccount);
    }

    public void deleteAccount(int userId , int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("account not found");
        }
        if(account.getCustomer().getId() != userId){
            throw new ApiException("invalid account");
        }
        Customer customer = account.getCustomer();
        customer.getAccounts().remove(account);
        accountRepository.delete(account);
    }

    public void activeBankAccount(int userId , int accountId){
        Account account1 = accountRepository.findAccountById(accountId);
        if(account1==null){
            throw new ApiException("account not found");
        }
        if(account1.isActive()==true ){
            throw new ApiException("account is already active");
        }
        if( account1.getCustomer().getId() != userId ){
            throw new ApiException("Account id mismatch");
        }

        account1.setActive(true);
        accountRepository.save(account1);
    }

    public Account viewAccountDetails(int userId , int accountId){
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("account not found");
        }
        if(account.getCustomer().getId() !=  userId ){
            throw new ApiException("account id mismatch");
        }
        return account;
    }

    public List<Account> getUserAccounts(int userId){
        List<Account> accounts = new ArrayList<>();
        Customer customer = customerRepository.findCustomerById(userId);

        for(Account account : customer.getAccounts()){
            accounts.add(account);
        }
        return accounts;
    }

    public void depositAccount(int userId , int accountId, int amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("account not found");
        }
        if(account.isActive()==false ){
            throw new ApiException("account not Active");
        }
        if(account.getCustomer().getId() != userId){
            throw new ApiException("account id mismatch");
        }
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);
    }

    public void withdrawAccount(int userId , int accountId, int amount){
        Account account = accountRepository.findAccountById(accountId);
        if(account==null){
            throw new ApiException("account not found");
        }
        if(account.isActive()==false ){
            throw new ApiException("account not Active");
        }
        if(account.getCustomer().getId() != userId){
            throw new ApiException("account id mismatch");
        }
        if(account.getBalance()<amount){
            throw new ApiException("insufficient balance");
        }
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    public void transferAccount(int userId , int accountId1 , int accountId2 , int amount){
        Account account1 = accountRepository.findAccountById(accountId1);
        Account account2 = accountRepository.findAccountById(accountId2);

        if(account1==null || account2==null){
            throw new ApiException("account not found");
        }
        if(account1.isActive()==false || account2.isActive()==false){
            throw new ApiException("account not Active");
        }
        if(account1.getCustomer().getId() != userId){
            throw new ApiException("account id mismatch");
        }

        if(account1.getBalance()<amount){
            throw new ApiException("insufficient balance");
        }

        account1.setBalance(account1.getBalance()-amount);
        account2.setBalance(account2.getBalance()+amount);
        accountRepository.save(account1);
        accountRepository.save(account2);

    }


    public void blockBankAccount(int userId , int accountId){
        Account account1 = accountRepository.findAccountById(accountId);
        if(account1==null){
            throw new ApiException("account not found");
        }
        if( account1.getCustomer().getId() != userId ){
            throw new ApiException("Account id mismatch");
        }

        account1.setActive(false);
        accountRepository.save(account1);
    }




}
