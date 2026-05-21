package it.skillswap.storage;

import it.skillswap.domain.*;
import java.io.*;
import java.time.LocalDateTime;

public class FileStorage implements Storage {

    private static final String DATA_DIR = "data";

    @Override
    public SkillSwapState load() {
        SkillSwapState state = new SkillSwapState();

        loadStudents(state);
        loadSkills(state);
        loadOffers(state);
        loadRequests(state);
        loadExchanges(state);
        loadReviews(state);

        return state;
    }

    @Override
    public void save(SkillSwapState state) {
        saveStudents(state);
        saveSkills(state);
        saveOffers(state);
        saveRequests(state);
        saveExchanges(state);
        saveReviews(state);
    }

    private void loadStudents(SkillSwapState state) {
        File file = new File(DATA_DIR + "/students.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";" , -1);

                Student student = new Student(
                        parts[0],
                        parts[1],
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4]),
                        Integer.parseInt(parts[5])
                );

                state.getStudents().put(student.getId(), student);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento students.csv");
        }
    }

    private void loadSkills(SkillSwapState state) {
        File file = new File(DATA_DIR + "/skills.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";" , -1);

                Skill skill = new Skill(
                        parts[0],
                        parts[1],
                        SkillCategory.valueOf(parts[2])
                );

                state.getSkills().put(skill.getId(), skill);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento skills.csv");
        }
    }

    private void loadOffers(SkillSwapState state) {
        File file = new File(DATA_DIR + "/offers.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";" , -1);

                Student student = state.getStudents().get(parts[1]);
                Skill skill = state.getSkills().get(parts[2]);

                Offer offer = new Offer(
                        parts[0],
                        student,
                        skill,
                        SkillLevel.valueOf(parts[3]),
                        parts[4],
                        Boolean.parseBoolean(parts[5])
                );

                state.getOffers().put(offer.getId(), offer);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento offers.csv");
        }
    }

    private void loadRequests(SkillSwapState state) {
        File file = new File(DATA_DIR + "/requests.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                Student student = state.getStudents().get(parts[1]);
                Skill skill = state.getSkills().get(parts[2]);

                Request request = new Request(
                        parts[0],
                        student,
                        skill,
                        SkillLevel.valueOf(parts[3]),
                        parts[4]
                );

                state.getRequests().put(request.getId(), request);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento requests.csv");
        }
    }

    private void loadExchanges(SkillSwapState state) {
        File file = new File(DATA_DIR + "/exchanges.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";" , -1);

                Offer offer = state.getOffers().get(parts[1]);
                Request request = state.getRequests().get(parts[2]);

                LocalDateTime closedAt = null;

                if (!parts[5].isBlank()) {
                    closedAt = LocalDateTime.parse(parts[5]);
                }

                Exchange exchange = new Exchange(
                        parts[0],
                        offer,
                        request,
                        ExchangeStatus.valueOf(parts[3]),
                        LocalDateTime.parse(parts[4]),
                        closedAt
                );

                state.getExchanges().put(exchange.getId(), exchange);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento exchanges.csv");
        }
    }

    private void loadReviews(SkillSwapState state) {
        File file = new File(DATA_DIR + "/reviews.csv");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";" , -1);

                Exchange exchange = state.getExchanges().get(parts[1]);
                Student reviewer = state.getStudents().get(parts[2]);
                Student reviewee = state.getStudents().get(parts[3]);

                Review review = new Review(
                        parts[0],
                        exchange,
                        reviewer,
                        reviewee,
                        Integer.parseInt(parts[4]),
                        parts[5],
                        LocalDateTime.parse(parts[6])
                );

                state.getReviews().put(review.getId(), review);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricamento reviews.csv");
        }
    }

    private void saveStudents(SkillSwapState state) {
        writeFile("students.csv", state.getStudents().values().stream()
                .map(s -> s.getId() + ";" +
                        s.getName() + ";" +
                        s.getStudentClass() + ";" +
                        s.getEmail() + ";" +
                        s.getRatingAvg() + ";" +
                        s.getRatingCount())
                .toList());
    }

    private void saveSkills(SkillSwapState state) {
        writeFile("skills.csv", state.getSkills().values().stream()
                .map(s -> s.getId() + ";" +
                        s.getName() + ";" +
                        s.getCategory())
                .toList());
    }

    private void saveOffers(SkillSwapState state) {
        writeFile("offers.csv", state.getOffers().values().stream()
                .map(o -> o.getId() + ";" +
                        o.getStudent().getId() + ";" +
                        o.getSkill().getId() + ";" +
                        o.getLevel() + ";" +
                        o.getNote() + ";" +
                        o.isActive())
                .toList());
    }

    private void saveRequests(SkillSwapState state) {
        writeFile("requests.csv", state.getRequests().values().stream()
                .map(r -> r.getId() + ";" +
                        r.getStudent().getId() + ";" +
                        r.getSkill().getId() + ";" +
                        r.getMinLevel() + ";" +
                        r.getNote())
                .toList());
    }

    private void saveExchanges(SkillSwapState state) {
        writeFile("exchanges.csv", state.getExchanges().values().stream()
                .map(e -> e.getId() + ";" +
                        e.getOffer().getId() + ";" +
                        e.getRequest().getId() + ";" +
                        e.getStatus() + ";" +
                        e.getCreatedAt() + ";" +
                        (e.getClosedAt() == null ? "" : e.getClosedAt()))
                .toList());
    }

    private void saveReviews(SkillSwapState state) {
        writeFile("reviews.csv", state.getReviews().values().stream()
                .map(r -> r.getId() + ";" +
                        r.getExchange().getId() + ";" +
                        r.getReviewer().getId() + ";" +
                        r.getReviewee().getId() + ";" +
                        r.getStars() + ";" +
                        r.getComment() + ";" +
                        r.getCreatedAt())
                .toList());
    }

    private void writeFile(String fileName, java.util.List<String> lines) {

        File dir = new File(DATA_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {

            for (String line : lines) {
                pw.println(line);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore scrittura file " + fileName);
        }
    }
}