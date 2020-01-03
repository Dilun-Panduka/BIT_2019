package lk.excellent.pharamacy_management.asset.process.generalLedger.controller;

import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import lk.excellent.pharamacy_management.asset.process.generalLedger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ledger")
public class LedgerController {

    private final LedgerService ledgerService;

    @Autowired
    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @RequestMapping
    public String LedgerTable(Model model) {
        List<Ledger> ledgers = ledgerService.findAll();
        model.addAttribute("ledgers", ledgers);
        return "ledger/ledger";
    }
}
