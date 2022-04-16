package az.fintech.unitech.controller;

import az.fintech.unitech.dto.request.LoginRequest;
import az.fintech.unitech.dto.request.RegisterRequest;
import az.fintech.unitech.dto.response.CustomerResponse;
import az.fintech.unitech.dto.response.LoginResponse;
import az.fintech.unitech.dto.response.RegisterResponse;
import az.fintech.unitech.service.CustomerService;
import az.fintech.unitech.service.LoginService;
import az.fintech.unitech.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final LoginService service;
    private final RegisterService registerService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerService.registerCustomer(request));
    }

    @GetMapping("getAll")
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }


}
