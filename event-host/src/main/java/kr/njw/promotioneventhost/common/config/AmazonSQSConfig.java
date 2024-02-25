package kr.njw.promotioneventhost.common.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.time.Duration;

@Configuration
public class AmazonSQSConfig {
    @Bean
    public SqsMessageListenerContainerFactory<?> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory.builder()
                .configure(sqsContainerOptionsBuilder ->
                        sqsContainerOptionsBuilder
                                .acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
                                .maxDelayBetweenPolls(Duration.ofSeconds(3))
                )
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}
