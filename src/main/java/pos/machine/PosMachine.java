package pos.machine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        return "";
    }

    public Map<String, Integer> countOccurrence(List<String> barcodeList) {
        return barcodeList.stream().collect(
                Collectors.groupingBy(Function.identity(), collectingAndThen(counting(), Long::intValue)));
    }

    public ItemInfo findByBarcode(String barcode) {
        return null;
    }

    public Integer calculateTotal(List<Integer> subtotalList) {
        return subtotalList.stream().reduce(Integer::sum).orElse(0);
    }

    public Integer calculateSubtotal(Integer unitPrice, Integer quantity) {
        return unitPrice * quantity;
    }

    public String generateItemReceipt(ItemInfo item, Integer quantity) {
        return "Name: "+item.getName()+", Quantity: "+quantity+", Unit price: "+item.getPrice()+" (yuan), Subtotal: "+
                calculateSubtotal(item.getPrice(), quantity)+" (yuan)";
    }
}
