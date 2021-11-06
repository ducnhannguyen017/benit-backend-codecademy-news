package com.benit.backend_codecademy_news.config;
import com.benit.backend_codecademy_news.constant.FirebaseConstant;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    @Bean
    public void init() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(FirebaseConstant.BUCKET)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}
