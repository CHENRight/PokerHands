import java.lang.management.PlatformLoggingMXBean;
import java.util.*;
import java.util.stream.Collectors;

public class CalculateValue {

    public static PokerCardValue getCardValue(List<String> card) {
        if (isStraightFlush(card)) {
            return PokerCardValue.STRAIGHT_FLUSH;
        } else if (isFourOfKind(card)) {
            return PokerCardValue.FOUR_OF_A_KIND;
        } else if (isFullHouse(card)) {
            return PokerCardValue.FULL_HOUSE;
        } else if (isFlush(card)) {
            return PokerCardValue.FLUSH;
        } else if (isStraight(card)) {
            return PokerCardValue.STRAIGHT;
        } else if (isHasThreeSameKind(card)) {
            return PokerCardValue.THREE_OF_A_KIND;
        } else if (isHasManyPair(card)) {
            return PokerCardValue.MANY_PAIR;
        } else if (isHasOnePair(card)) {
            return PokerCardValue.PAIR;
        }
        return PokerCardValue.HIGH_POINT;
    }

    static Boolean isHasOnePair(List<String> card) {
        List<Integer> cardNumber = getCardNumber(card);
        long count = cardNumber.stream().distinct().count();
        return count != cardNumber.size();
    }

    static Boolean isHasManyPair(List<String> card) {
        Set<Character> hashSet = new HashSet<>();
        for (String temp : card) {
            hashSet.add(temp.charAt(0));
        }
        return hashSet.size() <= 3;
    }

    static Boolean isHasThreeSameKind(List<String> card) {
        HashMap<Character, Integer> map = getSingleCardCount(card);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 2) {
                return true;
            }
        }
        return false;
    }

    private static HashMap<Character, Integer> getSingleCardCount(List<String> card) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < card.size(); i++) {
            if (map.containsKey(card.get(i).charAt(0))) {
                int temp = map.get(card.get(i).charAt(0));
                map.put(card.get(i).charAt(0), temp + 1);
            } else {
                map.put(card.get(i).charAt(0), 1);
            }
        }
        return map;
    }

    public static boolean isStraight(List<String> card) {
        if (card.size() != 5) {
            return false;
        }
        List<Integer> cardNumber = getCardNumber(card).stream().sorted().collect(Collectors.toList());
        int min = cardNumber.get(0);
        for (int i = 0; i < cardNumber.size(); i++) {
            if (cardNumber.contains(min)) {
                min++;
            } else {
                return false;
            }
        }
        return true;
    }

    public static boolean isFlush(List<String> card) {
        List<Character> cardColor = getCardColor(card);
        Set<Character> set = new HashSet<>(cardColor);
        return set.size() == 1 && cardColor.size() == 5;
    }

    public static boolean isFullHouse(List<String> card) {
        List<Integer> cardNumber = getCardNumber(card);
        Set<Integer> set = new HashSet<>(cardNumber);
        return set.size() == 2 && cardNumber.size() == 5;
    }

    private static boolean isFourOfKind(List<String> card) {
        HashMap<Character, Integer> map = getSingleCardCount(card);
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 3) {
                return true;
            }
        }
        return false;
    }

    private static boolean isStraightFlush(List<String> card) {
        return isStraight(card) && isFlush(card);
    }

    public static Player handleSameLevel(Player player1, Player player2) {
        switch (player1.getCardLevel()) {
            case 1:
                return handleSameHighPoint(player1, player2);
            case 2:
                return handleSamePair(player1, player2);
            case 3:
                return handleSameThreeOfKind(player1, player2);
            case 4:
                return handleSameStraight(player1, player2);
            case 5:
                //handle Same Flush
                return player1;
            case 6:
                return handleSameFullHouse(player1, player2);
            case 7:
                return handleSameFourOfKind(player1, player2);
            case 8:
                return handleSameStraightFlush(player1, player2);
        }
        return player1;
    }

    private static Player handleSameHighPoint(Player player1, Player player2) {
        int sumA = 0, sumB = 0;
        for (int i = 0; i < 5; i++) {
            sumA += getCardNumber(player1.getCard()).get(i);
            sumB += getCardNumber(player2.getCard()).get(i);
        }
        return sumA > sumB ? player1 : player2;
    }

    private static Player handleSamePair(Player player1, Player player2) {
        List<Integer> countListA = getCardNumber(player1.getCard());
        List<Integer> countListB = getCardNumber(player2.getCard());
        int maxPointA = getMaxCount(countListA);
        int maxPointB = getMaxCount(countListB);
        if (maxPointA == maxPointB) {
            return handleSameHighPoint(player1, player2);
        }
        return maxPointA > maxPointB ? player1 : player2;
    }

    private static Player handleSameThreeOfKind(Player player1, Player player2) {
        return compareMaxPoint(player1.getCard(), player2.getCard()) == 1 ? player1 : player2;
    }

    private static Player handleSameStraight(Player player1, Player player2) {
        return compareMaxPoint(player1.getCard(), player2.getCard()) == 1 ? player1 : player2;
    }

    private static Player handleSameFullHouse(Player player1, Player player2) {
        return compareMaxPoint(player1.getCard(), player2.getCard()) == 1 ? player1 : player2;
    }

    private static Player handleSameFourOfKind(Player player1, Player player2) {
        return compareMaxPoint(player1.getCard(), player2.getCard()) == 1 ? player1 : player2;
    }

    private static Player handleSameStraightFlush(Player player1, Player player2) {
        return compareMaxPoint(player1.getCard(), player2.getCard()) == 1 ? player1 : player2;
    }

    private static int compareMaxPoint(List<String> player1Card, List<String> player2Card) {
        List<Integer> countListA = getCardNumber(player1Card);
        List<Integer> countListB = getCardNumber(player2Card);
        int maxPointA = getMaxCount(countListA);
        int maxPointB = getMaxCount(countListB);
        return maxPointA > maxPointB ? 1 : 2;
    }


    private static int getMaxCount(List<Integer> countList) {
        HashMap<Integer, Integer> map = getCountTimesMap(countList);
        Collection<Integer> count = map.values();
        int maxCount = Collections.max(count);
        int maxNumber = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (maxCount == entry.getValue()) {
                maxNumber = entry.getKey() > maxNumber ? entry.getKey() : maxNumber;
            }
        }
        return maxNumber;
    }

    private static HashMap<Integer, Integer> getCountTimesMap(List<Integer> countList) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < countList.size(); i++) {
            if (map.containsKey(countList.get(i))) {
                int temp = map.get(countList.get(i));
                map.put(countList.get(i), temp + 1);
            } else {
                map.put(countList.get(i), 1);
            }
        }
        return map;
    }


    static private List<Integer> getCardNumber(List<String> card) {
        List<Integer> cardNumber = new ArrayList<>();
        for (String temp : card) {
            cardNumber.add((int) (temp.charAt(0)));
        }
        return cardNumber;
    }

    static private List<Character> getCardColor(List<String> card) {
        List<Character> cardNumber = new ArrayList<>();
        for (String temp : card) {
            cardNumber.add((temp.charAt(1)));
        }
        return cardNumber;
    }
}
