package com.demo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowDefinition;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.websocket.ServerWebSocketContainer;
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
public class EchoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoServiceApplication.class, args);
    }

    @Configuration
    @RestController
    public static class WebSocketIntegration {

        @Bean
        ServerWebSocketContainer serverWebSocketContainer() {
            return new ServerWebSocketContainer("/messages").withSockJs();
        }

        @Bean
        MessageHandler webSocketOutboundAdapter() {
            return new WebSocketOutboundMessageHandler(serverWebSocketContainer());
        }

        @Bean(name = "webSocketFlow.input")
        MessageChannel requestChannel() {
            return new DirectChannel();
        }

        @Bean
        IntegrationFlow webSocketFlow(EchoService echoService) {
            return (IntegrationFlowDefinition<?> integrationFlowDefinition) -> {
                Function<String, Object> splitter = (String messagePayload) -> {
                    String echo = echoService.echo(messagePayload);

                    return serverWebSocketContainer()
                            .getSessions()
                            .keySet()
                            .stream()
                            .map(s -> MessageBuilder.withPayload(echo)
                                    .setHeader(SimpMessageHeaderAccessor.SESSION_ID_HEADER, s)
                                    .build())
                            .collect(Collectors.toList());
                };
                integrationFlowDefinition.split(String.class, String.valueOf(splitter))
                        .channel(c -> c.executor(Executors.newCachedThreadPool()))
                        .handle(webSocketOutboundAdapter());
            };
        }

        @RequestMapping("/echo")
        void send(@RequestParam String name) {
            requestChannel().send(MessageBuilder.withPayload(name).build());
        }
    }

    @Configuration
    public static class AmqpIntegration {

        @Value("${echo.amqp.queue:echo}")
        private String echoQueueAndExchangeName;

        @Bean
        InitializingBean prepareQueue(AmqpAdmin amqpAdmin) {
            return () -> {
                Queue queue = new Queue(this.echoQueueAndExchangeName, true);
                DirectExchange exchange = new DirectExchange(this.echoQueueAndExchangeName);
                Binding binding = BindingBuilder.bind(queue).to(exchange).with(this.echoQueueAndExchangeName);
                amqpAdmin.declareQueue(queue);
                amqpAdmin.declareExchange(exchange);
                amqpAdmin.declareBinding(binding);
            };
        }

        @Bean
        IntegrationFlow amqpReplyFlow(ConnectionFactory rabbitConnectionFactory, EchoService echoService) {
            return IntegrationFlows.from(Amqp.inboundGateway(rabbitConnectionFactory, echoQueueAndExchangeName))
                    .transform(String.class, echoService::echo)
                    .get();
        }

        @Bean
        CommandLineRunner client(RabbitTemplate template) {
            return args -> Arrays.asList("Josh", "Sergii").forEach(
                    n -> System.out.println("template response: " + template.convertSendAndReceive(echoQueueAndExchangeName, n)));
        }
    }
}
