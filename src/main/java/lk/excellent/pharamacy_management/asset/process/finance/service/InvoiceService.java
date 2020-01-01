package lk.excellent.pharamacy_management.asset.process.finance.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lk.excellent.pharamacy_management.asset.process.finance.dao.InvoiceDao;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Invoice;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService implements AbstractService<Invoice, Integer> {
    private final InvoiceDao invoiceDao;

    @Autowired
    public InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDao.findAll();
    }

    @Override
    public Invoice findById(Integer id) {
        return invoiceDao.getOne(id);
    }

    @Override
    public Invoice persist(Invoice invoice) {
        return invoiceDao.save(invoice);
    }

    @Override
    public boolean delete(Integer id) {
        invoiceDao.deleteById(id);
        return false;
    }

    @Override
    public List<Invoice> search(Invoice invoice) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Invoice> invoiceExample = Example.of(invoice, matcher);
        return invoiceDao.findAll(invoiceExample);
    }

    public Invoice lastInvoice() {
        return invoiceDao.findFirstByOrderByIdDesc();
    }


    private static final String FILE_NAME = "/invoice.pdf";

    private void commonTableHeader(PdfPCell pdfPCell) {
        pdfPCell.setBorderColor(BaseColor.BLACK);
        pdfPCell.setPaddingLeft(10);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setBackgroundColor(BaseColor.DARK_GRAY);
        pdfPCell.setExtraParagraphSpace(5f);
    }
    public void createPdf(Invoice invoice) {
        Document document = new Document(PageSize.A4, 15, 15, 45, 30);
        try {
            String filePath = FILE_NAME;
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if (!exists) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            document.open();
            Font mainFont = FontFactory.getFont("Arial", 10, BaseColor.BLACK);


            Paragraph paragraph = new Paragraph("Excellent Health Solution \n" +
                    "34/5, Gunananda Road\n" +
                    "Panadura\n" +
                    "TP: 038 2279311 | 071 5868611\n"
                    , mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);


            PdfPTable table = new PdfPTable(4);//column amount
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeader = FontFactory.getFont("Arial", 10, BaseColor.BLACK);
            Font tableBody = FontFactory.getFont("Arial", 9, BaseColor.BLACK);

            float[] columnWidths = {2f, 2f, 2f, 2f};
            table.setWidths(columnWidths);

            PdfPCell name = new PdfPCell(new Paragraph("name", tableHeader));
            commonTableHeader(name);
            table.addCell(name);

            PdfPCell email = new PdfPCell(new Paragraph("email", tableHeader));
            commonTableHeader(email);
            table.addCell(email);

            PdfPCell mobile = new PdfPCell(new Paragraph("mobile", tableHeader));
            commonTableHeader(mobile);
            table.addCell(mobile);

            PdfPCell address = new PdfPCell(new Paragraph("address", tableHeader));
            commonTableHeader(address);
            table.addCell(address);
/*
            for (Employee employee : employees) {
                PdfPCell nameValue = new PdfPCell(new Paragraph(employee.getName(), tableHeader));
                commonTableHeader(nameValue);
                table.addCell(nameValue);

                PdfPCell emailValue = new PdfPCell(new Paragraph(employee.getEmail(), tableHeader));
               commonTableHeader(emailValue);
                table.addCell(emailValue);

                PdfPCell mobileValue = new PdfPCell(new Paragraph(employee.getMobile(), tableHeader));
                commonTableHeader(mobileValue);
                table.addCell(mobileValue);

                PdfPCell addressValue = new PdfPCell(new Paragraph(employee.getAddress(), tableHeader));
                commonTableHeader(addressValue);
                table.addCell(addressValue);
            }*/

            document.add(table);
            document.close();
            writer.close();


        } catch (Exception e) {
            System.out.println("Exception "+ e.toString());
        }
    }

    public List<Invoice> findByGivenDate(LocalDate currentDate, LocalDate currentDate1) {
        return invoiceDao.findByInvoicedAtBetween(currentDate,currentDate1);
    }
}
