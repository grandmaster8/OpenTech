package OpenTechnology;

import cpw.mods.fml.common.registry.GameRegistry;
import li.cil.oc.api.Items;
import li.cil.oc.api.driver.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 26.05.2016.
 */
public class Recipes {

    public static void init(){
        Items.get("");
    }

    public static void addRecipe(ItemStack out, Object... stacks){
        GameRegistry.addRecipe(out, stacks);
    }
}
