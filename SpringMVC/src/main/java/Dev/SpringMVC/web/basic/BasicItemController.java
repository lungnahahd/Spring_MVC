package Dev.SpringMVC.web.basic;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String save(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes, Model model) 
	{
		// 검증 오류 보관 
		Map<String, String> errors = new HashMap<>();
		
		// 검증 로직
		if (!StringUtils.hasText(item.getItemName())) 
		{
			errors.put("itemName", "상품 이름은 필수입니다!");
		}
		if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) 
		{
			errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
		}
		if (item.getQuentity() == null || item.getQuentity() >= 9999) 
		{
			errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
		}
		if(item.getPrice() != null && item.getQuentity() != null) 
		{
			int resultPrice = item.getPrice() * item.getQuentity();
			if (resultPrice < 10000) 
			{
				errors.put("globalError", "가격 * 수량의 합은 10,000원 이상만 허용합니다.");
			}
		}
		
		// 검증에 실패하면 다시 입력폼으로 돌아가는 로직 
		if (!errors.isEmpty()) 
		{
			model.addAttribute("errors", errors);
			return "basic/addForm";
		}
		
		// 검증 성공 후 로직 
		// ModelAttribute의 괄호 안 Name 속성으로 넘겨주는 Model 네이밍 지정 가능
		// ModelAttribute 어노테이션으로 Model까지 내부적으로 생성하여 집어 넣음 
		Item savedItem = itemRepository.save(item);
		redirectAttributes.addAttribute("itemId", savedItem.getId());
		redirectAttributes.addAttribute("status", true);
		 
		
		// PRG 형식을 지키기 위해 Redirect 리턴으로 형식 변형 
		return "redirect:/basic/items/{itemId}";
		//return "basic/item";
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
