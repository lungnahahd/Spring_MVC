package Dev.SpringMVC.web.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/{itemId}")
	public String itemDetail(@PathVariable(name = "itemId") long itemId, Model model) 
	{
		Item nowFindItem = itemRepository.findById(itemId);
		model.addAttribute("item", nowFindItem);
		return "basic/item";
	}
	
	@GetMapping("/add")
	public String addForm() 
	{
		return "basic/addForm";
	}
	
	@PostMapping("/add")
	public String save(@RequestParam(value="itemName") String itemName,
					   @RequestParam(value="price") Integer price,
					   @RequestParam(value="quantity") Integer quantity,
					   Model model) 
	{
		Item item = new Item();
		item.setItemName(itemName);
		item.setPrice(price);
		item.setQuentity(quantity);
		
		itemRepository.save(item);
		
		model.addAttribute("item", item);
		
		return "basic/item";
	}
	
	// 테스트 데이터 생성 
	@PostConstruct
	public void init() 
	{
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
	
	

}
