package com.alex.spring_rest_task.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("phrase")
public class PhraseController {
    private Map<String, String> phrases;

    @GetMapping
    public Map<String, Integer> leaders() {
        Map<String, Integer> leaders = new HashMap<>();

        return leaders;
    }

    @PostMapping("{playerName}", "{phrase}")
    public postPhrase(@PathVariable String playerName, @PathVariable String phrase) {
        if (message.get)
    }

    public static boolean isPalindrome(String str) {
        if (str == null) {
            return false;
        }

        String s = str.trim().toLowerCase();
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) return false;
        }

        return true;
    }
}
