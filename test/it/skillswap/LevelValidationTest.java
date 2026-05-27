package it.skillswap;

import it.skillswap.domain.MatchResult;
import it.skillswap.domain.SkillLevel;
import it.skillswap.service.MatchingService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

import java.util.List;

public class LevelValidationTest {

    public static void main(String[] args) {

        SkillSwapService service =
                new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("Anna", "4A", "anna@test.it");
        service.registerStudent("Luca", "4A", "luca@test.it");

        service.addOffer(
                "S2",
                "K1",
                SkillLevel.ADVANCED,
                "C"
        );

        service.addRequest(
                "S1",
                "K1",
                SkillLevel.BEGINNER,
                "Aiuto"
        );

        MatchingService matchingService =
                new MatchingService(service.getState());

        List<MatchResult> matches =
                matchingService.findOneWayMatches("S1");

        assert matches.size() == 1;

        System.out.println("LevelValidationTest passed!");
    }
}