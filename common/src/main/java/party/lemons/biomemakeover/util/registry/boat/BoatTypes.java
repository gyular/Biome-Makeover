package party.lemons.biomemakeover.util.registry.boat;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import com.google.common.collect.Lists;
import party.lemons.biomemakeover.BiomeMakeover;
import party.lemons.biomemakeover.Constants;
import party.lemons.biomemakeover.init.BMBlocks;
import party.lemons.biomemakeover.util.registry.WoodTypeInfo;

import java.util.List;

public class BoatTypes
{
    public static List<BoatType> TYPES = Lists.newArrayList();

    public static final BoatType BLIGHTED_BALSA = new BoatType(BiomeMakeover.ID("blighted_balsa"), ()-> BMBlocks.BLIGHTED_BALSA_WOOD_INFO.getItem(WoodTypeInfo.Type.BOAT).get(), ()-> BMBlocks.BLIGHTED_BALSA_WOOD_INFO.getItem(WoodTypeInfo.Type.CHEST_BOAT).get());
    public static final BoatType WILLOW = new BoatType(BiomeMakeover.ID("willow"), ()->BMBlocks.WILLOW_WOOD_INFO.getItem(WoodTypeInfo.Type.BOAT).get(), ()->BMBlocks.WILLOW_WOOD_INFO.getItem(WoodTypeInfo.Type.CHEST_BOAT).get());
    public static final BoatType SWAMP_CYPRESS = new BoatType(BiomeMakeover.ID("swamp_cypress"), ()->BMBlocks.SWAMP_CYPRESS_WOOD_INFO.getItem(WoodTypeInfo.Type.BOAT).get(), ()->BMBlocks.SWAMP_CYPRESS_WOOD_INFO.getItem(WoodTypeInfo.Type.CHEST_BOAT).get());
    public static final BoatType ANCIENT_OAK = new BoatType(BiomeMakeover.ID("ancient_oak"), ()->BMBlocks.ANCIENT_OAK_WOOD_INFO.getItem(WoodTypeInfo.Type.BOAT).get(), ()->BMBlocks.ANCIENT_OAK_WOOD_INFO.getItem(WoodTypeInfo.Type.CHEST_BOAT).get());


    //Vanilla Types
    public static final BoatType ACACIA = new VanillaBoatType(BiomeMakeover.ID("acacia"), Boat.Type.ACACIA, Items.ACACIA_BOAT, Items.ACACIA_CHEST_BOAT);
    public static final BoatType BIRCH = new VanillaBoatType(BiomeMakeover.ID("birch"), Boat.Type.BIRCH, Items.BIRCH_BOAT, Items.BIRCH_CHEST_BOAT);
    public static final BoatType DARK_OAK = new VanillaBoatType(BiomeMakeover.ID("dark_oak"), Boat.Type.DARK_OAK, Items.DARK_OAK_BOAT, Items.DARK_OAK_CHEST_BOAT);
    public static final BoatType JUNGLE = new VanillaBoatType(BiomeMakeover.ID("jungle"), Boat.Type.JUNGLE, Items.JUNGLE_BOAT, Items.JUNGLE_CHEST_BOAT);
    public static final BoatType OAK = new VanillaBoatType(BiomeMakeover.ID("oak"), Boat.Type.OAK, Items.OAK_BOAT, Items.OAK_CHEST_BOAT);
    public static final BoatType SPRUCE = new VanillaBoatType(BiomeMakeover.ID("spruce"), Boat.Type.SPRUCE, Items.SPRUCE_BOAT, Items.SPRUCE_CHEST_BOAT);

    public static BoatType getVanillaType(Boat.Type boatType)
    {
          for(BoatType t : TYPES)
          {
              if(t instanceof VanillaBoatType)
              {
                  if(((VanillaBoatType) t).getVanillaType() == boatType) return t;
              }
          }

        return null;
    }

    public static void registerModelLayers()
    {
        for(BoatType type : TYPES)
        {
            EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, type.getModelLocation()), "main"), ()->BoatModel.createBodyModel(false));
            EntityModelLayerRegistry.register(new ModelLayerLocation(new ResourceLocation(Constants.MOD_ID, type.getChestModelLocation()), "main"), ()->BoatModel.createBodyModel(true));
        }
    }
}