package com.alex.spring_rest_task.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.compare;

@RestController
@RequestMapping("phrase")
public class PhraseController {
    private int counter = 4;
    private List<Map<String, String>> phrases = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{put("id", "1"); put("player", "player1"); put("phrase", "Anna");}});
        add(new HashMap<String, String>() {{put("id", "2"); put("player", "player2"); put("phrase", "civic");}});
        add(new HashMap<String, String>() {{put("id", "3"); put("player", "player1"); put("phrase", "rotor");}});
    }};

    @GetMapping
    public Map<String, Integer> leaders() {

        Map<String, Integer> leaders = new HashMap<>();
        Integer points;

        for (Map<String, String> map : phrases) {
            if (leaders.get(map.get("player")) != null) {
                points = leaders.get(map.get("player"));
                points += map.get("phrase").length();
                leaders.put(map.get("player"), points);
            } else leaders.put(map.get("player"), map.get("phrase").length());
        }
/*
        Map<String, Integer> leaders = phrases.stream()
                .map(phrase -> new TreeMap<String, Integer>() {{put(phrase.get("player"), phrase.get("phrase").length());}})
                .sorted(Comparator.comparing(p -> ((List<Integer>) p.values()).get(0)))
                .collect(Collectors.toMap(Function.identity()); */
        return leaders;
    }

    @PostMapping
    public Map<String, Integer> postPhrase(@RequestParam String playerName, @RequestParam String phrase) {
        if (isPalindrome(phrase) && isUnique(phrase)) {
            phrases.add(new HashMap<String, String>() {{put("id", String.valueOf(counter)); put("player", playerName); put("phrase", phrase);}});
            return new HashMap<String, Integer>() {{put("id", counter++); put("points", phrase.length());}};
        } else return new HashMap<String, Integer>() {{put("id", 0); put("points", 0);}};
    }

    public boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String s = str.trim().toLowerCase();
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }

        return true;
    }

    public boolean isUnique(String str) {
        if (str == null) {
            return false;
        }

        for (Map<String, String> map : phrases) {
            if (map.get("phrase").equals(str)) return false;
        }

        return true;
    }
}
