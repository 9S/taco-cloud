package com.example.tacocloud;

import com.example.tacocloud.Ingredient.Type;
import com.example.tacocloud.orders.Order;
import com.example.tacocloud.orders.OrderRepository;
import com.example.tacocloud.user.User;
import com.example.tacocloud.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository designRepo;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final ArrayList<Ingredient> ingredients;

    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepo, OrderRepository orderRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.designRepo = designRepo;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
    }

    @GetMapping
    public String showDesignForm(Model model) {
        Type[] types = Type.values();

        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(Model model,
                                @Valid Taco taco,
                                @ModelAttribute Order order,
                                Errors errors) {
        if (errors.hasErrors()) {
            Type[] types = Type.values();
            for (Type type : types) {
                model.addAttribute(type.toString().toLowerCase(),
                        filterByType(ingredients, type));
            }
            return "design";
        }

        var newTaco = designRepo.save(taco);
        order.addDesign(newTaco);
        log.info("Processing design: {}", taco);
        return "redirect:/orders/current";
    }

    public List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
}
