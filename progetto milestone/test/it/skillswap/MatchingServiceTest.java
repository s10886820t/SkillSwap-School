package it.skillswap;

import it.skillswap.domain.MatchResult;
import it.skillswap.domain.SkillLevel;
import it.skillswap.service.MatchingService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

import java.util.List;

public class MatchingServiceTest {

    public static void main(String[] args) {

        it.skillswap.service.SkillSwapService service = new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("anna", "4A", "anna@test.it");
        service.registerStudent("luca", "4A", "luca@test.it");

        service.addOffer("S2", "K1", SkillLevel.ADVANCED, "C avanzato");
        service.addRequest("S1", "K1", SkillLevel.BEGINNER, "aiuto c");

        MatchingService matchingService = new MatchingService(service.getState());
        List<MatchResult> matches = matchingService.findOneWayMatches("S1");

        assert matches.size() == 1;

        MatchResult result = matches.get(0);
        assert result.getMatchedStudent().getId().equals("S2");
        System.out.println("MatchingServiceTest passed!");

    }
}
