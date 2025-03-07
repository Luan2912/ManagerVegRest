package hoang.luan.store.service;

import hoang.luan.store.model.Invoice;
import hoang.luan.store.model.InvoiceProduct;
import hoang.luan.store.model.Product;
import hoang.luan.store.model.*;
import hoang.luan.store.repository.InvoiceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class InvoiceProductService {
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;
    @Autowired
    private InvoiceService invoiceService;


    public List<InvoiceProduct> getAllInvoiceProduct(Integer id) {
        return invoiceProductRepository.findInvoiceProductsByTableId(id);
    }
    public List<Object[]> countProduct() {
        return invoiceProductRepository.countProduct();
    }


    public void addProduct(Product product, Integer idinvoice, Integer quantity) {
        Invoice invoice = invoiceService.getInvoiceById(idinvoice);
        List<InvoiceProduct> invoiceProducts = invoice.getInvoiceProducts();
        boolean productFound = false;

        if (!productFound) {
            InvoiceProduct invoiceProduct = new InvoiceProduct();
            invoiceProduct.setProduct(product);
            invoiceProduct.setQuantity(quantity);
            invoiceProduct.setInvoice(invoice);
            invoiceProductRepository.save(invoiceProduct);
        }
    }

    public void removeProduct(Product product, Integer idtable) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findInvoiceProductsByTableId(idtable);
        for (InvoiceProduct c : invoiceProducts) {
            if (c.getProduct().getId().equals(product.getId())) {
                invoiceProductRepository.delete(c);
                break;
            }
        }
    }
    public void updateProduct(Product product, Integer idtable , Integer quantity) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findInvoiceProductsByTableId(idtable);
        for (InvoiceProduct c : invoiceProducts) {
            if (c.getProduct().getId().equals(product.getId())) {
                c.setQuantity(quantity);
                invoiceProductRepository.delete(c);
                break;
            }
        }
    }
    public void removeAllProduct( Integer idtable ) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findInvoiceProductsByTableId(idtable);
        invoiceProductRepository.deleteAll(invoiceProducts);
    }

}
