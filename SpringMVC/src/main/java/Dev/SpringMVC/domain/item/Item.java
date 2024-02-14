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
	private Integer quentity; // null을 허용하기 위해 Integer 사용 
	
	public Item() {}
	
	public Item(String itemName, Integer price, Integer quentity) 
	{
		this.itemName = itemName;
		this.price = price;
		this.quentity = quentity;
	}
	
}
