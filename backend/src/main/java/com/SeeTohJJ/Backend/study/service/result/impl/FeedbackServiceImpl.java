package com.SeeTohJJ.Backend.study.service.result.impl;

import com.SeeTohJJ.Backend.study.service.result.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private static final int FAST_THRESHOLD_SECONDS = 5;
    private static final int SLOW_THRESHOLD_SECONDS = 20;

    private static final double HIGH_MASTERY = 8;
    private static final double MEDIUM_MASTERY = 5;

    @Override
    public String generateFeedback(boolean isCorrect, int updatedMastery, int timeTaken) {
        logger.info("Starting generateFeedback");

        boolean isFast = timeTaken <= FAST_THRESHOLD_SECONDS;
        boolean isSlow = timeTaken > SLOW_THRESHOLD_SECONDS;

        List<String> pool = getFeedbackPool(isCorrect, updatedMastery, isFast, isSlow);

        int index = ThreadLocalRandom.current().nextInt(pool.size());
        return pool.get(index);
    }

    private List<String> getFeedbackPool(boolean isCorrect, int mastery, boolean isFast, boolean isSlow) {
        if (isCorrect) {
            if (mastery >= HIGH_MASTERY) {
                if (isFast) {
                    return List.of(
                            "Lightning fast and perfectly accurate! You own this topic.",
                            "Instant recall! Flawless execution.",
                            "Blink and you miss it—perfect answer!"
                    );
                } else if (isSlow) {
                    return List.of(
                            "Way to double-check! Your mastery is showing.",
                            "Great job taking your time to ensure accuracy. Nailed it!",
                            "Thorough and accurate—mastery level work."
                    );
                } else {
                    return List.of(
                            "Flawless! Complete topic mastery achieved.",
                            "Outstanding! You know this material inside and out.",
                            "Superb execution—your foundation here is rock solid."
                    );
                }
            } else if (mastery >= MEDIUM_MASTERY) {
                if (isFast) {
                    return List.of(
                            "Quick and correct! Your confidence is really growing.",
                            "Fast response with spot-on reasoning."
                    );
                } else if (isSlow) {
                    return List.of(
                            "Way to think it through! The extra time paid off.",
                            "Great patience. Taking the time to analyze paid dividends."
                    );
                } else {
                    return List.of(
                            "Good job! Solid understanding demonstrated.",
                            "Nice work! You're making steady progress on this concept.",
                            "On the right track! Your knowledge here is solidifying."
                    );
                }
            } else { // Novice (< 0.50)
                if (isSlow) {
                    return List.of(
                            "Patience pays off! Excellent work reasoning through a tough one.",
                            "Great effort sticking with it and pulling out the right answer!"
                    );
                } else {
                    return List.of(
                            "Great start! Every correct answer builds up your foundation.",
                            "Right on! Keep practicing to lock this in.",
                            "Awesome step forward! You're laying a great foundation."
                    );
                }
            }
        } else { // Incorrect
            if (mastery >= HIGH_MASTERY) {
                if (isFast) {
                    return List.of(
                            "Whoa, too fast! You know this—just read carefully next time.",
                            "Careless mistake! Slow down a fraction, your foundation is solid.",
                            "Don't rush! You know the material, just slow down on the read."
                    );
                } else {
                    return List.of(
                            "Minor slip-up! A rare miss, but you still know this material well.",
                            "Tricky wording? Don't sweat it, your mastery is still high.",
                            "Unexpected miss, but don't lose confidence—your grasp is strong."
                    );
                }
            } else if (mastery >= MEDIUM_MASTERY) {
                if (isFast) {
                    return List.of(
                            "A bit rushed! Take a few extra seconds next time to be sure.",
                            "Speed cost you there! Give yourself a moment to re-read."
                    );
                } else {
                    return List.of(
                            "Close effort! A quick review will clear this right up.",
                            "Nice attempt! Learning comes directly from mistakes like these.",
                            "Good trial! Review the core rule here and you'll nail it next time."
                    );
                }
            } else { // Novice (< 0.50)
                if (isFast) {
                    return List.of(
                            "Don't rush it! It's okay to take your time while learning the basics.",
                            "Slow down a bit—learning new concepts takes deliberate thought."
                    );
                } else if (isSlow) {
                    return List.of(
                            "This is a tough concept and you gave it serious thought. Keep at it!",
                            "Great effort working through it. Difficult concepts take multiple exposures."
                    );
                } else {
                    return List.of(
                            "Better luck next time! Learning takes repetition.",
                            "Keep your head up! Practice is exactly how you conquer this.",
                            "Don't lose momentum! Every mistake points to what to review next."
                    );
                }
            }
        }
    }
}
