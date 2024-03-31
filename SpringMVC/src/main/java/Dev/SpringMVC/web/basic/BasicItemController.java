package Dev.SpringMVC.web.basic;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String save(@ModelAttribute("item") Item item) 
	{
		// ModelAttribute의 괄호 안 Name 속성으로 넘겨주는 Model 네이밍 지정 가능
		// ModelAttribute 어노테이션으로 Model까지 내부적으로 생성하여 집어 넣음 
		itemRepository.save(item);
		
		return "basic/item";
	}
	
	@GetMapping("/{itemId}/edit")
	public String editForm(@PathVariable(name = "itemId") long itemId, Model model) 
	{
		Item item = itemRepository.findById(itemId);
		model.addAttribute("item", item);
		
		return "basic/editForm";
	}
	
	@PostMapping("/{itemId}/edit")
	public String edit(@PathVariable(name="itemdId") long itemId, @ModelAttribute Item item) 
	{
		itemRepository.update(itemId, item);
		return "redirect:/basci/items/{itemId}";
	}
	
	
	// 테스트 데이터 생성 
	@PostConstruct
	public void init() 
	{
		itemRepository.save(new Item("itemA", 10000, 10));
		itemRepository.save(new Item("itemB", 20000, 20));
	}
	
	

}
