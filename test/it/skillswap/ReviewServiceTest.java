package it.skillswap;

import it.skillswap.domain.Exchange;
import it.skillswap.domain.Review;
import it.skillswap.domain.SkillLevel;
import it.skillswap.service.ExchangeService;
import it.skillswap.service.ReviewService;
import it.skillswap.service.SkillSwapService;
import it.skillswap.storage.InMemoryStorage;

public class ReviewServiceTest {

    public static void main(String[] args) {

        SkillSwapService service =
                new SkillSwapService(new InMemoryStorage());

        service.seedSkills();

        service.registerStudent("Anna", "4A", "anna@test.it");
        service.registerStudent("Luca", "4A", "luca@test.it");

        service.addOffer("S2", "K1",
                SkillLevel.ADVANCED,
                "C");

        service.addRequest("S1", "K1",
                SkillLevel.BEGINNER,
                "Aiuto");

        ExchangeService exchangeService =
                new ExchangeService(
                        service.getStorage(),
                        service.getState()
                );

        Exchange exchange =
                exchangeService.propose("O1", "R1");

        exchangeService.accept(exchange.getId());
        exchangeService.complete(exchange.getId());

        ReviewService reviewService =
                new ReviewService(
                        service.getStorage(),
                        service.getState()
                );

        Review review =
                reviewService.addReview(
                        exchange.getId(),
                        "S1",
                        5,
                        "Ottimo"
                );

        assert review.getStars() == 5;

        System.out.println("ReviewServiceTest passed!");
    }
}