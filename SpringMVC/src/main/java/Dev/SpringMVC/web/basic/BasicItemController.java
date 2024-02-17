package Dev.SpringMVC.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Dev.SpringMVC.domain.item.Item;
import Dev.SpringMVC.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;


@Controller
@RequestMapping("/basic/items")
public class BasicItemController 
{
	private final ItemRepository itemRepository;
	
	@Autowired
	public BasicItemController(ItemRepository itemRepository) 
	{
		this.itemRepository = itemRepository;
	}
	
	@GetMapping
	public String items(Model model) 
	{
		List<Item> items = itemRepository.findAll();
		model.addAttribute("items", items);
		return "basic/items";
	}
	
	@PostConstruct
	public void init() 
	{
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
	
	

}
