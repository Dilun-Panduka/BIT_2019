package lk.excellent.pharamacy_management.asset.process.finance.entity.Enum;

public enum InvoicePrintOrNot {
    PRINT("Required"),
    NOT("Not required");

    private final String invoicePrintOrNot;

    InvoicePrintOrNot(String invoicePrintOrNot) {
        this.invoicePrintOrNot = invoicePrintOrNot;
    }

    public String getInvoicePrintOrNot() {
        return invoicePrintOrNot;
    }
}

