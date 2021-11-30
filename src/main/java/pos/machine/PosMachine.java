package pos.machine;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        String receipt = "***<store earning no money>Receipt***\n";

        Map<String, Integer> itemMap = countOccurrence(barcodes);

        Integer total = 0;
        for (String barcode : itemMap.keySet()) {
            ItemInfo item = findByBarcode(barcode);

            if (item == null) {
                continue;
            }

            Integer subtotal = calculateSubtotal(item.getPrice(), itemMap.get(barcode));
            receipt += generateItemReceipt(item, itemMap.get(barcode), subtotal) + "\n";
            total += subtotal;
        }

        receipt += "----------------------\n" +
                    "Total: "+total+" (yuan)\n" +
                    "**********************";

        return receipt;
    }

    public Map<String, Integer> countOccurrence(List<String> barcodeList) {
        return barcodeList.stream().collect(
                Collectors.groupingBy(Function.identity(), LinkedHashMap::new, collectingAndThen(counting(), Long::intValue)));
    }

    public ItemInfo findByBarcode(String barcode) {
        return ItemDataLoader.loadAllItemInfos()
                .stream()
                .filter(item -> Objects.equals(barcode, item.getBarcode()))
                .findFirst()
                .orElse(null);
    }

    public Integer calculateSubtotal(Integer unitPrice, Integer quantity) {
        return unitPrice * quantity;
    }

    public String generateItemReceipt(ItemInfo item, Integer quantity, Integer subtotal) {
        return "Name: "+item.getName()+", Quantity: "+quantity+", Unit price: "+item.getPrice()+" (yuan), Subtotal: "+
                subtotal+" (yuan)";
    }
}
