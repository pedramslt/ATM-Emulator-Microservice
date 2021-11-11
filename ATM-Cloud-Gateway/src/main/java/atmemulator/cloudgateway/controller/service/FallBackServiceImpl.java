package atmemulator.cloudgateway.controller.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FallBackServiceImpl implements FallBackService {

    @Value("${atm.service.fallback.massage}")
    private String atmFallBackMassage;
    @Value("${bank.service.fallback.massage}")
    private String bankFallBackMassage;

    @Override
    public String atmServiceFallBack() {
        return atmFallBackMassage;
    }

    @Override
    public String bankServiceFallBack() {
        return bankFallBackMassage;
    }
}
