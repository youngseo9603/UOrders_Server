package com.example.uorders.Service;

import com.example.uorders.domain.Cafe;
import com.example.uorders.domain.Menu;
import com.example.uorders.dto.cartMenu.CartMenuRequest;
import com.example.uorders.dto.menu.CreateMenuRequest;
import com.example.uorders.exception.MenuNotFoundException;
import com.example.uorders.repository.CafeRepository;
import com.example.uorders.repository.MenuRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeService cafeService;

    @Transactional
    public void saveMenu(Menu menu) { menuRepository.save(menu);}

    public List<Menu> findMenus() { return menuRepository.findAll(); }

    public Menu findById(Long menuId) { return menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException(menuId)); }

    @Transactional
    public void createMenu (CreateMenuRequest request){

        Cafe cafe = cafeService.findById(request.getCafeIndex());

        Menu menu = Menu.builder()
                .cafe(cafe)
                .name(request.getMenuName())
                .price(request.getMenuPrice())
                .image(request.getMenuImage())
                .cartMenuSet(new HashSet<>())
                .orderMenuSet(new HashSet<>())
                .sizeSelect(request.isMenuSize())
                .temperatureSelect(request.isMenuTemperature())
                .status(request.getSoldOut())
                .build();



    }
}
