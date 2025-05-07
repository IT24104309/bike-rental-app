package lk.sliit.bike_rental_api.controller;

import lk.sliit.bike_rental_api.models.DiscountRule;
import lk.sliit.bike_rental_api.service.DiscountRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manage-discount-rule")
public class DocumentRuleController {
    public final DiscountRuleService discountRuleService;

    public DocumentRuleController(DiscountRuleService discountRuleService) {
        this.discountRuleService = discountRuleService;
    }

    //Create Document Rule
    @PostMapping()
    public ResponseEntity<DiscountRule> createDcRule(DiscountRule discountRule) {
        return ResponseEntity.ok(new DiscountRule());
    }

    //Read all
    @GetMapping
    public ResponseEntity<List<DiscountRule>> readAll() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    //Read one
    @GetMapping("/{ruleId}")
    public ResponseEntity<DiscountRule> readOne(String ruleId) {
        return ResponseEntity.ok(new DiscountRule());
    }

    //Update Rule
    @PutMapping
    public ResponseEntity<DiscountRule> updateRule(DiscountRule discountRule) {
        return ResponseEntity.ok(new DiscountRule());
    }

    //Delete Rule
    @DeleteMapping
    public ResponseEntity<String> deleteRule(String ruleId) {
        return ResponseEntity.ok(ruleId);
    }


}
