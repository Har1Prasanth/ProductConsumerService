package com.example.MessageService.Service;

import com.example.MessageService.DTO.ProductEntity;
import com.example.MessageService.Domain.Product;
import com.example.MessageService.Repo.ProductRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private final ProductRepo productRepo;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(Product product) {

        logger.info("Product Details Received is.. " + product);

        ProductEntity entity=new ProductEntity();
        BeanUtils.copyProperties(product,entity);

        ProductEntity productsaved=new ProductEntity();
        ProductEntity productsaved1=new ProductEntity();

    //    productsaved=productRepo.findByProductName(entity.getProductName());

        productsaved1=productRepo.findByProductNameAndPrice(entity.getProductName(), entity.getPrice());



        if(productsaved1==null){
            ProductEntity productEntity = productRepo.save(entity);
            logger.info("Product saved into consumer db");
        }else {
            logger.info("Recieved Product Details from existing db " + productsaved1.toString());
            productsaved1.setQuantity(product.getQuantity()+productsaved1.getQuantity());
            productRepo.save(productsaved1);
            logger.info("Updated DB");

        }


    }
}