package it.skillswap;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.ExchangeStatus;
import it.skillswap.domain.SkillLevel;
import it.skillswap.service.ExchangeService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

public class ExchangeServiceTest {

    public static void main(String[] args) {

        it.skillswap.service.SkillSwapService service = new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("anna", "4A", "anna@test.it");
        service.registerStudent("luca", "4A", "luca@test.it");

        service.addOffer("S2", "K1", SkillLevel.ADVANCED, "C");
        service.addRequest("S1", "K1", SkillLevel.BEGINNER, "aiuto");

        ExchangeService exchangeService = new ExchangeService(service.getStorage(), service.getState());
        Exchange exchange = exchangeService.propose("O1", "R1");

        assert exchange.getStatus() == ExchangeStatus.PROPOSED;
        exchangeService.accept(exchange.getId());
        assert exchange.getStatus() == ExchangeStatus.ACCEPTED;
        exchangeService.complete(exchange.getId());
        assert exchange.getStatus() == ExchangeStatus.COMPLETED;
        System.out.println("ExchangeServiceTest passed!");

    }
}
