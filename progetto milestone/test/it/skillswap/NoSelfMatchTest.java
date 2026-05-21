package it.skillswap;

import it.skillswap.domain.SkillLevel;
import it.skillswap.service.MatchingService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

public class NoSelfMatchTest {

    public static void main(String[] args) {

        SkillSwapService service =
                new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("Anna", "4A", "anna@test.it");

        service.addOffer("S1", "K1",
                SkillLevel.ADVANCED,
                "C");

        service.addRequest("S1", "K1",
                SkillLevel.BEGINNER,
                "Aiuto");

        MatchingService matchingService =
                new MatchingService(service.getState());

        assert matchingService
                .findOneWayMatches("S1")
                .isEmpty();

        System.out.println("NoSelfMatchTest passed!");
    }
}