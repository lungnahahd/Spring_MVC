package Dev.SpringMVC.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository 
{
	private static final Map<Long ,Item> store = new HashMap<>();
	private static long sequence = 0L;
	
	// 아이템 저장 
	public Item save(Item item) 
	{
		item.setId(++sequence);
		store.put(item.getId(), item);
		
		return item; 
	}
	
	// 아이디 기준으로 데이터 조회 
	public Item findById(Long id) 
	{
		return store.get(id);
	}
	
	// 모든 데이터 조회 
	public List<Item> findAll()
	{
		return new ArrayList<>(store.values());
	}
	
	// 데이터 업데이트 	
	public void update(Long itemId, Item updateParam) 
	{
		Item wantItem = findById(itemId);
		wantItem.setItemName(updateParam.getItemName());
		wantItem.setPrice(updateParam.getPrice());
		wantItem.setQuentity(updateParam.getQuentity());
	}
	
	// store 비우기 
	public void clearStore() 
	{
		store.clear();
	}
	
}
