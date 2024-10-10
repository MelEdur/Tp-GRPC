package com.unla.tp.service;

import com.unla.tp.entity.Topic;
import com.unla.tp.repository.ITopicRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final   SolicitudesService solicitudesService;
    private final ITopicRepository topicRepository;


    //Función para crear un un consumer de un topic ingresado
    public void addConsumer(String topic){
        //Establecer configuración
        Map<String,Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG,"default");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        //Crear consumer con esa config

        ConsumerFactory<String,String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProps = new ContainerProperties(topic);


        //Maneja los mensajes recibidos
        containerProps.setMessageListener(new MessageListener<String,String>() {

            private String codigo;

            @Override
            public void onMessage(ConsumerRecord<String, String> data) {
                //Separo el topic del mensaje en código y topic ej. /AC035/solicitudes ==> "AC035","solicitudes"
                String[] topicRecibido = data.topic().split("_");
                String codigoTienda = topicRecibido[topicRecibido.length - 2];
                //Decido qué hacer en base al topic
                switch (topicRecibido[topicRecibido.length - 1]){
                    case "solicitudes":
                        solicitudesService.actualizarSolicitud(codigoTienda, data.value());
                        break;
                    case "despacho":
                        solicitudesService.recibirDespacho(codigoTienda, data.value());
                        break;
                }
            }
        });

        ConcurrentMessageListenerContainer<String,String> listenerContainer =
                new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
        listenerContainer.start();
    }

    @PostConstruct
    public void activarConsumers(){
        List<Topic> topics = topicRepository.findAll();

        for(Topic topic : topics){
            this.addConsumer(topic.getTopic());
            System.out.println("CONSUMER PARA TOPIC "+ topic.getTopic());
        }
    }
}
