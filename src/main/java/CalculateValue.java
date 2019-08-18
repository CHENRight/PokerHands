import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;
import java.util.stream.Collectors;

public class CalculateValue {

    public static PokerCardValue getCardValue(List<String> card) {
        if (isFourOfKind(card)) {
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
