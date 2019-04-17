package com.telerikacademy.carservice.service;

import com.telerikacademy.carservice.service.contracts.PassayService;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import static org.passay.DictionarySubstringRule.ERROR_CODE;

@Service
public class PassayServiceImpl implements PassayService {
    @Override
    public String generateRandomPassword() {
        PasswordGenerator generator = new PasswordGenerator();
        CharacterRule lowercaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowercaseRule.setNumberOfCharacters(2);

        CharacterRule uppercaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        uppercaseRule.setNumberOfCharacters(2);

        CharacterRule digitsRule = new CharacterRule(EnglishCharacterData.Digit);
        digitsRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule specialCharacters = new CharacterRule(specialChars);
        specialCharacters.setNumberOfCharacters(2);

        return generator.generatePassword(10, specialCharacters, lowercaseRule,
                uppercaseRule, digitsRule);
    }
}
