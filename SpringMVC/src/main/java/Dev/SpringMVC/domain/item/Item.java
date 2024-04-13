package Dev.SpringMVC.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item 
{
	private Long id;
	private String itemName;
	private Integer price; // null을 허용하기 위해 Integer 사용 
	private Integer quantity; // null을 허용하기 위해 Integer 사용 
	
	public Item() {}
	
	public Item(String itemName, Integer price, Integer quantity) 
	{
		this.itemName = itemName;
		this.price = price;
		this.quantity = quantity;
	}
	
}
