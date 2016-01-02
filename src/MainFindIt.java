import com.findit.android.data.Category;
import com.findit.android.data.Drawer;
import com.findit.android.data.Furniture;
import com.findit.android.data.Item;



public class MainFindIt {

	public static void main(String[] args) {
		Furniture desk = new Furniture("Desk", 1, 3);
		Category category = new Category("video games");
		category.addToGroup(new Item("super smash bros"));
		Drawer drawer = new Drawer("game drawer");
		Drawer drawer2 = new Drawer("game drawer");
		drawer.addToGroup(new Item("mario pen"));
		desk.addDrawer(drawer);
		drawer.setId(10);
		drawer2.setId(10);
		System.out.println(drawer.equals(drawer2));
	}
}
