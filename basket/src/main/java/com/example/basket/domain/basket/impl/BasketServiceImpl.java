package com.example.basket.domain.basket.impl;

import com.example.basket.domain.basket.api.BasketDto;
import com.example.basket.domain.basket.api.BasketService;
import com.example.basket.domain.basket.api.basketitem.BasketItemDto;
import com.example.basket.domain.basket.api.user.UserDto;
import com.example.basket.domain.basket.api.product.ProductDto;
import com.example.basket.domain.basket.impl.basketitem.BasketItem;
import com.example.basket.domain.basket.impl.basketitem.BasketItemServiceImpl;
import com.example.basket.domain.library.AuthCallableFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private static final String authUrl = "http://localhost:8090/auth/users/";
    public final int BASKET_STATUS_NONE = 0;
    public final int BASKET_STATUS_SALED = 1;


    private final AuthCallableFeignClient authCallableFeignClient;

    private final BasketRepository repository;
    private final BasketItemServiceImpl basketItemService;
    private final RestTemplate restTemplate;


    public BasketServiceImpl(AuthCallableFeignClient authCallableFeignClient, BasketRepository basketRepository, BasketItemServiceImpl basketItemService, RestTemplate restTemplate) {
        this.authCallableFeignClient = authCallableFeignClient;
        this.repository = basketRepository;
        this.basketItemService = basketItemService;
        this.restTemplate = restTemplate;
    }

    @Override
    public BasketDto addProductToBasket(BasketDto basketDto) {
        Basket basket = repository.findBasketByUserIdAndStatusEquals(basketDto.getUserId(), BASKET_STATUS_NONE);
        if (basket != null) {
            // Basket Var Senaryosu
            return sepetVarUrunEkle(basket, basketDto);
        } else {
            // Basket yok senaryosu
            return sepetYokYeniSepetOlustur(basketDto);
        }

    }

    private BasketDto sepetYokYeniSepetOlustur(BasketDto basketDto) {
        Basket basket = new Basket();
        UserDto user = getUser(String.valueOf(basketDto.getUserId()));
        basket.setUserId(user.getUserId());
        basket.setStatus(BASKET_STATUS_NONE);
        List<BasketItem> basketItemList = new ArrayList<>();
        ProductDto product = basketItemService.getProductById(String.valueOf(basketDto.getBasketItemList().get(0).getProduct().getProductId()));

        for (BasketItemDto basketItemDto : basketDto.getBasketItemList()) {
            BasketItem basketItem = new BasketItem();
            basketItem.setProductId(product.getProductId());
            basketItem.setCount(basketItemDto.getCount());
            basketItem.setBasketItemAmount(basketItem.getCount() * product.getPrice());
            basketItem.setBasket(basket);
            basketItemList.add(basketItem);
//          basketItemList.add(basketItem);
//          Yukarıdaki kod satırını neden çalıştırmadı?
        }
        basket.setBasketItemList(basketItemList);
        basket.setTotalAmount(basket.getBasketItemList().get(0).getCount() * product.getPrice());

//        basket = repository.save(basket);
        //      basket.setTotalAmount(calculateBasketAmount(basket.getBasketId()));


        basket = repository.save(basket);

        return toDto(basket);
    }

    private BasketDto sepetVarUrunEkle(Basket basket, BasketDto basketDto) {
        List<BasketItem> basketItemList = basket.getBasketItemList();
        // Aşağıdaki kod satırı yerine gelen listeyi for ile dönebilirdik fakat bunu yapmak yerine repository'e metod yazıp oradan var mı yok mu kontrolünü yapmak bestPractice.
        BasketItem basketItem = basketItemService.findBasketItemByBasketIdAndProductId(basket.getBasketId(), basketDto.getBasketItemList().get(0).getProduct().getProductId());

        // Aynı üründen mi ekleme yapmış farklı üründen mi ekleme yapmış

        // Farklı üründen ekleme yapma senaryosu
        if (basketItem == null) {
            System.out.println("Eklenen ürün sepette yok senaryosu");
            BasketItem newBasketItem = new BasketItem();

            // Product product = basketItem.getProduct(); Hoca bunu yazdı yüksek ihtimalle yanlış var!
            ProductDto productDto = basketItemService.getProductById(String.valueOf(basketDto.getBasketItemList().get(0).getProduct().getProductId()));
            newBasketItem.setProductId(productDto.getProductId());
            newBasketItem.setCount(basketDto.getBasketItemList().get(0).getCount());
            newBasketItem.setBasketItemAmount(basketDto.getBasketItemList().get(0).getCount() * productDto.getPrice());
            newBasketItem.setBasket(basket);
//            newBasketItem = basketItemService.save(newBasketItem);
            basketItemList.add(newBasketItem);

        }
        // Ayın üründen ürün  ekleme senaryosu
        else {
            System.out.println("Eklenen ürün sepette var senaryosu");
            System.out.println("liste var mı " + basketDto.getBasketItemList());
            System.out.println("BasketİtemList boş mu" + basketDto.getBasketItemList().get(0).getProduct().getName());
            System.out.println("BasketItem : " + basketItem);
            // Eklenen ürün sepette var
            ProductDto productDto = basketItemService.getProductById(String.valueOf(basketItem.getProductId()));
            basketItem.setProductId(productDto.getProductId());
            basketItem.setCount(basketDto.getBasketItemList().get(0).getCount() + basketItem.getCount());
            basketItem.setBasketItemId(basket.getBasketItemList().get(0).getBasketItemId());
            basketItem.setBasketItemAmount(basketItem.getCount() * productDto.getPrice());

        }

        basket.setTotalAmount(calculateBasketAmount(basket.getBasketId()));
        basket.setBasketItemList(basketItemList);
        repository.save(basket);

        return toDto(basket);

    }

    private double calculateBasketAmount(long basketId) {
        Basket basket = repository.findBasketByBasketId(basketId);
        double totalAmount = 0.0;
        for (BasketItem basketItem : basket.getBasketItemList()) {
            totalAmount += basketItem.getBasketItemAmount();
        }
        return totalAmount;
    }

    @Override
    public BasketDto getBasketByUserId(String userId) {
//        UserDto userDto = restTemplate.getForEntity(authUrl + userId, UserDto.class).getBody();
        UserDto userDto = authCallableFeignClient.getUserById(userId);
        return toDto(repository.findBasketByUserIdAndStatusEquals(userDto.getUserId(), BASKET_STATUS_NONE));
    }

    @Override
    public void removeProductFromBasket(String userId, String basketItemId) {
        UserDto userDto = getUser(userId);
        Basket basket = repository.findBasketByUserIdAndStatusEquals(userDto.getUserId(), BASKET_STATUS_NONE);
        BasketItem basketItem = basketItemService.findBasketEntity(basketItemId);
        basketItemService.delete(basketItem.getBasketItemId());
        basket.setTotalAmount(calculateBasketAmount(basket.getBasketId()));
        basket = repository.save(basket);
    }

    public UserDto getUser(String userId) {
        return restTemplate.getForEntity(authUrl + userId, UserDto.class).getBody();
    }

    // Response'tan önce çalışacak olan metod
    public BasketDto toDto(Basket basket) {
        return BasketDto.builder()
                .basketId(Math.toIntExact(basket.getBasketId()))
                .totalAmount(basket.getTotalAmount())
                .userId(basket.getUserId())
                .status(basket.getStatus())
                .basketItemList(basket.getBasketItemList().stream().map(basketItem -> basketItemService.toDto(basketItem)).collect(Collectors.toList()))
                .build();
    }
}
