package OpenTechnology;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

/**
 * Created by Avaja on 26.05.2016.
 */
public class Recipes {

    public static void init(){
    }

    public static void addRecipe(ItemStack out, Object... stacks){
        GameRegistry.addRecipe(out, stacks);
    }
}
