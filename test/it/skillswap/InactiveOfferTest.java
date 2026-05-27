package it.skillswap;

import it.skillswap.domain.Offer;
import it.skillswap.domain.SkillLevel;
import it.skillswap.service.MatchingService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

public class InactiveOfferTest {

    public static void main(String[] args) {

        SkillSwapService service
                = new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("Anna", "4A", "anna@test.it");
        service.registerStudent("Luca", "4A", "luca@test.it");

        Offer offer = service.addOffer(
                "S2",
                "K1",
                SkillLevel.ADVANCED,
                "C"
        );

        offer.deactivate();

        service.addRequest("S1", "K1",
                SkillLevel.BEGINNER, "Aiuto");

        MatchingService matchingService = new MatchingService(service.getState());

        assert matchingService
                .findOneWayMatches("S1").isEmpty();

        System.out.println("InactiveOfferTest passed!");
    }
}
