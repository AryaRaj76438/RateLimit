package com.quantumsyntax.slidingwindow.controller;

import com.quantumsyntax.slidingwindow.model.Calculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @GetMapping("/add")
    public ResponseEntity<Calculator> add(@RequestParam int left, @RequestParam int right) {
        return ResponseEntity.ok(Calculator.builder().operation("add").answer(left + right).build());
    }

    @GetMapping("/subtract")
    public ResponseEntity<Calculator> subtract(@RequestParam int left, @RequestParam int right) {
        return ResponseEntity.ok(Calculator.builder().operation("subtract").answer(left - right).build());
    }

    @GetMapping("/multiply")
    public ResponseEntity<Calculator> multiply(@RequestParam int left, @RequestParam int right) {
        return ResponseEntity.ok(Calculator.builder().operation("multiply").answer(left * right).build());
    }
}
