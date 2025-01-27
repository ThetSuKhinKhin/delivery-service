package com.example.deliveryservicemanagement.controller;

import com.example.deliveryservicemanagement.ds.Merchant;
import com.example.deliveryservicemanagement.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/merchant")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/save")
    public String saveMerchant (@ModelAttribute("merchant")Merchant merchant,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "dashboard";
        }
        merchantService.saveMerchant(merchant);
        return "redirect:/merchant/findAll";
    }

    @GetMapping("/findById")
    public String findMerchantById(@RequestParam("id")int id, Model model){
        model.addAttribute("merchant", merchantService.findMerchantById(id));
        return "merchantDetails";
    }

    @GetMapping("/findAll")
    public String findAllMerchant(Model model){
        model.addAttribute("merchants",
                merchantService.findAllMerchant());
        return "allMerchant";
    }

    @GetMapping("/delete/{id}")
    public String deleteMerchant(@PathVariable int id){
        merchantService.deleteMerchantById(id);
        return "redirect:/merchant/findAll";
    }

    @PostMapping("update/{id}")
    public String updateMerchantData(@PathVariable int id,
                                     @ModelAttribute("merchant")Merchant merchant,
                                     BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "dashboard";
        }
        merchantService.updateMerchantData(id, merchant);
        return "redirect:/merchant/findAll";
    }

    @GetMapping("/findByName")
    public String findMerchantByName(@RequestParam("name")String name,Model model){
        model.addAttribute("merchantName",
                merchantService.findMerchantByName(name));
        return "merchantDetails";
    }
}
