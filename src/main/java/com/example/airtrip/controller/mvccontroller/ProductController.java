package com.example.airtrip.controller.mvccontroller;

import com.example.airtrip.domain.dto.dtoformvc.BarProductDTO;
import com.example.airtrip.domain.entity.entityformvc.OrderM;
import com.example.airtrip.domain.entity.entityformvc.Product;
import com.example.airtrip.domain.enums.TypeOfWeapon;
import com.example.airtrip.domain.mapper.mvcmapper.ProductMapper;
import com.example.airtrip.repository.OrderRepository;
import com.example.airtrip.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/findAll")
    public String getAll(ModelMap model){
        var products = productRepository.findAll()
                .stream()
                .map(ProductMapper::entityToDTO)
                .toList();
        model.addAttribute("listProducts", products);
        model.addAttribute("ratingProducts", findAll());
        return "productIndex";
    }
    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("id") Long id) {
        var country = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " was not found"));
        productRepository.delete(country);
        return "redirect:/product/findAll";
    }
    @GetMapping("/create")
    public String create(ModelMap modelMap){
        modelMap.addAttribute("types", TypeOfWeapon.values());
        return "create_product";
    }
    @PostMapping("/create")
    public String submitCreate(@RequestParam("name") String name,
                               @RequestParam("type") TypeOfWeapon typeOfWeapon,
                               @RequestParam("flag") MultipartFile file) throws IOException {
       var product = Product.builder()
               .name(name)
               .typeOfWeapon(typeOfWeapon)
               .image(file.getBytes())
               .build();
        productRepository.saveAndFlush(product);
        return "redirect:/product/findAll";
    }
    @GetMapping("/editProduct")
    public String editCountry(@RequestParam("id") Long id,
                              ModelMap modelMap) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " was not found"));
        var productDTO = ProductMapper.entityToDTO(product);
        modelMap.addAttribute("dto", productDTO);
        modelMap.addAttribute("types", TypeOfWeapon.values());
        return "edit_product";
    }
    @Transactional
    @PostMapping("/updateProduct")
    public String submitEditCountry(@RequestParam("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("typeOfWeapon") TypeOfWeapon weapon,
                                    @RequestParam("flag") MultipartFile multipartFile) throws IOException {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with id " + id + " was not found"));
        product.setName(name);
        product.setTypeOfWeapon(weapon);
        if(multipartFile.getBytes().length!=0){
            product.setImage(multipartFile.getBytes());
        }
        return "redirect:/product/findAll";
    }
    private List<BarProductDTO> findAll(){
        var products = productRepository.findAll()
                .stream()
                .map(ProductMapper::entityToDTO)
                .toList();
        var list = new ArrayList<BarProductDTO>();
        var orders = orderRepository.findAll()
                .stream()
                .map(OrderM::getProducts)
                .map(ProductMapper::entityListToDTOList)
                .toList();
        for(var product : products){
            long count = 0;
            for(var order : orders)
            {
                count += order.stream()
                        .filter(e -> e.equals(product))
                        .count();
            }
            list.add(BarProductDTO.builder()
                    .name(product.getName())
                    .number(count)
                    .build());
        }
        return list;
    }
}
