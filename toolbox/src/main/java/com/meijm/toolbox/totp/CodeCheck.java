package com.meijm.toolbox.totp;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

public class CodeCheck {
    public static void main(String[] args) throws CodeGenerationException, InterruptedException {
        
// 生成密钥key
        String secret = "test";

// 用密钥生成一个TOTP码
        CodeGenerator codeGenerator = new DefaultCodeGenerator(HashingAlgorithm.SHA1, 6);
        TimeProvider timeProvider = new SystemTimeProvider();
        String code = codeGenerator.generate(secret, timeProvider.getTime());

// 验证用户输入的TOTP码是否正确
        DefaultCodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        verifier.setTimePeriod(1);
        verifier.setAllowedTimePeriodDiscrepancy(7);
        boolean successful = verifier.isValidCode(secret, code);
        System.out.println("Secret: " + secret);
        System.out.println("Code: " + code);
        System.out.println("立即验证 Valid: " + successful);
        Thread.sleep(2000L);
        successful = verifier.isValidCode(secret, code);
        System.out.println("2秒后验证 Valid: " + successful);
        Thread.sleep(5000L);
        successful = verifier.isValidCode(secret, code);
        System.out.println("7秒后验证 Valid: " + successful);
        Thread.sleep(5000L);
        successful = verifier.isValidCode(secret, code);
        System.out.println("13秒后验证 Valid: " + successful);

    }
}
