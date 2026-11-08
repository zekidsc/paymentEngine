package com.designpatter.paymentengine.factory;


import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BankFactoryRegistry {

    private final List<BankToolkitFactory> factories;
    private final Map<String, BankToolkitFactory> factoryMap;

    public BankFactoryRegistry(List<BankToolkitFactory> factories) {
        this.factories = factories;
        this.factoryMap = factories.stream()
                .collect(Collectors.toMap(
                        f -> f.getBankCode().toUpperCase(),
                        Function.identity()
                ));
    }

    public BankToolkitFactory resolveByBin(String bin) {
        return factories.stream()
                .filter(factory -> factory.supportsBin(bin))
                .findFirst()
                .orElseGet(() -> {
                    // Eşleşme yoksa varsayılan ilk aktif fabrikayı döner
                    return factories.stream().findFirst()
                            .orElseThrow(() -> new IllegalStateException("Sistemde aktif hiçbir banka fabrikası bulunamadı."));
                });
    }

    public BankToolkitFactory resolveByBankCode(String bankCode) {
        return Optional.ofNullable(factoryMap.get(bankCode.toUpperCase()))
                .orElseThrow(() -> new IllegalArgumentException("Desteklenmeyen veya pasif banka kodu: " + bankCode));
    }
}
