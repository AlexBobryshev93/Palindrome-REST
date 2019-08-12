package com.alex.spring_rest_task.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("phrase")
public class PhraseController {
    private int counter /* = 4*/;
    private int foundId;
    private List<Map<String, String>> phrases = new ArrayList<Map<String, String>>() /* {{
        add(new HashMap<String, String>() {{put("id", "1"); put("player", "player1"); put("phrase", "Anna");}});
        add(new HashMap<String, String>() {{put("id", "2"); put("player", "player2"); put("phrase", "civic");}});
        add(new HashMap<String, String>() {{put("id", "3"); put("player", "player1"); put("phrase", "rotor");}});
    }} */ ;

    @GetMapping
    public Map<String, Integer> leaders() {
        Map<String, Integer> leaders = phrases.stream()
                .map(e -> new HashMap<String, Integer>() {{
                    put(e.get("player"), e.get("phrase")
                        .replaceAll("[^a-zA-Z0-9]", "") // [^a-zA-Z0-9] removes every symbol except letters and numbers
                        .length());
                    }})
                .collect(HashMap::new,
                        (a, b) -> b.forEach((k, v) -> a.merge(k, v, Integer::sum)),
                        Map::putAll);

        return leaders.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // descending
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    @PostMapping
    public Map<String, Integer> postPhrase(@RequestParam String playerName, @RequestParam String phrase) {
        if (isPalindrome(phrase)) {
            if (isUnique(phrase)) {
                phrases.add(new HashMap<String, String>() {{
                    put("id", String.valueOf(++counter));
                    put("player", playerName);
                    put("phrase", phrase);
                }});

                return new HashMap<String, Integer>() {{
                    put("id", counter);
                    put("points", phrase.replaceAll("[^a-zA-Z0-9]", "").length());
                }};

            } else return new HashMap<String, Integer>() {{put("id", foundId); put("points", 0);}}; // if not unique
        }
        else return new HashMap<String, Integer>() {{put("id", 0); put("points", 0);}}; // if not palindrome
    }

    private boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String s = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // [^a-zA-Z0-9] removes every symbol except letters and numbers
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }

        return true;
    }

    private boolean isUnique(String str) {
        if (str == null) {
            return false;
        }

        String s = str.toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // [^a-zA-Z0-9] removes every symbol except letters and numbers
        for (Map<String, String> map : phrases) {
            if (map.get("phrase").toLowerCase().replaceAll("[^a-zA-Z0-9]", "").equals(s)) { // [^a-zA-Z0-9] removes every symbol except letters and numbers
                foundId = Integer.parseInt(map.get("id"));
                return false;
            }
        }

        return true;
    }
}
